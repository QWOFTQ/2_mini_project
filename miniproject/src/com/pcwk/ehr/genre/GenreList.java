package com.pcwk.ehr.genre;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.pcwk.ehr.admin.MovieDao;
import com.pcwk.ehr.admin.MovieVO;

public class GenreList {

    public static void movieAll(String genre) {
        // 모든 영화 조회
        MovieDao movieDao = new MovieDao();
        ArrayList<MovieVO> allMovies = movieDao.doSelectAll();

        System.out.println(genre + " 장르 영화 출력");
        Set<MovieVO> uniqueMovies = new HashSet<>(); // 중복 제거를 위한 Set

        for (MovieVO movie : allMovies) {
            if (genre.equals(movie.getGenre())) {
                uniqueMovies.add(movie); // Set에 추가하여 중복 제거
            }
        }

        // 중복이 제거된 영화 출력
        for (MovieVO movie : uniqueMovies) {
            System.out.println(movie);
        }

        if (uniqueMovies.isEmpty()) {
            System.out.println("해당 장르의 영화가 없습니다.");
        }
    }
}
