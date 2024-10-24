package com.pcwk.ehr.KCH;

import java.util.Scanner;

import com.pcwk.ehr.cmn.PLog;

public class Main implements PLog {

    MemberController controller;

    public Main() {
        controller = new MemberController();
        // 메뉴 선택
        doActionMenu();
    }

    // 메뉴 선택 
    public void doActionMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // main 메뉴
            System.out.println(menu());
            System.out.print("Menu를 선택 하세요.>");
            String menu = scanner.nextLine().trim();

            switch (menu) {
                case "1": // 회원 단건 조회
                    MemberVO outVO = controller.doSelectOne();
                    if (null == outVO) {
                        System.out.println("회원 단건 조회 실패!");
                    } else {
                        System.out.printf("조회 회원: %s%n", outVO);
                    }
                    break;
                case "2": // 회원 가입
                    int flag = controller.doSave();
                    if (1 == flag) {
                        System.out.println("회원 가입 되었습니다.");
                    } else {
                        System.out.println("회원 가입 실패!");
                    }
                    break;
                case "3": // 회원 수정
                    int flagUpdate = controller.doUpdate();
                    if (2 == flagUpdate) {
                        System.out.println("회원정보가 수정 되었습니다.");
                    } else {
                        System.out.println("회원 수정 실패!");
                    }
                    break;
                case "4": // 회원 삭제
                    int flagDelete = controller.doDelete();
                    if (1 == flagDelete) {
                        System.out.println("회원이 삭제 되었습니다.");
                    } else {
                        System.out.println("회원 삭제 실패!");
                    }
                    break;
                case "5": // 회원 관리 종료 후 관리자 메뉴로 돌아감
                    System.out.println("회원 관리 프로그램을 종료합니다.");
                    return; // 관리자 메뉴로 돌아가기 위해 `return` 사용
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택해주세요.");
                    break;
            }
        }
    }

    // 메뉴
    public String menu() {
        StringBuilder sb = new StringBuilder(2000);
        sb.append(" +-+-+-+-+ +-+-+-+-+ +-+-+-+-+-+-+-+ \n");
        sb.append(" |M|i|n|i| |J|a|v|a| |P|r|o|j|e|c|t| \n");
        sb.append(" +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ \n");
        sb.append(" |2|0|2|4|.|1|0|.|2|5|               \n");
        sb.append(" +-+-+-+-+-+-+-+-+-+-+               \n");
        sb.append("  *** 회원 관리 프로그램 ***               \n");
        sb.append("  1. 회원 단건 조회:                     \n");
        sb.append("  2. 회원 단건 저장:                     \n"); 
        sb.append("  3. 회원 수정:                        \n");
        sb.append("  4. 회원 삭제:                        \n");  
        sb.append("  5. 종료 :                           \n");

        return sb.toString();
    }

    public static void main(String[] args) {
        new Main();
    }
}
