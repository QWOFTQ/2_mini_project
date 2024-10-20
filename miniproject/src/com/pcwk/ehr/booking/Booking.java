package com.pcwk.ehr.booking;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import com.pcwk.ehr.admin.MovieDao;
import com.pcwk.ehr.admin.MovieVO;

public class Booking {

    // 영화 예매 메서드
	public void bookingMovie() {
	    MovieDao movieDao = new MovieDao();
	    ArrayList<MovieVO> allMovies = movieDao.doSelectAll();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
	    Date currentTime = new Date();
	    
	    System.out.println("현재 시간: " + sdf.format(currentTime));
	    System.out.println("예매 가능한 영화 목록:");
	    
	    ArrayList<MovieVO> availableMovies = new ArrayList<>();
	    
	    for (MovieVO movie : allMovies) {
	        Date startDate = movie.getStartDate();
	        // 현재 시간이 영화 시작 시간보다 이후인 경우만 추가
	        if (startDate != null && currentTime.before(startDate)) {
	            availableMovies.add(movie);
	        }
	    }

	    if (availableMovies.isEmpty()) {
	        System.out.println("현재 예매 가능한 영화가 없습니다.");
	        return; // 예매 종료
	    }

	    for (MovieVO movie : availableMovies) {
	        System.out.println(movie);
	    }

	    try (Scanner scanner = new Scanner(System.in)) {
	        System.out.print("예매할 영화의 제목을 입력하세요: ");
	        String selectedTitle = scanner.nextLine();

	        boolean movieBooked = false;
	        for (MovieVO movie : availableMovies) {
	            if (movie.getTitle().trim().equalsIgnoreCase(selectedTitle.trim())) {
	                System.out.println("영화 '" + selectedTitle + "'이(가) 예매되었습니다.");
	                movieBooked = true;
	                break;
	            }
	        }

	        if (!movieBooked) {
	            System.out.println("해당 영화는 존재하지 않거나 예매할 수 없습니다. 입력한 제목: " + selectedTitle);
	        }
	    } catch (Exception e) {
	        System.out.println("오류가 발생했습니다: " + e.getMessage());
	    }
	}

}
