package com.pcwk.ehr.admin;

import java.time.LocalDate;

public class MovieVO {
    private String title;
    private String genre;
    private int age;
    private Double rating;
    private LocalDate startDate;
    private String time;
    private Integer seat; // Integer로 변경하여 null 체크 가능

    public MovieVO(String title, String genre, int age, Double rating, LocalDate startDate, String time, Integer seat) {
        this.title = title;
        this.genre = genre;
        this.age = age;
        this.rating = rating;
        this.startDate = startDate != null ? startDate : LocalDate.now(); // 현재 날짜로 설정
        this.time = time;
        this.seat = (seat != null && seat >= 0) ? seat : 0; // 0 이상이면 설정, 아니면 0
    }

    public MovieVO() {
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "MovieVO{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", age=" + age +
                ", rating=" + rating +
                ", startDate=" + startDate +
                ", time='" + time + '\'' +
                ", seat=" + seat +
                '}';
    }
}
