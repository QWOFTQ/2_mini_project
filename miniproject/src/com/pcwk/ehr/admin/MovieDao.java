package com.pcwk.ehr.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MovieDao implements MovieDiv<MovieVO> {
    // CSV 파일 경로 설정
    private final String fileName = "C:\\2024_09_09\\01_JAVA\\WORKSPACE\\miniproject\\movieList2.csv";
    // 영화 목록을 저장할 리스트
    public static List<MovieVO> movies = new ArrayList<>();

    // 생성자: 영화 목록을 파일에서 읽어옴
    public MovieDao() {
        super();
        readFile(fileName);
    }

    // 전체 영화 목록 반환
    public ArrayList<MovieVO> doSelectAll() {
        return new ArrayList<>(movies);
    }

    // 영화 존재 여부 확인
    private boolean isExistsMovie(MovieVO movie) {
        for (MovieVO vo : movies) {
            if (vo.getTitle().equalsIgnoreCase(movie.getTitle())) {
                return true; // 영화가 존재함
            }
        }
        return false; // 영화가 존재하지 않음
    }

    // 영화 저장 메소드
    public int doSave(MovieVO param, String timeInput) {
        // 좌석 수 유효성 검사
        if (param.getSeat() == null || param.getSeat() < 0) {
            System.out.println("좌석 수는 0 이상이어야 합니다.");
            return 0; // 저장 실패
        }

        // 영화가 이미 존재하는지 확인
        if (isExistsMovie(param)) {
            return 2; // 영화가 이미 존재함
        }

        // 현재 날짜와 사용자 입력 시간 설정
        LocalDate currentDate = LocalDate.now();
        param.setStartDate(currentDate); // 시작 날짜 설정
        param.setTime(timeInput); // 상영 시간 설정

        // 영화 목록에 추가하고 파일에 저장
        boolean check = movies.add(param);
        int flag = check ? 1 : 0;
        writeFile(fileName); // 파일에 기록
        return flag; // 성공 여부 반환
    }

    // 영화 수정 메소드
    @Override
    public int doUpdate(MovieVO param, String timeInput) {
        String paramTitle = param.getTitle().trim();
        for (MovieVO movie : movies) {
            if (movie.getTitle().trim().equalsIgnoreCase(paramTitle)) {
                // 좌석 수 유효성 검사
                if (param.getSeat() == null || param.getSeat() < 0) {
                    System.out.println("좌석 수는 0 이상이어야 합니다.");
                    return 0; // 수정 실패
                }
                // 수정할 영화의 속성 업데이트
                movie.setTitle(param.getTitle());
                movie.setGenre(param.getGenre());
                movie.setAge(param.getAge());
                movie.setRating(param.getRating());
                movie.setTime(timeInput); // 수정된 상영 시간
                movie.setSeat(param.getSeat()); // 수정된 좌석 수
                writeFile(fileName); // 파일에 기록
                return 1; // 수정 성공
            }
        }
        return 0; // 수정 실패
    }

    // 영화 삭제 메소드
    @Override
    public int doDelete(MovieVO param) {
        // 영화 제목으로 삭제
        boolean removed = movies.removeIf(movie -> movie.getTitle().equalsIgnoreCase(param.getTitle()));
        if (removed) {
            writeFile(fileName); // 삭제 후 파일에 기록
            return 1; // 삭제 성공
        } else {
            System.out.println("삭제할 영화가 존재하지 않습니다.");
            return 0; // 삭제 실패
        }
    }

    // 문자열을 MovieVO 객체로 변환하는 메소드
    public MovieVO stringToMovie(String data) {
        String[] movieArr = data.split(",");

        // 필수 정보 확인
        if (movieArr.length < 3) {
            System.out.println("데이터 형식 오류: " + data);
            return null;
        }

        // 영화 정보 추출 및 정리
        String title = movieArr[0].trim();
        String genre = movieArr[1].trim();
        int age = parseAge(movieArr[2]); // 나이 파싱
        Double rating = parseDouble(movieArr[3]); // 평점 파싱
        String time = parseTime(movieArr); // 시간 파싱
        Integer seat = parseSeat(movieArr); // 좌석 파싱

        LocalDate startDate = LocalDate.now(); // 현재 날짜로 설정

        return new MovieVO(title, genre, age, rating, startDate, time, seat);
    }

    // 나이 파싱 메소드
    private int parseAge(String ageStr) {
        if (ageStr.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(ageStr.trim());
        } catch (NumberFormatException e) {
            System.out.println("상영 등급 오류: " + ageStr);
            return 0;
        }
    }

    // 평점 파싱 메소드
    private Double parseDouble(String ratingStr) {
        if (ratingStr.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(ratingStr.trim());
        } catch (NumberFormatException e) {
            System.out.println("평점 형식 오류: " + ratingStr);
            return null;
        }
    }

    // 시간 파싱 메소드
    private String parseTime(String[] movieArr) {
        if (movieArr.length > 5 && !movieArr[5].trim().isEmpty()) {
            return movieArr[5].trim();
        }
        return null;
    }

    // 좌석 파싱 메소드
    private Integer parseSeat(String[] movieArr) {
        if (movieArr.length > 6 && !movieArr[6].trim().isEmpty()) {
            try {
                return Integer.parseInt(movieArr[6].trim());
            } catch (NumberFormatException e) {
                System.out.println("좌석 수 오류: " + movieArr[6]);
                return null;
            }
        }
        return null; // 기본 좌석 수는 null
    }

    // 파일에서 영화 목록 읽기
    @Override
    public int readFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String data;
            br.readLine(); // 헤더 스킵
            while ((data = br.readLine()) != null) {
                MovieVO outVo = stringToMovie(data); // 문자열을 MovieVO로 변환
                if (outVo != null) {
                    movies.add(outVo); // 유효한 영화만 추가
                }
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        return movies.size(); // 읽은 영화 수 반환
    }

    // 영화 목록을 파일에 쓰기
    @Override
    public int writeFile(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            // CSV 헤더 작성
            bw.write("title,genre,age,rating,startDate,time,seat");
            bw.newLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd(EEEE)"); // 날짜 형식 설정

            for (MovieVO movie : movies) {
                // 각 영화의 정보를 CSV 형식으로 작성
                StringBuilder sb = new StringBuilder();
                sb.append(movie.getTitle()).append(",")
                  .append(movie.getGenre()).append(",")
                  .append(movie.getAge()).append(",")
                  .append(movie.getRating() != null ? movie.getRating() : "").append(",")
                  .append(movie.getStartDate() != null ? movie.getStartDate().format(formatter) : "").append(",")
                  .append(movie.getTime() != null ? movie.getTime() : "").append(",") // 시간 추가
                  .append(movie.getSeat()); // 좌석 수 추가

                bw.write(sb.toString());
                bw.newLine(); // 다음 줄로 이동
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        return movies.size(); // 파일에 쓴 영화 수 반환
    }

    @Override
    public int doUpdate(MovieVO param) {
        return doUpdate(param, param.getTime()); // 기존 doUpdate 메소드 호출
    }

    // 제목으로 영화 선택
    @Override
    public MovieVO doSelectAll(String title) {
        for (MovieVO movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie; // 제목이 일치하는 영화 반환
            }
        }
        return null; // 영화가 존재하지 않음
    }

    @Override
    public int doSelectAll(MovieVO param) {
        // 이 메소드는 필요에 따라 구현해 주시면 됩니다.
        return 0;
    }

    // 영화 저장 메소드 (미구현)
    @Override
    public int doSave(MovieVO param) {
        // TODO Auto-generated method stub
        return 0;
    }

    // 제목으로 영화 선택 (미구현)
    @Override
    public MovieVO doSelectByTitle(String title) {
        // TODO Auto-generated method stub
        return null;
    }
}
