package com.pcwk.ehr.booking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import com.pcwk.ehr.admin.MovieDao;
import com.pcwk.ehr.admin.MovieVO;

public class Booking {

    // 영화 예매 메서드
    public void bookingMovie() {
        MovieDao movieDao = new MovieDao(); // MovieDao 객체 생성
        ArrayList<MovieVO> allMovies = movieDao.doSelectAll(); // 모든 영화 정보 가져오기
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd(EEEE)"); // 날짜 형식 (요일 포함)
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm"); // 시간 형식
        
        LocalDate currentDate = LocalDate.now(); // 현재 날짜
        LocalTime currentTime = LocalTime.now(); // 현재 시간

        // 현재 날짜와 시간 출력
        System.out.println("현재 시간: " + dateFormatter.format(currentDate) + " " + currentTime.format(timeFormatter));
        System.out.println("예매 가능한 영화 목록:");

        ArrayList<MovieVO> availableMovies = new ArrayList<>(); // 예매 가능한 영화 리스트

        // 모든 영화에서 예매 가능한 영화 필터링
        for (MovieVO movie : allMovies) {
            LocalDate movieDate = movie.getStartDate(); // 영화 시작 날짜
            LocalTime movieStartTime = LocalTime.parse(movie.getTime(), timeFormatter); // 영화 시작 시간

            // 현재 날짜가 영화 시작 날짜 이전이거나, 현재 날짜가 영화 시작 날짜와 같고 현재 시간이 영화 시작 시간 이전인 경우
            if (currentDate.isBefore(movieDate) || (currentDate.isEqual(movieDate) && currentTime.isBefore(movieStartTime))) {
                // 이미 추가된 영화인지 확인
                boolean exists = availableMovies.stream()
                        .anyMatch(existingMovie -> existingMovie.getTitle().equals(movie.getTitle()));
                // 영화가 없다면 추가
                if (!exists) {
                    availableMovies.add(movie);
                }
            }
        }

        // 예매 가능한 영화가 없을 경우 메시지 출력
        if (availableMovies.isEmpty()) {
            System.out.println("현재 예매 가능한 영화가 없습니다.");
            return; // 예매 종료
        }

        // 예매 가능한 영화 목록 출력
        for (MovieVO movie : availableMovies) {
            System.out.println(movie);
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("예매할 영화의 제목을 입력하세요: ");
            String selectedTitle = scanner.nextLine(); // 사용자로부터 영화 제목 입력받기

            boolean movieBooked = false; // 영화 예매 여부 확인 변수
            for (MovieVO movie : availableMovies) {
                // 제목 비교 시 공백 제거하고 대소문자 구분 없이 비교
                if (movie.getTitle().trim().equalsIgnoreCase(selectedTitle.trim())) {
                    System.out.println("영화 '" + selectedTitle + "'이(가) 예매되었습니다."); // 예매 완료 메시지
                    movieBooked = true; // 영화가 예매된 것으로 설정
                    break; // 루프 종료
                }
            }

            // 입력한 제목의 영화가 예매되지 않은 경우 메시지 출력
            if (!movieBooked) {
                System.out.println("해당 영화는 존재하지 않거나 예매할 수 없습니다. 입력한 제목: " + selectedTitle);
            }
        } catch (Exception e) {
            // 예외 발생 시 메시지 출력
            System.out.println("오류가 발생했습니다: " + e.getMessage());
        }
    }
}
