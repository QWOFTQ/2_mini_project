package com.pcwk.ehr.admin;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MovieDao implements MovieDiv<MovieVO> {

    // CSV 파일 경로 설정
    private final String fileName = "C:\\2024_09_09\\01_JAVA\\WORKSPACE\\miniproject\\movieList2.csv";
    
    // 영화 정보를 저장할 리스트
    public static List<MovieVO> movies = new ArrayList<>();

    // 생성자: MovieDao 객체가 생성될 때 파일에서 영화 목록을 읽어온다.
    public MovieDao() {
        super();
        readFile(fileName); // 파일에서 영화 목록 읽기
    }

    // 모든 영화 조회 메서드
    public ArrayList<MovieVO> doSelectAll() {
        return new ArrayList<>(movies); // 현재 저장된 모든 영화 목록 반환
    }

    // 영화 제목이 중복되는지 확인하는 메서드
    private boolean isExistsMovie(MovieVO movie) {
        for (MovieVO vo : movies) {
            // 대소문자 구분 없이 제목 비교
            if (vo.getTitle().equalsIgnoreCase(movie.getTitle())) {
                return true; // 중복된 제목이 있으면 true 반환
            }
        }
        return false; // 중복이 없으면 false 반환
    }

    // 영화 저장 메서드
    @Override
    public int doSave(MovieVO param) {
        // 중복된 영화 제목 체크
        if (isExistsMovie(param)) {
            return 2; // 중복된 영화 제목
        }

        // 영화 목록에 추가
        boolean check = movies.add(param); 
        // 추가 성공 여부에 따라 flag 설정
        int flag = check ? 1 : 0; 
        // 파일에 변경 사항 저장
        writeFile(fileName); 
        return flag; // 추가 결과 반환
    }

    // 영화 업데이트 메서드
    @Override
    public int doUpdate(MovieVO param) {
        String paramTitle = param.getTitle().trim(); // 입력 제목의 공백 제거
        for (MovieVO movie : movies) {
            // 영화 제목 비교
            if (movie.getTitle().trim().equalsIgnoreCase(paramTitle)) {
                // 모든 정보를 업데이트
                movie.setTitle(param.getTitle());
                movie.setGenre(param.getGenre());
                movie.setAge(param.getAge());
                movie.setRating(param.getRating());
                movie.setStartDate(param.getStartDate());
                movie.setEndDate(param.getEndDate());
                return 1; // 업데이트 성공
            }
        }
        return 0; // 업데이트 실패
    }

    // 영화 삭제 메서드
    @Override
    public int doDelete(MovieVO param) {
        // 대소문자 구분 없이 영화 제목으로 삭제
        boolean removed = movies.removeIf(movie -> movie.getTitle().equalsIgnoreCase(param.getTitle())); 
        if (removed) {
            // 파일에 변경 사항 저장
            writeFile(fileName); 
            return 1; // 성공
        } else {
            // 삭제 실패 시 메시지 출력
            System.out.println("삭제할 영화가 존재하지 않습니다."); 
            return 0; // 실패
        }
    }

    // 문자열을 MovieVO 객체로 변환하는 메서드
    public MovieVO stringToMovie(String data) {
        // CSV의 각 필드를 분리하여 배열로 저장
        String[] movieArr = data.split(","); 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");

        // 최소한 3개의 필드(title, genre, age)가 필요합니다.
        if (movieArr.length < 3) {
            System.out.println("데이터 형식 오류: " + data);
            return null; // 오류 발생 시 null 반환
        }

        // 제목, 장르, 나이 정보 가져오기
        String title = movieArr[0].trim();
        String genre = movieArr[1].trim();
        
        // 나이는 반드시 정수여야 하므로 기본값 설정
        int age = 0; 
        if (!movieArr[2].trim().isEmpty()) {
            try {
                age = Integer.parseInt(movieArr[2].trim());
                if (age < 0) {
                    System.out.println("상영 등급 오류: " + movieArr[2]); // 음수 체크
                    return null; // 잘못된 경우 null 반환
                }
            } catch (NumberFormatException e) {
                System.out.println("상영 등급 오류: " + movieArr[2]);
                return null; // 잘못된 경우 null 반환
            }
        }

        // 평점, 시작일, 종료일 기본값 설정
        Double rating = null; 
        Date startDate = null; 
        Date endDate = null; 

        // 평점, 시작일, 종료일을 선택적으로 읽기
        if (movieArr.length > 3 && !movieArr[3].trim().isEmpty()) {
            try {
                rating = Double.parseDouble(movieArr[3].trim());
            } catch (NumberFormatException e) {
                System.out.println("평점 형식 오류: " + movieArr[3]);
                return null; // 잘못된 경우 null 반환
            }
        }

        try {
            // 시작일, 종료일 변환
            if (movieArr.length > 4 && !movieArr[4].trim().isEmpty()) {
                startDate = sdf.parse(movieArr[4].trim());
            }
            if (movieArr.length > 5 && !movieArr[5].trim().isEmpty()) {
                endDate = sdf.parse(movieArr[5].trim());
            }
        } catch (ParseException e) {
            System.out.println("날짜 형식 오류: " + e.getMessage());
            return null; // 잘못된 경우 null 반환
        }

        // MovieVO 객체 반환
        return new MovieVO(title, genre, age, rating, startDate, endDate); 
    }

    // 파일에서 영화 정보를 읽는 메서드
    @Override
    public int readFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String data;
            br.readLine(); // 첫 번째 줄(헤더) 건너뛰기
            while ((data = br.readLine()) != null) {
                // 문자열을 MovieVO로 변환
                MovieVO outVo = stringToMovie(data); 
                if (outVo != null) {
                    movies.add(outVo); // 영화 목록에 추가
                }
            }
        } catch (IOException e) {
            System.out.println("IOException:" + e.getMessage());
        }

        // 영화정보 전체 조회:
        // displayList(movies); // 이 부분을 주석 처리합니다.
        return movies.size(); // 읽은 영화의 개수 반환
    }

    // 영화 정보를 파일에 쓰는 메서드
    @Override
    public int writeFile(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            // 헤더 작성
            bw.write("title,genre,age,rating,startDate,endDate");
            bw.newLine(); // 줄바꿈
            for (MovieVO movie : movies) {
                StringBuilder sb = new StringBuilder();
                // 영화 정보를 CSV 형식으로 저장
                sb.append(movie.getTitle()).append(",")
                  .append(movie.getGenre()).append(",")
                  .append(movie.getAge()).append(",")
                  .append(movie.getRating() != null ? movie.getRating() : "").append(",")
                  .append(movie.getStartDate() != null ? new SimpleDateFormat("yyyy.MM.dd HH:mm").format(movie.getStartDate()) : "").append(",")
                  .append(movie.getEndDate() != null ? new SimpleDateFormat("yyyy.MM.dd HH:mm").format(movie.getEndDate()) : "");
                bw.write(sb.toString()); // 파일에 쓰기
                bw.newLine(); // 줄바꿈
            }
        } catch (IOException e) {
            System.out.println("IOException:" + e.getMessage());
        }
        return movies.size(); // 저장된 영화의 개수 반환
    }

    // 파일 이름 반환 메서드
    public String getFileName() {
        return fileName;
    }

    @Override
    public int doSelectAll(MovieVO param) {
        // 이 메서드는 현재 사용되지 않음
        return 0; 
    }
}
