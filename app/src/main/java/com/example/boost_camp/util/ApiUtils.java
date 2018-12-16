package com.example.boost_camp.util;

/**
 * Created by park on 2018-12-08.
 */

public class ApiUtils {
    public static final String BASE_URL
            ="https://openapi.naver.com/v1/search/";
    public static MovieApiInterface getMovieApi(){
        return MovieUtil.getRetrofit(BASE_URL).create(MovieApiInterface.class);
    }
}
