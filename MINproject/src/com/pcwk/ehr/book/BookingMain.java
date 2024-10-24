package com.pcwk.ehr.book;

import com.pcwk.ehr.movie.MovieDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BookingMain {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // BufferedReader 사용
        MovieDao movieDao = new MovieDao();
        BookingMovie book = new BookingMovie(movieDao);

        while (true) {
            System.out.println("1. 영화 예매 2. 프로그램 종료");
            System.out.print("선택> ");
            
            try {
                int choice = Integer.parseInt(reader.readLine()); // BufferedReader로 입력 받기

                switch (choice) {
                    case 1:
                        book.bookMovie(reader); // BufferedReader 전달
                        break;

                    case 2:
                        System.out.println("프로그램 종료 !");
                        return; // 프로그램 종료

                    default:
                        System.out.println("올바른 번호를 선택해주세요 !");
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자만 입력해주세요.");
            } catch (IOException e) {
                System.out.println("입력 오류: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("오류가 발생했습니다: " + e.getMessage());
            }
        }
    }
}
