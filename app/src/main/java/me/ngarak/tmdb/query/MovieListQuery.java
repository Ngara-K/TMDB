package me.ngarak.tmdb.query;

import me.ngarak.tmdb.model.PopularMoviesResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieListQuery {
    @GET("/movie/popular")
    Call<PopularMoviesResult> getPopularMovies (@Query("page") int page, @Query("api_key") String api_key);
}
