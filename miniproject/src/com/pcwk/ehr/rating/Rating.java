package com.pcwk.ehr.rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pcwk.ehr.admin.MovieDao;
import com.pcwk.ehr.admin.MovieVO;

public class Rating {

	public List<MovieVO> rating() {
	
		MovieDao movieDao = new MovieDao();
		ArrayList<MovieVO> allMovies = movieDao.doSelectAll();
		List<Double> ratingreverse = new ArrayList<Double>(); 
		List<String> title = new ArrayList<String>();
		
		for(MovieVO movie: allMovies) {
			title.add(movie.getTitle());
			ratingreverse.add(movie.getRating()); 
		}
		
		System.out.println("\n************ 평점순으로 영화 목록 나열 ************\n");
		for(int i=0; i < title.size(); i++) {
			Collections.sort(ratingreverse, Collections.reverseOrder());
			System.out.printf("%d. %s의 평점은 %.1f 입니다.\n",(i+1),title.get(i),ratingreverse.get(i));
		}
		return allMovies;
	}

}
