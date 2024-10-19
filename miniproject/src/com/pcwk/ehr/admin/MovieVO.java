package com.pcwk.ehr.admin;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MovieVO {
	private String title; // 제목
	private String genre; // 장르
	private int age; // 상영 등급
	private Double rating; // 평점 (null 허용)
	private Date startDate; // 상영 시작일 (null 허용)
	private Date endDate; // 상영 종료일 (null 허용)

	public MovieVO() {
		super();
	}

	public MovieVO(String title, String genre, int age, Double rating, Date startDate, Date endDate) {
		this.title = title;
		this.genre = genre;
		this.age = age;
		this.rating = rating;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, endDate, genre, rating, startDate, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovieVO other = (MovieVO) obj;
		return age == other.age && Objects.equals(endDate, other.endDate) && Objects.equals(genre, other.genre)
				&& Objects.equals(rating, other.rating) && Objects.equals(startDate, other.startDate)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		return "MovieVO [title=" + title + ", genre=" + genre + ", age=" + age + ", rating="
				+ (rating != null ? rating : "N/A") + ", startDate="
				+ (startDate != null ? sdf.format(startDate) : "N/A") + ", endDate="
				+ (endDate != null ? sdf.format(endDate) : "N/A") + "]";

	}
}
