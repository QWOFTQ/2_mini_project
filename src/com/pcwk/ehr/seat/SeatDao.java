package com.pcwk.ehr.seat;

import java.util.ArrayList;
import java.util.List;

public class SeatDao {

	private final String fileName = "C:\\2024_09_09\\01_JAVA\\WORKSPACE\\miniproject\\seat.csv";
	
	public static List<SeatVO> seat = new ArrayList<SeatVO>();
	
	public SeatDao() {
		seat = new ArrayList<SeatVO>();
		initalizeSeat();
	}

	private void initalizeSeat() {
		// TODO Auto-generated method stub
		
	}
	
}
