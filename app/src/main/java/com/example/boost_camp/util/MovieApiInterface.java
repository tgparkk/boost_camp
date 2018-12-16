package com.example.boost_camp.util;

import com.example.boost_camp.model.movie_material.MovieInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by park on 2018-12-07.
 */

public interface MovieApiInterface {

    @Headers({"X-Naver-Client-Id: M3T3tdsvFNuCGIAOxCbI"
            ,"X-Naver-Client-Secret: uUNFxlW8oW"})

//    @GET("movie.json?")
//    Call<MovieInfo>getMovieItems(@Query ("Query")String movieName);

    @GET("movie.json?")
    Call<MovieInfo>getMovieItemsMores(@Query ("Query")String movieName, @Query("start")Integer count);

}
