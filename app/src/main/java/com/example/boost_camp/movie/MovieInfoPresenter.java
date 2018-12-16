package com.example.boost_camp.movie;

import com.example.boost_camp.model.movie_material.MovieInfo;

/**
 * Created by park on 2018-12-07.
 */

public class MovieInfoPresenter implements MovieContract.UserActionListener {

    private final MovieContract.View mView;
    //리사이클러 뷰
    public MovieInfoPresenter(MovieContract.View view) {
        mView=view;
    }

    @Override
    public void showSearchResult(String movieName) {

    }

}