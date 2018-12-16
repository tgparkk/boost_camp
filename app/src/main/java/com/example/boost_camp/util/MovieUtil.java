package com.example.boost_camp.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by park on 2018-12-07.
 */

public class MovieUtil {

    private static Retrofit retrofit=null;
    public static Retrofit getRetrofit(String baseUrl){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
