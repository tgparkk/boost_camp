package com.example.boost_camp.movie;

import com.example.boost_camp.model.movie_material.MovieInfo;
/**
 * Created by park on 2018-12-07.
 */

public interface MovieContract {
    interface View{
        void showMovieInfoResult(MovieInfo movieInfo);
        void showLoadError(String message);
        void loadingStart();
        void loadingEnd();
        void reload(String movieName);
    }
    interface UserActionListener{
        //void loadMovieInfoData();
        void showSearchResult(String movieName);
    }
}
