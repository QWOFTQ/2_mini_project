package com.pcwk.ehr.movie;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MovieDaoMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieDao movieDao = new MovieDao();
        MovieService movieService = new MovieService(movieDao);

        while (true) {
            try {
                System.out.println("1. 영화 등록  2. 영화 조회  3. 영화 수정  4. 영화 삭제  5. 프로그램 종료");
                System.out.print("선택> ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        movieService.addMovie(scanner);
                        break;

                    case 2:
                        movieService.displayAllMovies();
                        break;

                    case 3:
                        movieService.updateMovie(scanner);
                        break;

                    case 4:
                        movieService.deleteMovie(scanner);
                        break;

                    case 5:
                        System.out.println("프로그램 종료 !");
                        return;

                    default:
                        System.out.println("올바른 번호를 입력해주세요 !");
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다.");
                scanner.nextLine(); // 입력 버퍼를 비웁니다.
            }
        }
    }
}
