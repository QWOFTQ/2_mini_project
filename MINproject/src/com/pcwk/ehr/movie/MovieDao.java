package com.pcwk.ehr.movie;

import java.io.*;
import java.util.*;

public class MovieDao {
    private static final String fileName = "D:\\JAP_20240909\\01_JAVA\\workspace\\KCH-kang\\MINproject\\movie.csv";

    // 모든 영화 데이터를 가져오는 메서드
    public List<MovieVO> getAllMovies() {
        List<MovieVO> movies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 공백 제거와 함께 콤마로 필드 분리
                String[] data = line.split(",\\s*");
                if (data.length >= 6) { // 최소 6개의 필드가 있어야 함
                    try {
                        // ID는 파일에서 자동 생성되므로 movies.size()로 설정
                        String movieTitle = data[0];
                        String movieGenre = data[1];
                        int minAge = Integer.parseInt(data[2]);  // 연령 제한은 숫자여야 함
                        double movieRating = Double.parseDouble(data[3]);  // 평점은 숫자여야 함

                        // 스케줄 정보 처리
                        String[] days = data[4].split("/");
                        String[] times = data[5].split("/");

                        if (days.length != times.length) {
                            throw new IllegalArgumentException("요일과 시간이 일치하지 않습니다.");
                        }

                        Map<String, List<String>> schedule = new HashMap<>();
                        for (int i = 0; i < days.length; i++) {
                            schedule.put(days[i], Arrays.asList(times[i].split(";")));
                        }

                        // ID는 movies.size() + 1로 자동 생성
                        int id = movies.size() + 1;
                        movies.add(new MovieVO(id, movieTitle, movieGenre, movieRating, minAge, schedule));
                    } catch (NumberFormatException e) {
                        System.err.println("숫자 형식 오류: " + e.getMessage());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("데이터 부족: " + Arrays.toString(data));
                    } catch (IllegalArgumentException e) {
                        System.err.println("잘못된 스케줄 정보: " + e.getMessage());
                    }
                } else {
                    System.err.println("잘못된 형식의 데이터: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // 영화 추가 메서드
    public void doAddMovie(MovieVO movie) {
        // ID는 빼고 등록되도록 수정
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            // ID 없이 형식화된 문자열을 작성
            String movieString = formatMovieToStringWithoutId(movie);
            pw.println(movieString);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 영화 수정 메서드
    public void doUpdateMovie(MovieVO doUpdatedMovie) {
        List<MovieVO> movies = getAllMovies();
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (MovieVO movie : movies) {
                if (movie.getId() == doUpdatedMovie.getId()) {
                    // 수정된 영화 정보를 ID 없이 저장
                    pw.println(formatMovieToStringWithoutId(doUpdatedMovie));
                } else {
                    // 기존 영화 정보를 ID 없이 저장
                    pw.println(formatMovieToStringWithoutId(movie));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 영화 삭제 메서드
    public void doDeleteMovie(int id) {
        List<MovieVO> movies = getAllMovies();
        List<MovieVO> doUpdatedMovies = new ArrayList<>();
        for (MovieVO movie : movies) {
            if (movie.getId() != id) {
                doUpdatedMovies.add(movie); // 삭제하지 않은 영화들만 추가
            }
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (MovieVO movie : doUpdatedMovies) {
                // ID 없이 저장
                pw.println(formatMovieToStringWithoutId(movie));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ID로 특정 영화 조회 메서드
    public MovieVO getMovieId(int id) {
        List<MovieVO> movies = getAllMovies();
        for (MovieVO movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

    // 평점으로 영화 조회 메서드
    public List<MovieVO> getMoviesByRating(double minRating) {
        List<MovieVO> filteredMovies = new ArrayList<>();
        for (MovieVO movie : getAllMovies()) {
            if (movie.getMovieRating() >= minRating) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }

    // MovieVO 객체를 CSV 형식의 문자열로 변환하는 메서드 (ID 없이)
    private String formatMovieToStringWithoutId(MovieVO movie) {
        StringBuilder sb = new StringBuilder();
        sb.append(movie.getMovieTitle()).append(", ")
                .append(movie.getMovieGenre()).append(", ")
                .append(movie.getMinAge()).append(", ")
                .append(movie.getMovieRating()).append(", ");

        List<String> days = new ArrayList<>(movie.getSchedule().keySet());
        sb.append(String.join("/", days)).append(", ");

        List<String> times = new ArrayList<>();
        for (String day : days) {
            times.add(String.join(";", movie.getSchedule().get(day)));
        }
        sb.append(String.join("/", times));

        return sb.toString();
    }

    // MovieVO 객체를 CSV 형식의 문자열로 변환하는 메서드 (ID 포함)
    private String formatMovieToString(MovieVO movie) {
        StringBuilder sb = new StringBuilder();
        sb.append(movie.getId()).append(", ")
                .append(movie.getMovieTitle()).append(", ")
                .append(movie.getMovieGenre()).append(", ")
                .append(movie.getMinAge()).append(", ")
                .append(movie.getMovieRating()).append(", ");

        List<String> days = new ArrayList<>(movie.getSchedule().keySet());
        sb.append(String.join("/", days)).append(", ");

        List<String> times = new ArrayList<>();
        for (String day : days) {
            times.add(String.join(";", movie.getSchedule().get(day)));
        }
        sb.append(String.join("/", times));

        return sb.toString();
    }
}
