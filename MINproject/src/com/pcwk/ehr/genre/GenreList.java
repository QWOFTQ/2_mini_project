package com.pcwk.ehr.genre;

import com.pcwk.ehr.movie.MovieDao;
import com.pcwk.ehr.movie.MovieVO;

import java.util.List;
import java.util.stream.Collectors;

public class GenreList {
    private MovieDao movieDao;

    public GenreList(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public void movieAll(String genre) {
        List<MovieVO> movies = movieDao.getAllMovies(); // 영화 목록 가져오기
        List<MovieVO> filteredMovies = movies.stream()
                .filter(movie -> movie.getMovieGenre().equalsIgnoreCase(genre)) // 장르 필터링
                .collect(Collectors.toList());

        if (filteredMovies.isEmpty()) {
            System.out.println("해당 장르의 영화가 없습니다.");
        } else {
            System.out.println("장르: " + genre);
            for (MovieVO movie : filteredMovies) {
                System.out.println(movie.getMovieTitle());
            }
        }
    }
}
