package com.pcwk.ehr.genre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.pcwk.ehr.admin.MovieDaoMain;
import com.pcwk.ehr.booking.Booking;
import com.pcwk.ehr.booking.BookingMain;

public class GenreMain {
	public void showMenu() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		GenreList genre = new GenreList(); // GenreList 인스턴스 생성
		while (true) {
			try {
				System.out.println("\n************ 장르 조회 시스템 ************");
				System.out.println("1. 장르별 영화 조회");
				System.out.println("2. 예매");
				System.out.println("3. 메인 메뉴");
				System.out.println("4. 종료");
				System.out.print("메뉴를 선택하세요: ");

				int choice = Integer.parseInt(reader.readLine());

				switch (choice) {
				case 1:
					System.out.print("조회할 장르를 입력하세요: ");
					String genreInput = reader.readLine(); // 사용자로부터 장르 입력 받기
					GenreList.movieAll(genreInput); // 입력받은 장르에 따라 영화 출력
					break;

				case 2:
					System.out.print("예매: ");
					Booking booking = new Booking();
					booking.bookingMovie();
					
					break;
					
				case 3:
					// 메인 메뉴 구현 후 테스트 필요
					System.out.println("메인 메뉴로 돌아갑니다.");
					MovieDaoMain movieDaoMain = new MovieDaoMain(); // 인스턴스 생성
					movieDaoMain.showMenu(); // 인스턴스를 통해 메소드 호출
					break;

				case 4:
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
		GenreMain main = new GenreMain();
		main.showMenu(); // 메뉴 표시
	}
}
