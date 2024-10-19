package com.pcwk.ehr.cmn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiCsv {
    private static final String KOBIS_API_KEY = "82ca741a2844c5c180a208137bb92bd7";

    public static void main(String[] args) {
        String targetDate = "20241001"; // 조회할 날짜
        String boxOfficeUrl = String.format("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=%s&targetDt=%s", KOBIS_API_KEY, targetDate);

        try {
            // 박스 오피스 데이터 가져오기
            String boxOfficeResponse = sendGetRequest(boxOfficeUrl);
            JSONArray movieList = parseBoxOfficeResponse(boxOfficeResponse);

            // CSV 파일에 쓰기
            BufferedWriter writer = new BufferedWriter(new FileWriter("movieList2.csv"));
            writer.write("title,genre,age\n"); // CSV 헤더를 title, genre, age로 변경

            for (int i = 0; i < movieList.length(); i++) {
                String movieCd = movieList.getJSONObject(i).getString("movieCd");
                String movieInfoUrl = String.format("https://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=%s&movieCd=%s", KOBIS_API_KEY, movieCd);

                // 영화 정보 가져오기
                String movieInfoResponse = sendGetRequest(movieInfoUrl);
                JSONObject movieInfo = parseMovieInfoResponse(movieInfoResponse);

                // watchGradeNm에서 숫자만 추출
                String watchGradeNm = movieInfo.getJSONArray("audits").getJSONObject(0).getString("watchGradeNm");
                String ageRating = extractNumber(watchGradeNm); // 숫자만 추출

                // 영화 장르 정보 확인
                List<String> genres = new ArrayList<>();
                JSONArray genreArray = movieInfo.getJSONArray("genres");

                // 특정 장르만 포함하는지 확인
                for (int j = 0; j < genreArray.length(); j++) {
                    String genreNm = genreArray.getJSONObject(j).getString("genreNm");
                    if (genreNm.equals("액션") || genreNm.equals("드라마") || genreNm.equals("애니메이션")) {
                        genres.add(genreNm);
                    }
                }

                // 조건에 맞는 영화만 CSV에 추가
                if (!genres.isEmpty()) {
                    writer.write(String.format("%s,%s,%s\n", movieInfo.getString("movieNm"), String.join(", ", genres), ageRating));
                }
            }

            writer.close();
            System.out.println("CSV 파일이 생성되었습니다.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String sendGetRequest(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();
        return response.toString();
    }

    private static JSONArray parseBoxOfficeResponse(String response) {
        JSONObject json = new JSONObject(response);
        return json.getJSONObject("boxOfficeResult").getJSONArray("weeklyBoxOfficeList");
    }

    private static JSONObject parseMovieInfoResponse(String response) {
        JSONObject json = new JSONObject(response);
        return json.getJSONObject("movieInfoResult").getJSONObject("movieInfo");
    }

    // watchGradeNm에서 숫자만 추출하는 메서드
    private static String extractNumber(String str) {
        Pattern pattern = Pattern.compile("\\d+"); // 숫자 패턴
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(); // 첫 번째 매치된 숫자 반환
        }
        return ""; // 숫자가 없으면 빈 문자열 반환
    }
   
}
