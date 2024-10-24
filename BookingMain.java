package com.pcwk.ehr.book;

import com.pcwk.ehr.movie.MovieDao;
import com.pcwk.ehr.kakaoPay.kakaoPayment;

import java.util.Scanner;

public class BookingMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieDao movieDao = new MovieDao();
        BookingMovie book = new BookingMovie(movieDao);

        while (true){
            System.out.println("1. 영화 예매 2. 프로그램 종료");
            System.out.print("선택> ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    book.bookMovie(scanner);

                    int totalPrice = book.getTotalPrice();
                    if (totalPrice > 0) {
                        System.out.println("총 결제 금액: " + totalPrice + "원");

                        try {
                            String paymentUrl = kakaoPayment.preparePayment(String.valueOf(totalPrice));
                            System.out.println("카카오페이 결제 준비 완료. 결제 URL: " + paymentUrl);
                            System.out.println("해당 URL로 이동하여 결제를 완료해 주세요.");
                        } catch (Exception e) {
                            System.out.println("카카오페이 결제 준비 중 오류가 발생했습니다: " + e.getMessage());
                        }
                    } else {
                        System.out.println("결제가 필요하지 않습니다. 올바른 예매를 진행하지 않았습니다.");
                    }
                    break;

                case 2:
                    System.out.println("프로그램 종료 !");
                    scanner.close();
                    return;

                default:
                    System.out.println("올바른 번호를 선택해주세요 !");
            }
        }
    }
}
