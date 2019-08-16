package me.ngarak.tmdb;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    //Defining and declaring base url for listening
    private static Retrofit retrofit;
    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";

    public static Retrofit getRetrofitInst() {

        //Initializing Retrofit instance
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(TMDB_BASE_URL)
                    .build();
        }
        return retrofit;
    }
}
