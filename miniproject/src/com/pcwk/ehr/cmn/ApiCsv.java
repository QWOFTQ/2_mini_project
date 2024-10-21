package com.pcwk.ehr.cmn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiCsv {
    // KOBIS API 키
    private static final String KOBIS_API_KEY = "82ca741a2844c5c180a208137bb92bd7";

    public static void main(String[] args) {
        // 현재 날짜를 가져옴
        LocalDate currentDate = LocalDate.now();
        // 조회할 날짜 (형식: YYYYMMDD)
        String targetDate = "20241006"; 
        // KOBIS API에서 박스 오피스 데이터를 가져올 URL 생성
        String boxOfficeUrl = String.format("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=%s&targetDt=%s", KOBIS_API_KEY, targetDate);

        try {
            // 박스 오피스 데이터 요청 및 응답 받기
            String boxOfficeResponse = sendGetRequest(boxOfficeUrl);
            // 응답에서 영화 리스트 추출
            JSONArray movieList = parseBoxOfficeResponse(boxOfficeResponse);

            // CSV 파일 작성 준비
            BufferedWriter writer = new BufferedWriter(new FileWriter("movieList2.csv"));
            writer.write("title,genre,age,rating,startDate,time,seat\n"); // CSV 헤더

            int movieCount = 0; // 추가할 영화 수 카운트
            for (int i = 0; i < movieList.length(); i++) {
                String movieCd = movieList.getJSONObject(i).getString("movieCd");
                // 영화 정보 요청 URL 생성
                String movieInfoUrl = String.format("https://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=%s&movieCd=%s", KOBIS_API_KEY, movieCd);

                // 영화 정보 요청 및 응답 받기
                String movieInfoResponse = sendGetRequest(movieInfoUrl);
                // 영화 정보 파싱
                JSONObject movieInfo = parseMovieInfoResponse(movieInfoResponse);

                // 관람등급에서 숫자만 추출
                String watchGradeNm = movieInfo.getJSONArray("audits").getJSONObject(0).getString("watchGradeNm");
                String ageRating = extractNumber(watchGradeNm); // 숫자만 추출

                // 영화 장르 정보 확인
                List<String> genres = new ArrayList<>();
                JSONArray genreArray = movieInfo.getJSONArray("genres");

                // 특정 장르만 포함하는지 확인하여 리스트에 추가
                for (int j = 0; j < genreArray.length(); j++) {
                    String genreNm = genreArray.getJSONObject(j).getString("genreNm");
                    if (genreNm.equals("액션") || genreNm.equals("드라마") || genreNm.equals("애니메이션")) {
                        genres.add(genreNm); // 조건에 맞는 장르 추가
                    }
                }

                // 조건에 맞는 영화만 CSV에 추가
                if (!genres.isEmpty()) {
                    // "일(요일)" 형식으로 변환
                    String formattedDate = currentDate.getDayOfMonth() + "(" + currentDate.getDayOfWeek().getDisplayName(java.time.format.TextStyle.SHORT, java.util.Locale.KOREA) + ")";

                    // CSV 파일에 영화 정보 작성
                    writer.write(String.format("%s,%s,%s,%s,%s,%s,%s\n", 
                        movieInfo.getString("movieNm"), // 영화 제목
                        String.join(", ", genres), // 장르
                        ageRating, // 연령 등급
                        "", // 평점은 나중에 추가할 수 있음
                        formattedDate, // 현재 날짜
                        "", // 상영 시간은 나중에 추가할 수 있음
                        ""  // 좌석 정보는 나중에 추가할 수 있음
                    ));
                    
                    movieCount++; // 추가된 영화 수 증가

                    // 9개 이상의 영화가 추가되면 루프 종료
                    if (movieCount >= 9) {
                        break; // 9개 영화가 추가되면 루프 종료
                    }
                }
            }

            writer.close(); // 파일 닫기
            System.out.println("CSV 파일이 생성되었습니다.");

        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        }
    }

    // GET 요청을 보내고 응답을 문자열로 반환하는 메소드
    private static String sendGetRequest(String urlStr) throws Exception {
        URL url = new URL(urlStr); // URL 객체 생성
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // 연결 열기
        conn.setRequestMethod("GET"); // 요청 방식 설정

        // 응답을 읽어오기 위한 BufferedReader
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder(); // 응답을 저장할 StringBuilder
        String inputLine;

        // 응답을 한 줄씩 읽어 StringBuilder에 추가
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close(); // BufferedReader 닫기
        return response.toString(); // 응답 문자열 반환
    }

    // 박스 오피스 응답을 파싱하여 영화 리스트를 반환하는 메소드
    private static JSONArray parseBoxOfficeResponse(String response) {
        JSONObject json = new JSONObject(response); // 응답 JSON 객체 생성
        return json.getJSONObject("boxOfficeResult").getJSONArray("weeklyBoxOfficeList"); // 영화 리스트 반환
    }

    // 영화 정보 응답을 파싱하여 영화 정보를 반환하는 메소드
    private static JSONObject parseMovieInfoResponse(String response) {
        JSONObject json = new JSONObject(response); // 응답 JSON 객체 생성
        return json.getJSONObject("movieInfoResult").getJSONObject("movieInfo"); // 영화 정보 반환
    }

    // 문자열에서 숫자만 추출하는 메소드
    private static String extractNumber(String str) {
        Pattern pattern = Pattern.compile("\\d+"); // 숫자 패턴
        Matcher matcher = pattern.matcher(str); // 패턴 매처 생성
        if (matcher.find()) {
            return matcher.group(); // 첫 번째 매치된 숫자 반환
        }
        return ""; // 숫자가 없으면 빈 문자열 반환
    }
}
