package com.pcwk.ehr.movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieVO {
    private int id;
    private String movieTitle;
    private String movieGenre;
    private double movieRating;
    private int minAge;
    private Map<String, List<String>> schedule;

    public MovieVO(int id, String movieTitle, String movieGenre, double movieRating, int minAge, Map<String, List<String>> schedule) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.movieGenre = movieGenre;
        this.movieRating = movieRating;
        this.minAge = minAge;
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(double movieRating) {
        this.movieRating = movieRating;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public Map<String, List<String>> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, List<String>> schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(", ").append(movieTitle).append(", ").append(movieGenre).append(", ").append(movieRating)
                .append(", ").append(minAge).append("\n");
        if (schedule != null) {
            for (Map.Entry<String, List<String>> entry : schedule.entrySet()) {
                sb.append(entry.getKey()).append(" 상영 시간: ").append(String.join(", ", entry.getValue())).append("\n");
            }
        }
        return sb.toString();
    }
}
