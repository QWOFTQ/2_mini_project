package com.pcwk.ehr.genre;

import com.pcwk.ehr.movie.MovieDao;
import com.pcwk.ehr.book.BookingMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GenreMain {
    private MovieDao movieDao;
    private BufferedReader reader;

    public GenreMain(MovieDao movieDao) {
        this.movieDao = movieDao;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void showMenu() {
        GenreList genre = new GenreList(movieDao); // GenreList 인스턴스 생성 시 MovieDao 전달

        while (true) {
            try {
                System.out.println("\n************ 장르별 영화 조회 시스템 ************\n");
                System.out.println("1. 장르별 영화 조회");
                System.out.println("2. 예매");
                System.out.println("3. 메인 메뉴");
                System.out.print("메뉴를 선택하세요: ");
                
                int choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                    case 1:
                        System.out.print("조회할 장르를 입력하세요: ");
                        String genreInput = reader.readLine(); // 사용자 입력
                        genre.movieAll(genreInput); // 입력된 장르에 대한 영화 목록 조회
                        break;

                    case 2:
                        // 예매 기능을 실행하기 위해 BookingMain의 main 메서드 호출
                        System.out.println("영화 예매로 이동합니다...");
                        BookingMain.main(new String[0]); // BookingMain의 main 메서드 호출
                        break;

                    case 3:
                    	
                    	System.out.println("메인 메뉴로 돌아갑니다.");
                        return; 
                   
                 

                    default:
                        System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                }

            } catch (IOException | NumberFormatException e) {
                System.out.println("입력 오류: " + e.getMessage());
            }
        }
    }

    // GenreMain의 main 메서드 추가
    public static void main(String[] args) {
        MovieDao movieDao = new MovieDao(); // MovieDao 객체 생성
        GenreMain genreMain = new GenreMain(movieDao); // GenreMain 인스턴스 생성
        genreMain.showMenu(); // 메뉴 표시
    }
}
