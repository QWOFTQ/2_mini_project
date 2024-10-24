package com.pcwk.ehr.member;

public class Movie {
	private String title;
	private String genre;
	private int age;
	private double rating;

	public Movie(String title, String genre, int age, double rating) {
		this.title = title;   // 제목
		this.genre = genre;   // 장르
		this.age =   age;     // 나이
		this.rating = rating; // 평점

	}

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
	
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}


	@Override
	public String toString() {
		return "Movie [title=" + title + ", genre=" + genre + ", age=" + age + ", rating=" + rating + "]";
	}



}