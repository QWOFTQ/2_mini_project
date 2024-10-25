package com.pcwk.ehr.rating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.pcwk.ehr.book.BookingMain;

public class RatingMain {
    public static void main(String[] args) {
        new RatingMain().showMenu(); // Entry point
    }

    public void showMenu() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Rating rating = new Rating(); // Rating instance
        while (true) {
            try {
                System.out.println("\n************ 평점별 영화 조회 시스템 ************\n");
                System.out.println("1. 평점별 영화 조회");
                System.out.println("2. 예매");
                System.out.println("3. 메인 메뉴"); // 변경된 부분
                System.out.print("\n메뉴를 선택하세요: ");

                int choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                    case 1:
                        rating.rating(); // Retrieve and show ratings
                        break;

                    case 2:
                        System.out.print("예매: ");
                        BookingMain.main(new String[0]); // Booking functionality
                        break;

                    case 3:
                        System.out.println("메인 메뉴");
                        return; // 사용자 메뉴로 돌아가는 부분

                    default:
                        System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                }

            } catch (IOException | NumberFormatException e) {
                System.out.println("입력 오류: " + e.getMessage());
            }
        }
    }
}
