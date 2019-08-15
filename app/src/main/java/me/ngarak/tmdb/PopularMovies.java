package me.ngarak.tmdb;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.ngarak.tmdb.adapter.popularMovies_Adapter;
import me.ngarak.tmdb.model.PopularMoviesResult;
import me.ngarak.tmdb.model.Result;
import me.ngarak.tmdb.query.MovieListQuery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMovies extends AppCompatActivity {

    public static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private Call<PopularMoviesResult> moviesResultCall;
    private List<Result> resultList;
    private int totalPages;
    private int page;
    private int totalResult;
    private popularMovies_Adapter popularMoviesAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);

        recyclerView = findViewById(R.id.popularMoviesRecycler);

        //Setting RecyclerView layout
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //RecyclerView item decorator
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        //Initializing Instance
        MovieListQuery movieListQuery = RetrofitInstance.getRetrofitInst().create(MovieListQuery.class);
        //Get list of movies of 1 page
        moviesResultCall = movieListQuery.getPopularMovies(1, API_KEY);
        //Retrofit Call for list of movies
        moviesResultCall.enqueue(new Callback<PopularMoviesResult>() {
            @Override
            public void onResponse(@NonNull Call<PopularMoviesResult> call, @NonNull Response<PopularMoviesResult> response) {

                if (response.body() != null) {
                    //Get list of movies to @Result class
                    resultList = response.body().getResults();
                    //Get number of pages
                    totalPages = response.body().getTotalPages();
                    //get number of results per page
                    totalResult = response.body().getTotalResults();

                    //Setting Adapter to the RecyclerView
                    popularMoviesAdapter = new popularMovies_Adapter(PopularMovies.this, resultList);
                    recyclerView.setAdapter(popularMoviesAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PopularMoviesResult> call, @NonNull Throwable t) {
                //Log and toast on failure
                Log.d("TAG", "onFailure: " + t);
                Toast.makeText(PopularMovies.this, "Error : " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
