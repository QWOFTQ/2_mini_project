package com.pcwk.ehr.book;

import com.pcwk.ehr.movie.MovieDao;
import com.pcwk.ehr.movie.MovieVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BookingMovie {
    private MovieDao movieDao; // MovieDao instance
    private String[][] seats;

    // Ticket prices as constants
    private static final int CHILD_PRICE = 7000;
    private static final int TEEN_PRICE = 9000;
    private static final int ADULT_PRICE = 12000;

    public BookingMovie(MovieDao movieDao) {
        this.movieDao = movieDao;
        this.seats = new String[5][5]; // Initialize seats
        emptySeats();
    }

    private void emptySeats() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = "O"; // Mark all seats as available
            }
        }
    }

    public void bookMovie(BufferedReader reader) {
        try {
            List<MovieVO> movies = movieDao.getAllMovies();
            if (movies.isEmpty()) {
                System.out.println("등록한 영화가 없습니다.");
                return;
            }

            System.out.println("예매 가능한 영화 목록: ");
            for (MovieVO movie : movies) {
                System.out.println(movie);
            }

            System.out.print("예매할 영화 ID: ");
            int bookId = Integer.parseInt(reader.readLine());
            MovieVO reservationMovie = movieDao.getMovieId(bookId);

            if (reservationMovie == null) {
                System.out.println("올바른 영화 ID를 입력해주세요!");
                return;
            }

            Map<String, List<String>> schedule = reservationMovie.getSchedule();
            if (schedule.isEmpty()) {
                System.out.println("해당 영화의 상영 일정이 없습니다.");
                return;
            }

            System.out.println("상영 가능한 요일 / 시간:");
            for (Map.Entry<String, List<String>> entry : schedule.entrySet()) {
                System.out.println("요일: " + entry.getKey() + " / 상영 시간: " + String.join(", ", entry.getValue()));
            }

            System.out.print("상영 요일을 선택해주세요: ");
            String selectedDay = reader.readLine().trim();
            List<String> availableTimes = schedule.get(selectedDay);
            if (availableTimes == null) {
                System.out.println("올바른 요일을 선택해주세요!");
                return;
            }

            System.out.println("선택한 요일의 상영 시간: " + String.join(", ", availableTimes));
            System.out.print("상영 시간을 선택해주세요: ");
            String selectedTime = reader.readLine().trim();
            if (!availableTimes.contains(selectedTime)) {
                System.out.println("올바른 시간을 선택해주세요!");
                return;
            }

            System.out.println("선택한 영화: " + reservationMovie.getMovieTitle() + ", 상영 요일: " + selectedDay + ", 상영 시간: " + selectedTime);

            // Age check and seat booking
            int minAge = reservationMovie.getMinAge();
            System.out.println("연령 제한: " + (minAge == 0 ? "전체관람가" : minAge + "세 이상 관람가"));

            showSeatMap();

            // Input for ticket counts based on age restrictions
            int childrenCount = 0, teenCount = 0, adultCount = 0;
            if (minAge <= 12) {
                System.out.print("어린이 인원 수 입력: ");
                childrenCount = Integer.parseInt(reader.readLine());
            }

            if (minAge <= 18) {
                System.out.print("청소년 인원 수 입력: ");
                teenCount = Integer.parseInt(reader.readLine());
            }

            System.out.print("성인 인원 수 입력: ");
            adultCount = Integer.parseInt(reader.readLine());

            int totalPeople = childrenCount + teenCount + adultCount;

            while (true) {
                System.out.print("예약할 좌석 번호를 입력해주세요 (ex: A1,B2,C3): ");
                String[] seatInputs = reader.readLine().toUpperCase().split(",");

                if (seatInputs.length != totalPeople) {
                    System.out.println("입력한 좌석 수가 인원 수와 맞지 않습니다! 다시 입력해주세요.");
                    continue;
                }

                boolean isSeatAvailable = true;
                for (String seatInput : seatInputs) {
                    char rowChar = seatInput.charAt(0);
                    int row = rowChar - 'A';
                    int col = Character.getNumericValue(seatInput.charAt(1)) - 1;
                    if (row < 0 || row >= seats.length || col < 0 || col >= seats[0].length || !seats[row][col].equals("O")) {
                        System.out.println("잘못된 좌석 번호이거나 이미 예약된 좌석입니다: " + seatInput);
                        isSeatAvailable = false;
                        break;
                    }
                }

                if (isSeatAvailable) {
                    for (String seatInput : seatInputs) {
                        char rowChar = seatInput.charAt(0);
                        int row = rowChar - 'A';
                        int col = Character.getNumericValue(seatInput.charAt(1)) - 1;
                        seats[row][col] = "X"; // Mark seat as reserved
                    }
                    System.out.println("좌석이 예약되었습니다.");
                    break;
                } else {
                    System.out.println("다시 좌석을 선택해주세요!");
                }
            }

            int totalPrice = (childrenCount * CHILD_PRICE) + (teenCount * TEEN_PRICE) + (adultCount * ADULT_PRICE);
            System.out.println("총 예매 금액: " + totalPrice + "원");

        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력입니다. 숫자만 입력해주세요.");
        } catch (IOException e) {
            System.out.println("입력 오류: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("예매 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void showSeatMap() {
        System.out.println("\n───SCREEN───");
        System.out.print("  ");
        for (int j = 0; j < seats[0].length; j++) {
            System.out.print((j + 1) + " ");
        }
        System.out.println();
        char rowLabel = 'A';
        for (int i = 0; i < seats.length; i++) {
            System.out.print(rowLabel++ + " ");
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j] + " ");
            }
            System.out.println();
        }
    }
}
