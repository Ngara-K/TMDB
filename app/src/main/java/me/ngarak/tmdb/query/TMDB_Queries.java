package me.ngarak.tmdb.query;

import me.ngarak.tmdb.model.MoviesResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDB_Queries {
    @GET("movie/popular")
    Call<MoviesResult> getPopularMovies(@Query("page") int page, @Query("api_key") String api_key);
}
