package com.pcwk.ehr.book;

import com.pcwk.ehr.movie.MovieDao;

import java.util.Scanner;

public class BookingMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PaymentService paymentService = new PaymentService();
        MovieDao movieDao = new MovieDao();
        BookingMovie book = new BookingMovie(movieDao);


        System.out.println("1. 영화 예매  2. 메인 메뉴");
        System.out.print("선택>");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                book.bookMovie(scanner);

                int totalPrice = book.getTotalPrice();

                if(totalPrice > 0){
                    System.out.println("\n총 결제 금액:" + totalPrice + "원");
                    paymentService.usingCoupon(totalPrice);
                    paymentService.paymethod(totalPrice);

                }else {
                    System.out.println("결제가 필요하지 않습니다.");
                    return;
                }

            case 2:
	

        }
    }


}