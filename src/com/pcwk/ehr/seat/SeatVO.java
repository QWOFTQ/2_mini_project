package com.pcwk.ehr.seat;

public class SeatVO {
	private int row; // 좌석의 행
	private int column; // 좌석의 열
	private boolean reserved; // 예약 여부

	public SeatVO(int row, int columnd, int column) {
		super();
		this.row = row;
		this.column = column;

	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	@Override
	public String toString() {
		return "SeatVO [row=" + row + ", column=" + column + ", reserved=" + reserved + "]";

	}

}
