package com.pcwk.ehr.member;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import com.pcwk.ehr.genre.GenreMain;
import com.pcwk.ehr.movie.MovieDao;
import com.pcwk.ehr.movie.MovieDaoMain;
import com.pcwk.ehr.movie.MovieVO;
import com.pcwk.ehr.rating.Rating; // Rating 클래스를 import
import com.pcwk.ehr.rating.RatingMain; // RatingMain import 추가
import com.pcwk.ehr.KCH.Main; // Main 클래스 import 추가
import com.pcwk.ehr.book.BookingMain;

public class MovieLoginApp {
    private static final String admId = "rkdcofls";
    private static final String admPw = "1030";
    private static final String userId01 = "yonjin";
    private static final String userPw01 = "1004";
    private static final String userId02 = "tjdals";
    private static final String userPw02 = "1103";

    public static void main(String[] args) throws ParseException {
        MovieDao movieDao = new MovieDao();
        Scanner scanner = new Scanner(System.in);
        boolean isLoggedIn = false;
        boolean isAdmin = false;

        while (!isLoggedIn) {
            System.out.print("아이디 입력: ");
            String inputId = scanner.nextLine();

            if (inputId.equals(userId01) || inputId.equals(admId) || inputId.equals(userId02)) {
                while (true) {
                    System.out.print("패스워드 입력: ");
                    String inputPw = scanner.nextLine();

                    if ((inputId.equals(userId01) && inputPw.equals(userPw01))
                            || (inputId.equals(admId) && inputPw.equals(admPw))
                            || (inputId.equals(userId02) && inputPw.equals(userPw02))) {
                        if (inputId.equals(admId)) {
                            System.out.println("관리자로 로그인 하셨습니다.");
                            isAdmin = true;
                        } else {
                            System.out.println("사용자로 로그인 하셨습니다.");
                        }
                        isLoggedIn = true;
                        break;
                    } else {
                        System.out.println("패스워드를 잘못 입력하셨습니다.");
                    }
                }
            } else {
                System.out.println("아이디를 잘못 입력하셨습니다.");
            }
        }

        if (isAdmin) {
            adminMenu(scanner, movieDao);
        } else {
            userMenu(scanner, movieDao);
        }

        System.out.println("로그아웃 되었습니다.");
        scanner.close();
    }

    private static void adminMenu(Scanner scanner, MovieDao movieDao) {
        boolean adminRunning = true;

        while (adminRunning) {
            System.out.println("\n===관리자 메인 메뉴===");
            System.out.println("1. 영화 관리");
            System.out.println("2. 회원 관리");
            System.out.println("3. 로그아웃");
            System.out.print("선택: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    movieManagementMenu(scanner);
                    break;
                case "2":
                    System.out.println("회원 관리");
                    Main.main(new String[] {}); // 회원 관리 프로그램 호출
                    break;
                case "3":
                    System.out.println("관리자 로그아웃중...");
                    adminRunning = false;
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
                    break;
            }
        }
    }

    private static void movieManagementMenu(Scanner scanner) {
        MovieDaoMain.main(new String[] {}); // 영화 관리 기능 호출
    }

    private static void userMenu(Scanner scanner, MovieDao movieDao) {
        boolean userRunning = true;

        while (userRunning) {
            System.out.println("\n===사용자 메인 메뉴===");
            System.out.println("\n=== CGV 홍대점=== \n");
            System.out.println("1. 영화 목록 조회");
            System.out.println("2. 영화 장르별 조회");
            System.out.println("3. 평점별 영화 조회"); // 평점 조회 기능 추가
            System.out.println("4. 예매"); // 평점 조회 기능 추가
            System.out.println("5. 로그아웃");
            System.out.print("선택: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                	// 영화 목록 조회
                    List<MovieVO> movieList = movieDao.getAllMovies();
                    for (MovieVO movie : movieList) {
                        System.out.printf("ID: %d, 제목: %s, 장르: %s, 평점: %.2f, 최소 연령: %d\n",
                                movie.getId(), movie.getMovieTitle(), movie.getMovieGenre(), movie.getMovieRating(), movie.getMinAge());
               
                    }
                    break;
                case "2":
                    // 장르별 조회 기능 호출
                    GenreMain.main(new String[] {});
                    break;
                case "3":
                    // 평점순 조회 기능 호출
                    RatingMain ratingMain = new RatingMain(); // RatingMain 인스턴스 생성
                    ratingMain.showMenu(); // 평점 조회 메뉴 호출
                    break;
                case "4":
                    System.out.println("예매");
                    BookingMain.main(new String[0]);
                    break;
                case "5":
                	System.out.println("사용자 로그아웃중...");
                	userRunning = false;
                	break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
                    break;
            }
        }
    }
}
