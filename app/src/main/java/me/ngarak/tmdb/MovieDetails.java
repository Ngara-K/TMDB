package me.ngarak.tmdb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.ngarak.tmdb.adapter.similarMovies_Adapter;
import me.ngarak.tmdb.model.Movie;
import me.ngarak.tmdb.model.MoviesResult;
import me.ngarak.tmdb.query.TMDB_Queries;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.ngarak.tmdb.model.movieImagePathBuilder.pathBuilder;

public class MovieDetails extends AppCompatActivity {

    Movie movie;
    ImageView bigHero;
    TextView title, overview;
    RecyclerView recyclerView;
    Call<MoviesResult> moviesResultCall;
    TMDB_Queries tmdb_queries;
    similarMovies_Adapter similarMovies_adapter;
    List<Movie> movieList;
    private int currentPage = 1;
    private int totalPages;

    public static final String API_KEY = BuildConfig.TMDB_API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Initializing Retrofit Instance for Similar Movie List
        tmdb_queries = RetrofitInstance.getRetrofitInst().create(TMDB_Queries.class);

        recyclerView = findViewById(R.id.similarMoviesRecyclerView);

        bigHero = findViewById(R.id.big_hero);
        title = findViewById(R.id._title);
        overview = findViewById(R.id.overview);

        //RecyclerView item decorator
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(itemDecoration);

        //Get Data from Bundle PopularMovies Activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        movie = (Movie) bundle.getSerializable("movie");

        //Get Movie Details
        getMovieDetails(movie.getTitle(), movie.getOverview(),movie.getBackdropPath());

        //Get Similar Movies
        getSimilarMovies(movie.getId(), currentPage);
    }

    private void getMovieDetails(String _title, String _overview, String backdropPath) {
        title.setText(_title);
        overview.setText(_overview);
        Glide.with(this)
                .load(pathBuilder(backdropPath))
                .placeholder(R.drawable.tmdb_placeholder)
                .into(bigHero);
    }

    private void getSimilarMovies(Integer movie_id, final int currentPage) {
        //Get list of movies of per page
        moviesResultCall = tmdb_queries.getSimilarMovies(movie_id, currentPage, API_KEY);

        moviesResultCall.enqueue(new Callback<MoviesResult>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResult> call, @NonNull Response<MoviesResult> response) {

                if (currentPage == 1) {
                    if (response.body() != null) {
                        //Get list of movies to @Result class
                        movieList = getResultsList(response);
                        //Get number of pages
                        totalPages = response.body().getTotalPages();
                        Log.d("TAG", "TOTAL PAGES: " + totalPages);

                        //Setting Adapter to the RecyclerView
                        similarMovies_adapter = new similarMovies_Adapter(movieList);
                        recyclerView.setAdapter(similarMovies_adapter);
                    } else {
                        Toast.makeText(MovieDetails.this, "No response", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    //Appending data to the list
                    List<Movie> movies = getResultsList(response);
                    if (movies != null) {
                        for (Movie movie : movies) {
                            movieList.add(movie);
                            similarMovies_adapter.notifyItemInserted(movieList.size() - 1);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResult> call, @NonNull Throwable t) {
                //Log and toast on failure
                Log.d("TAG", "onFailure: " + t);
                Toast.makeText(MovieDetails.this, "Error : " + t, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private List<Movie> getResultsList(Response<MoviesResult> response) {
        MoviesResult similarMoviesResult = response.body();
        return similarMoviesResult != null ? similarMoviesResult.getMovies() : null;
    }
}