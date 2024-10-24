package com.pcwk.ehr.rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.pcwk.ehr.movie.MovieDao;
import com.pcwk.ehr.movie.MovieVO;

public class Rating {

	private MovieDao movieDao;

	public Rating() {
		movieDao = new MovieDao();
	}

	public void rating() {
		List<MovieVO> allMovies = movieDao.getAllMovies(); // Get all movies

		// Movie title and ratings map
		Map<String, Double> movieRatings = new HashMap<>();

		// Keep only the last rating for movies with duplicate titles
		for (MovieVO movie : allMovies) {
			movieRatings.put(movie.getMovieTitle(), movie.getMovieRating());
		}

		// Create a list from the map
		List<Map.Entry<String, Double>> movieRatingList = new ArrayList<>(movieRatings.entrySet());

		// Sort by rating in descending order
		Collections.sort(movieRatingList, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

		System.out.println("\n************ 평점순으로 영화 목록 나열 ************\n");

		// Output the sorted data
		for (int i = 0; i < movieRatingList.size(); i++) {
			Map.Entry<String, Double> entry = movieRatingList.get(i);
			System.out.printf("%d. %s의 평점은 %.1f 입니다.\n", (i + 1), entry.getKey(), entry.getValue());
		}
	}

}
