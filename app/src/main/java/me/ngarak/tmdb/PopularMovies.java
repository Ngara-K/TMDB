package me.ngarak.tmdb;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.ngarak.tmdb.adapter.popularMovies_Adapter;
import me.ngarak.tmdb.model.PopularMoviesResult;
import me.ngarak.tmdb.model.Result;
import me.ngarak.tmdb.query.MovieListQuery;
import me.ngarak.tmdb.utils.InfiniteScroll;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMovies extends AppCompatActivity {

    public static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private Call<PopularMoviesResult> moviesResultCall;
    private List<Result> resultList;
    private int totalPages;
    private int currentPage = 1;
    private popularMovies_Adapter popularMoviesAdapter;
    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;

    private MovieListQuery movieListQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);

        recyclerView = findViewById(R.id.popularMoviesRecycler);

        //Initializing Retrofit Instance for Movie List
        movieListQuery = RetrofitInstance.getRetrofitInst().create(MovieListQuery.class);

        //Setting RecyclerView layout
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //RecyclerView item decorator
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
//        recyclerView.setAdapter(popularMoviesAdapter);

        //Infinite Scroll Listener
        recyclerView.addOnScrollListener(new InfiniteScroll(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                recyclerView = view;

                Log.d("TAG", "totalItemsCount: " + totalItemsCount);

                if ((page + 1) <= totalPages) {
                    loadPopularMovies(page + 1);
                }
                else {
                    Toast.makeText(PopularMovies.this, "Last Page", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Load first page
        loadPopularMovies(currentPage);
    }

    private void loadPopularMovies(final int page) {
        Log.d("TAG", "loadPopularMovies: Loading ..   " + page);
        //Get list of movies of per page
        moviesResultCall = movieListQuery.getPopularMovies(page, API_KEY);
        //Retrofit Call for list of movies
        moviesResultCall.enqueue(new Callback<PopularMoviesResult>() {
            @Override
            public void onResponse(@NonNull Call<PopularMoviesResult> call, @NonNull Response<PopularMoviesResult> response) {

                if (page == 1) {
                    if (response.body() != null) {
                        //Get list of movies to @Result class
                        resultList = getResultsList(response);
                        //Get number of pages
                        totalPages = response.body().getTotalPages();
                        Log.d("TAG", "TOTAL PAGES: " + totalPages);

                        //Setting Adapter to the RecyclerView
                        popularMoviesAdapter = new popularMovies_Adapter(resultList);
                        recyclerView.setAdapter(popularMoviesAdapter);
                    } else {
                        Toast.makeText(PopularMovies.this, "No response", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    //Appending data to the list
                    List<Result> results = getResultsList(response);
                    if (results != null) {
                        for (Result result : results) {
                            resultList.add(result);
                            popularMoviesAdapter.notifyItemInserted(resultList.size() - 1);
                        }
                    }
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

    //Get Result of the fetched data
    private List<Result> getResultsList(Response<PopularMoviesResult> response) {
        PopularMoviesResult popularMoviesResult = response.body();
        return popularMoviesResult != null ? popularMoviesResult.getResults() : null;
    }
}
