package com.pcwk.ehr.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MovieDaoMain {

    private MovieDao dao = null;

    public MovieDaoMain() {
        dao = new MovieDao(); // MovieDao 사용
    }

    // 사용자 입력을 받아 영화 정보를 생성
    private MovieVO createMovieFromInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        
        try {
            System.out.print("영화 제목을 입력하세요: ");
            String title = reader.readLine();
            if (title.isEmpty()) throw new IllegalArgumentException("제목을 입력해야 합니다.");

            System.out.print("영화 장르를 입력하세요: ");
            String genre = reader.readLine();
            if (genre.isEmpty()) throw new IllegalArgumentException("장르를 입력해야 합니다.");

            System.out.print("상영 등급을 입력하세요: ");
            int age = Integer.parseInt(reader.readLine());

            System.out.print("영화 평점을 입력하세요 (없으면 0 입력): ");
            double rating = Double.parseDouble(reader.readLine());

            System.out.print("영화 상영 시작일(yyyy.MM.dd HH:mm)을 입력하세요: ");
            Date startDate = sdf.parse(reader.readLine());

            System.out.print("영화 상영 종료일(yyyy.MM.dd HH:mm)을 입력하세요: ");
            Date endDate = sdf.parse(reader.readLine());

            return new MovieVO(title, genre, age, rating, startDate, endDate);
        } catch (IOException | ParseException | IllegalArgumentException e) {
            System.out.println("입력 오류: " + e.getMessage());
            return null;
        }
    }

    public void doSave() {
        System.out.println("영화 등록");
        MovieVO movie = createMovieFromInput(); // 사용자 입력으로 영화 객체 초기화
        if (movie != null) {
            int flag = dao.doSave(movie);
            if (flag == 2) {
                System.out.println(movie.getTitle() + " 중복");
            } else if (flag == 0) {
                System.out.println(movie.getTitle() + " 등록 실패");
            } else {
                System.out.println("**************************");
                System.out.println(movie.getTitle() + " 등록 성공");
                System.out.println("**************************");
                dao.writeFile(dao.getFileName()); // 영화 등록 후 CSV 파일에 저장
            }
        } else {
            System.out.println("영화 객체가 초기화되지 않았습니다.");
        }
    }

    public void doDelete() {
        System.out.println("영화 삭제");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("삭제할 영화 제목을 입력하세요: ");
            String title = reader.readLine();
            if (title.isEmpty()) throw new IllegalArgumentException("제목을 입력해야 합니다.");

            MovieVO movieToDelete = new MovieVO(title, "", 0, 0.0, null, null);
            int flag = dao.doDelete(movieToDelete);
            if (flag == 1) {
                System.out.println("**************************");
                System.out.println(title + " 삭제 성공");
                System.out.println("**************************");
                dao.writeFile(dao.getFileName()); // 영화 삭제 후 CSV 파일에 저장
            } else {
                System.out.println(title + " 삭제 실패");
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("입력 오류: " + e.getMessage());
        }
    }

    public void doSelectAll() {
        System.out.println("영화 조회");
        List<MovieVO> movies = dao.doSelectAll(); // 모든 영화 목록 조회
        if (movies.isEmpty()) {
            System.out.println("등록된 영화가 없습니다.");
        } else {
            System.out.println("**************************");
            for (MovieVO movie : movies) {
                System.out.println(movie); // 영화 목록 출력
            }
            System.out.println("**************************");
        }
    }

    public void doUpdate() {
        System.out.println("영화 수정");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("수정할 영화 제목을 입력하세요: ");
            String title = reader.readLine();

            List<MovieVO> movieList = dao.doSelectAll();
            MovieVO foundMovie = null;

            for (MovieVO movie : movieList) {
                if (movie.getTitle().equals(title)) {
                    foundMovie = movie;
                    break;
                }
            }

            if (foundMovie == null) {
                System.out.println("영화를 찾을 수 없습니다.");
                return;
            }

            // 수정할 정보 입력 받기
            System.out.println("현재 정보: " + foundMovie);
            System.out.println("수정할 항목을 선택하세요: ");
            System.out.println("1. 제목");
            System.out.println("2. 장르");
            System.out.println("3. 상영 등급");
            System.out.println("4. 평점");
            System.out.println("5. 상영 시작일");
            System.out.println("6. 상영 종료일");
            System.out.print("선택: ");
            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                    System.out.print("새 제목을 입력하세요: ");
                    String newTitle = reader.readLine();
                    foundMovie.setTitle(newTitle);
                    break;
                case 2:
                	System.out.print("새 장르를 입력하세요: ");
                	foundMovie.setGenre(reader.readLine());
                	break;
                case 3:
                	System.out.print("새 상영 등급을 입력하세요: ");
                	foundMovie.setAge(Integer.parseInt(reader.readLine()));
                	break;
                case 4:
                    System.out.print("새 평점을 입력하세요: ");
                    foundMovie.setRating(Double.parseDouble(reader.readLine()));
                    break;
                case 5:
                    System.out.print("새 상영 시작일(yyyy.MM.dd HH:mm)을 입력하세요: ");
                    foundMovie.setStartDate(new SimpleDateFormat("yyyy.MM.dd HH:mm").parse(reader.readLine()));
                    break;
                case 6:
                    System.out.print("새 상영 종료일(yyyy.MM.dd HH:mm)을 입력하세요: ");
                    foundMovie.setEndDate(new SimpleDateFormat("yyyy.MM.dd HH:mm").parse(reader.readLine()));
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
                    return;
            }

            dao.doUpdate(foundMovie); // 수정된 영화 정보 업데이트
            dao.writeFile(dao.getFileName()); // CSV 파일에 저장
            System.out.println("**************************");
            System.out.println("영화 정보가 수정되었습니다.");
            System.out.println("**************************");
        } catch (IOException | NumberFormatException e) {
            System.out.println("입력 오류: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("수정 오류: " + e.getMessage());
        }
    }

    public void showMenu() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println("\n************ 영화 관리 시스템 ************");
                System.out.println("1. 영화 등록");
                System.out.println("2. 영화 조회");
                System.out.println("3. 영화 수정");
                System.out.println("4. 영화 삭제");
                System.out.println("5. 종료");
                System.out.print("메뉴를 선택하세요: ");
                
                int choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                    case 1:
                        doSave();
                        break;
                    case 2:
                        doSelectAll();
                        break;
                    case 3:
                        doUpdate();
                        break;
                    case 4:
                        doDelete();
                        break;
                    case 5:
                        System.out.println("프로그램을 종료합니다.");
                        return; // 프로그램 종료
                    default:
                        System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                }
       
            } catch (IOException | NumberFormatException e) {
                System.out.println("입력 오류: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        MovieDaoMain main = new MovieDaoMain();
        main.showMenu(); // 메뉴 표시
    }
}
 