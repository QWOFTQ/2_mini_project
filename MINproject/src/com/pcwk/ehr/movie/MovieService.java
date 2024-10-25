package com.pcwk.ehr.movie;

import java.util.*;

public class MovieService {
    private MovieDao movieDao;

    public MovieService(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

    public MovieService() {

    }

    public void addMovie(Scanner scanner) {
        try {
            String title = "";
            while (title.isEmpty()) {
                System.out.print("영화 제목: ");
                title = scanner.nextLine().trim();
                if (title.isEmpty()) {
                    System.out.println("영화 제목을 입력해주세요!");
                }
            }

            String genre = "";
            while (genre.isEmpty()) {
                System.out.print("영화 장르: ");
                genre = scanner.nextLine().trim();
                if (genre.isEmpty()) {
                    System.out.println("영화 장르를 입력해주세요!");
                }
            }

            Map<String, List<String>> schedule = new HashMap<>();
            while (true) {
                System.out.print("상영 요일 입력 (예: 월~금 중 하나의 요일을 고르세요! 종료하려면 '끝' 입력): ");
                String day = scanner.nextLine().trim();
                if (day.equalsIgnoreCase("끝")) {
                    if (schedule.isEmpty()) {
                        System.out.println("최소 한 개 이상의 상영 요일을 입력해주세요!");
                    } else {
                        break;
                    }
                } else {
                    List<String> times = new ArrayList<>();
                    System.out.print(day + " 상영 시간 입력 (예: 12:00/17:00, 여러 개는 /로 구분): ");
                    String timeInput = scanner.nextLine().trim();
                    if (!timeInput.isEmpty()) {
                        times.addAll(Arrays.asList(timeInput.split("/")));
                        schedule.put(day, times);
                    } else {
                        System.out.println("상영 시간을 입력해주세요!");
                    }
                }
            }

            double rating = -1;
            while (rating < 0 || rating > 10) {
                try {
                    System.out.print("평점 ( 0.0~10.0 사이의 숫자): ");
                    rating = scanner.nextDouble();
                    scanner.nextLine();
                    if (rating < 0 || rating > 10) {
                        System.out.println("0.0에서 10.0 사이의 숫자를 입력해주세요!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("잘못된 입력입니다.");
                    scanner.nextLine();
                }
            }

            int minAge = -1;
            while (minAge != 0 && minAge != 12 && minAge != 15 && minAge != 19) {
                try {
                    System.out.print("연령 제한 (0, 12, 15, 19 중 하나를 입력해주세요): ");
                    minAge = scanner.nextInt();
                    scanner.nextLine();
                    if (minAge != 0 && minAge != 12 && minAge != 15 && minAge != 19) {
                        System.out.println("올바른 연령 제한을 입력해주세요! (0, 12, 15, 19 중 하나)");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("잘못된 입력입니다.");
                    scanner.nextLine();
                }
            }

            int id = movieDao.getAllMovies().size() + 1;
            MovieVO newMovie = new MovieVO(id, title, genre, rating, minAge, schedule);
            movieDao.doAddMovie(newMovie);
            System.out.println("영화가 추가되었습니다.");
        } catch (InputMismatchException e) {
            System.out.println("잘못된 입력입니다.");
            scanner.nextLine();
        }
    }

    public void displayAllMovies() {
        List<MovieVO> movies = movieDao.getAllMovies();
        for (MovieVO movie : movies) {
            System.out.println(formatMovieDetails(movie));
        }
    }

    private String formatMovieDetails(MovieVO movie) {
        StringBuilder sb = new StringBuilder();
        sb.append(movie.getId()).append(", ")
                .append(movie.getMovieTitle()).append(", ")
                .append(movie.getMovieGenre()).append(", 평점: ")
                .append(movie.getMovieRating()).append(", 연령 제한: ")
                .append(movie.getMinAge()).append("\n");

        if (movie.getSchedule() != null) {
            for (Map.Entry<String, List<String>> entry : movie.getSchedule().entrySet()) {
                sb.append(entry.getKey()).append(" 상영 시간: ").append(String.join(", ", entry.getValue())).append("\n");
            }
        }
        return sb.toString();
    }

    public void updateMovie(Scanner scanner) {
        try {
            System.out.print("수정할 영화 ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            MovieVO movie = movieDao.getMovieId(id);
            if (movie == null) {
                System.out.println("해당 ID의 영화를 찾을 수 없습니다.");
                return;
            }

            double newRating = -1;
            while (newRating < 0 || newRating > 10) {
                try {
                    System.out.print("새 평점 (현재: " + movie.getMovieRating() + "): ");
                    newRating = scanner.nextDouble();
                    scanner.nextLine();
                    if (newRating < 0 || newRating > 10) {
                        System.out.println("0.0에서 10.0 사이의 숫자를 입력해주세요!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("잘못된 입력입니다. 숫자를 입력해주세요!");
                    scanner.nextLine();
                }
            }
            movie.setMovieRating(newRating);

            Map<String, List<String>> newSchedule = new HashMap<>();
            while (true) {
                System.out.print("새 상영 요일 입력 (예: 월~금 중 하나의 요일을 고르세요! 종료하려면 '끝' 입력): ");
                String day = scanner.nextLine().trim();
                if (day.equalsIgnoreCase("끝")) {
                    if (newSchedule.isEmpty()) {
                        System.out.println("최소 한 개 이상의 상영 요일을 입력해주세요!");
                    } else {
                        break;
                    }
                } else {
                    List<String> times = new ArrayList<>();
                    System.out.print(day + " 상영 시간 입력 (예: 12:00/17:00, 여러 개는 /로 구분): ");
                    String timeInput = scanner.nextLine().trim();
                    if (!timeInput.isEmpty()) {
                        times.addAll(Arrays.asList(timeInput.split("/")));
                        newSchedule.put(day, times);
                    } else {
                        System.out.println("상영 시간을 입력해주세요!");
                    }
                }
            }

            movie.setSchedule(newSchedule);

            movieDao.doUpdateMovie(movie);
            System.out.println("영화 정보가 수정되었습니다.");
        } catch (InputMismatchException e) {
            System.out.println("잘못된 입력입니다.");
            scanner.nextLine();
        }
    }

    public void deleteMovie(Scanner scanner) {
        try {
            System.out.print("삭제할 영화 ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            movieDao.doDeleteMovie(id);
            System.out.println("영화가 삭제되었습니다.");
        } catch (InputMismatchException e) {
            System.out.println("잘못된 입력입니다.");
            scanner.nextLine();
        }
    }
}

