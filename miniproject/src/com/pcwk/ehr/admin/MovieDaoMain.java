package com.pcwk.ehr.admin;

import java.util.Scanner;

public class MovieDaoMain {

    private static MovieDao dao = new MovieDao();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n-------영화 관리 시스템--------\n");
            System.out.println("1. 영화 등록");
            System.out.println("2. 영화 수정");
            System.out.println("3. 영화 삭제");
            System.out.println("4. 영화 목록 보기");
            System.out.println("5. 종료");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    doSave();
                    break;
                case 2:
                    doUpdate();
                    break;
                case 3:
                    doDelete();
                    break;
                case 4:
                    showAllMovies();
                    break;
                case 5:
                    System.out.println("종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }

    public static void doSave() {
        System.out.println("영화 등록");
        MovieVO movie = createMovieFromInput(); // 사용자 입력으로 영화 객체 생성
        if (movie != null) {
            System.out.print("상영 시간을 입력하세요: ");
            String timeInput = scanner.nextLine();
            int flag = dao.doSave(movie, timeInput); // DAO를 통해 영화 저장
            if (flag == 2) {
                System.out.println("영화가 이미 존재합니다.");
            } else if (flag == 0) {
                System.out.println(movie.getTitle() + " 등록 실패");
            } else {
                // 성공적으로 등록된 경우
                System.out.println("**************************");
                System.out.println(movie.getTitle() + " 등록 성공");
                System.out.println("**************************");
            }
        } else {
            System.out.println("영화 객체가 초기화되지 않았습니다.");
        }
    }

    public static void doUpdate() {
        System.out.println("영화 수정");
        System.out.print("수정할 영화의 제목을 입력하세요: ");
        String title = scanner.nextLine().trim(); // 입력값의 앞뒤 공백 제거
        MovieVO existingMovie = null;

        // 모든 영화 목록에서 입력한 제목과 일치하는 영화 찾기
        for (MovieVO movie : dao.doSelectAll()) {
            if (movie.getTitle().equalsIgnoreCase(title)) { // 대소문자 무시하여 비교
                existingMovie = movie;
                break; // 일치하는 영화가 있으면 반복 종료
            }
        }

        if (existingMovie != null) {
            boolean updating = true;
            while (updating) {
                System.out.println("\n수정할 항목을 선택하세요:");
                System.out.println("1. 제목");
                System.out.println("2. 장르");
                System.out.println("3. 상영 등급");
                System.out.println("4. 평점");
                System.out.println("5. 상영 시간");
                System.out.println("6. 종료");
                System.out.print("선택: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume the newline character

                switch (choice) {
                    case 1:
                        System.out.print("새 제목을 입력하세요: ");
                        String newTitle = scanner.nextLine();
                        existingMovie.setTitle(newTitle);
                        break;
                    case 2:
                        System.out.print("새 장르를 입력하세요: ");
                        String newGenre = scanner.nextLine();
                        existingMovie.setGenre(newGenre);
                        break;
                    case 3:
                        System.out.print("새 상영 등급을 입력하세요: ");
                        int newAge = scanner.nextInt();
                        existingMovie.setAge(newAge);
                        scanner.nextLine(); // consume the newline character
                        break;
                    case 4:
                        System.out.print("새 평점을 입력하세요: ");
                        double newRating = scanner.nextDouble();
                        existingMovie.setRating(newRating);
                        scanner.nextLine(); // consume the newline character
                        break;
                    case 5:
                        System.out.print("새 상영 시간을 입력하세요: ");
                        String newTime = scanner.nextLine();
                        existingMovie.setTime(newTime);
                        break;
                    case 6:
                        updating = false; // 수정 종료
                        continue;
                    default:
                        System.out.println("잘못된 선택입니다.");
                        continue;
                }

                // 수정 완료 후 DAO 호출
                int flag = dao.doUpdate(existingMovie, existingMovie.getTime()); // 기존 상영 시간을 사용
                if (flag == 0) {
                    System.out.println("수정 실패");
                } else {
                    System.out.println("수정 성공");
                }
            }
        } else {
            System.out.println("해당 제목의 영화가 존재하지 않습니다.");
        }
    }


    public static void doDelete() {
        System.out.println("영화 삭제");
        System.out.print("삭제할 영화의 제목을 입력하세요: ");
        String title = scanner.nextLine();
        MovieVO movie = new MovieVO(); // 기본 생성자를 사용하여 초기화
        movie.setTitle(title);
        int flag = dao.doDelete(movie); // DAO를 통해 영화 삭제
        if (flag == 0) {
            System.out.println(movie.getTitle() + " 삭제 실패");
        } else {
            System.out.println(movie.getTitle() + " 삭제 성공");
        }
    }

    public static void showAllMovies() {
        System.out.println("영화 목록");
        for (MovieVO movie : dao.doSelectAll()) {
            System.out.println(movie);
        }
    }

    private static MovieVO createMovieFromInput() {
        MovieVO movie = new MovieVO(null, null, 0, null, null, null, null); // 생성자를 사용하여 초기화
        System.out.print("영화 제목: ");
        String title = scanner.nextLine();
        movie.setTitle(title);

        System.out.print("장르: ");
        String genre = scanner.nextLine();
        movie.setGenre(genre);

        System.out.print("상영 등급 (예: 12, 15): ");
        int age = scanner.nextInt();
        movie.setAge(age);
        scanner.nextLine(); // consume the newline character

        System.out.print("평점 (예: 8.5): ");
        double rating = scanner.nextDouble();
        movie.setRating(rating);
        scanner.nextLine(); // consume the newline character

        return movie; // 영화 객체 반환
    }

	public void showMenu() {
		// TODO Auto-generated method stub
		
	}
}
