package com.pcwk.ehr.book;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class PaymentService {
    Scanner scanner = new Scanner(System.in);

    public void paymethod(int totalPrice) {
        System.out.println("\n=== 결제 시스템 ===");
        System.out.println("1. 계좌이체  2. 카드 결제");
        System.out.print("선택>");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                accountTransfer(totalPrice);
                break;

            case 2:
                cardPayment(totalPrice);
                break;

            default:
                System.out.println("잘못된 선택입니다. 1이나 2를 골라주세요.");
                paymethod(totalPrice);
                return;
        }
    }

    public void usingCoupon(int totalPrice) {
        boolean validInput = false;

        while(!validInput) {
            System.out.print("할인 쿠폰이 사용하시겠습니까? (Y/N): ");
            String useCoupon = scanner.next();

            if (useCoupon.equalsIgnoreCase("Y")) {
                validInput = true;
                System.out.print("할인 쿠폰 번호를 입력하세요 (4자리): ");
                String couponNumber = scanner.next();
                String[] validCoupons = {"1234", "0000", "1004"};
                boolean isValidCoupons = false;

                for (String validCoupon : validCoupons) {
                    if (couponNumber.equals(validCoupon)) {
                        isValidCoupons = true;
                        break;
                    }
                }

                if (isValidCoupons) {
                    System.out.println("할인 쿠폰이 적용되었습니다. 20% 할인됩니다");
                    int dicountPrice = (int) (totalPrice * 0.8);
                    System.out.printf("할인된 가격은 %d원 입니다\n", dicountPrice);
                } else {
                    System.out.println("유효하지 않은 쿠폰 번호입니다.");
                }
            } else if (useCoupon.equalsIgnoreCase("N")) {
                validInput = true;
            } else {
                System.out.println("Y 혹은 N을 입력해주세요");

            }
        }
    }

    public void accountTransfer(int totalPrice){
        System.out.print("계좌번호를 입력해주세요 (14 ~ 16자리): ");
        String buyerAccountInput = scanner.next();
        while (true) {
            if (buyerAccountInput.length() >= 14 && buyerAccountInput.length() <= 16) {
                System.out.println("판매자 계좌번호: 123-456-789");

                while (true){
                    System.out.println("입금할 금액을 입력하세요: ");
                    int transferAmount = scanner.nextInt();

                    if (transferAmount == totalPrice) {
                        System.out.printf("결제 금액은 %d원입니다. 결제를 진행하시겠습니까? (Y/N): ", totalPrice);
                        String confirm = scanner.next();

                        if (confirm.equalsIgnoreCase("Y")) {
                            System.out.println("결제가 완료되었습니다.");
                        } else {
                            System.out.println("결제가 취소되었습니다.");
                        }
                        break;
                    } else {
                        System.out.println("결제 실패! 입금한 금액이 가격과 일치하지 않습니다.");
                    }
                }
                break;
            }else {
                System.out.println("계좌번호를 다시 입력헤주세요 (14 ~ 16자리");
            }
        }
    }

    public void cardPayment(int totalPrice){
        String cardNumber;
        while(true){
            System.out.print("카드 번호를 입력해주세요 (16자리): ");
            cardNumber = scanner.next();

            if(cardNumber.length() == 16){
                break;
            }else {
                System.out.println("잘못된 카드 번호입니다. 16자리 번호를 입력해주세요.");
            }
        }

        String cvcNumber;
        while(true){
            System.out.print("CVC 번호를 입력해주세요 (3자리): ");
            cvcNumber = scanner.next();

            if(cvcNumber.length() == 3){
                break;
            }else {
                System.out.println("잘못된 CVC 번호입니다. 3자리 번호를 입력해주세요.");
            }
        }

        String expirationDate;
        while(true){
            System.out.print("카드 유효기간을 입력해주세요 (MM/yy): ");
            expirationDate = scanner.next();

            if(expirationDate.matches("^(0[1-9]|1[0-2])/\\d{2}$")){
                try{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
                    LocalDate expiryDate = LocalDate.parse("01/" + expirationDate,DateTimeFormatter.ofPattern("dd/MM/yy"));
                    LocalDate currentDate = LocalDate.now();

                    if(expiryDate.isBefore(currentDate)){
                        System.out.println("카드 유효기간이 지났습니다. 유효기간을 확인해주세요.");
                    }else {
                        break;
                    }
                } catch (DateTimeParseException e){
                    System.out.println("유효기간 형식이 잘못되었습니다. 다시 입력해주세요.");
                }
            }else {
                System.out.println("유효하지 않은 유효기간입니다. MM/yy 형식으로 입력해주세요.");
            }
        }

        System.out.printf("결제 금액은 %d원입니다. 결제를 진행하시겠습니까? (Y/N): ", totalPrice);
        String confirm = scanner.next();

        if (confirm.equalsIgnoreCase("Y")) {
            System.out.println("결제가 완료되었습니다.");
        } else {
            System.out.println("결제가 취소되었습니다.");
        }

    }

}