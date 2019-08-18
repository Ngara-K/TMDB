package me.ngarak.tmdb.query;

import me.ngarak.tmdb.model.MoviesResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDB_Queries {
    @GET("movie/popular")
    Call<MoviesResult> getPopularMovies(@Query("page") int page, @Query("api_key") String api_key);

    @GET("movie/{movie_id}/similar")
    Call<MoviesResult> getSimilarMovies (@Path("movie_id") int movie_id, @Query("page") int page, @Query("api_key") String api_key);
}
