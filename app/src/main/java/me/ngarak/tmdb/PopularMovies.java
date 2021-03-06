package me.ngarak.tmdb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import me.ngarak.tmdb.adapter.popularMovies_Adapter;
import me.ngarak.tmdb.adapter.popularMovies_Adapter.MovieOnClickListener;
import me.ngarak.tmdb.db.Database;
import me.ngarak.tmdb.db.MoviesDAO;
import me.ngarak.tmdb.model.Movie;
import me.ngarak.tmdb.model.MoviesResult;
import me.ngarak.tmdb.query.TMDB_Queries;
import me.ngarak.tmdb.utils.CSVWriter;
import me.ngarak.tmdb.utils.InfiniteScroll;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMovies extends AppCompatActivity {

    public static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private Call<MoviesResult> moviesResultCall;
    private List<Movie> movieList;
    private int totalPages;
    private int currentPage = 1;
    private popularMovies_Adapter popularMoviesAdapter;
    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;

    private TMDB_Queries tmdb_queries;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);

        recyclerView = findViewById(R.id.popularMoviesRecycler);

        //Initializing Retrofit Instance for Movie List
        tmdb_queries = RetrofitInstance.getRetrofitInst().create(TMDB_Queries.class);

        //Setting RecyclerView layout
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //RecyclerView item decorator
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        requestPermission();

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
        moviesResultCall = tmdb_queries.getPopularMovies(page, API_KEY);
        //Retrofit Call for list of movies
        moviesResultCall.enqueue(new Callback<MoviesResult>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResult> call, @NonNull Response<MoviesResult> response) {

                if (page == 1) {
                    if (response.body() != null) {
                        //Get list of movies to @Result class
                        movieList = getResultsList(response);
                        //Get number of pages
                        totalPages = response.body().getTotalPages();
                        Log.d("TAG", "TOTAL PAGES: " + totalPages);

                        //Setting Adapter to the RecyclerView
                        popularMoviesAdapter = new popularMovies_Adapter(movieList, new MovieOnClickListener() {
                            @Override
                            public void onClick(Movie movie) {
                                Log.d("TAG", "onClick_ID: " + movie.getId());

                                requestPermission();
                                addToSQLITE (movie);

                                Intent intent = new Intent(PopularMovies.this, MovieDetails.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("movie", movie);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                        recyclerView.setAdapter(popularMoviesAdapter);
                    } else {
                        Toast.makeText(PopularMovies.this, "No response", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    //Appending data to the list
                    List<Movie> movies = getResultsList(response);
                    if (movies != null) {
                        for (Movie movie : movies) {
                            movieList.add(movie);
                            popularMoviesAdapter.notifyItemInserted(movies.size() - 1);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResult> call, @NonNull Throwable t) {
                //Log and toast on failure
                Log.d("TAG", "onFailure: " + t);
                Toast.makeText(PopularMovies.this, "Error : " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestPermission() {

        AndPermission.with(PopularMovies.this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
//                        Toast.makeText(PopularMovies.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
//                Toast.makeText(PopularMovies.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                requestPermission();
            }
        }).start();
    }

    private void addToSQLITE (Movie movie) {
        database = Database.getInstance(PopularMovies.this);
        database.moviesDAO().InsertMovie(movie);

        LiveData<List<Movie>> movieList = database.moviesDAO().getMovies();
        movieList.observeForever(new Observer<List<Movie>>() {
            @Override
            public void onChanged (List<Movie> movieList) {
                Log.d("TAG", "onChanged: " + movieList.size());
                exportToSCV();
            }

            private void exportToSCV () {

                //directory to save scv file
                File exportDir = new File(Environment.getExternalStorageDirectory(), "tmdb");
                if (!exportDir.exists()) {
                    exportDir.mkdirs();
                }

                File file = new File(exportDir, "tmdb" + ".csv");
                try {
                    file.createNewFile();
                    CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                    //selecting from db
                    Cursor curCSV = database.query("SELECT * FROM " + "popular_movies", null);
                    csvWrite.writeNext(curCSV.getColumnNames());
                    while (curCSV.moveToNext()) {
                       
                        String[] arrStr = new String[curCSV.getColumnCount()];
                        for (int i = 0; i < curCSV.getColumnCount() - 1; i++)
                            arrStr[i] = curCSV.getString(i);
                        csvWrite.writeNext(arrStr);
                    }
                    csvWrite.close();
                    curCSV.close();
                    Log.d("TAG", "exportToSCV: " + exportDir.getAbsolutePath());
                } catch (Exception sqlEx) {
                    Log.e("LOG_TAG", sqlEx.getMessage(), sqlEx);
                }
            }
        });
    }

    //Get Result of the fetched data
    private List<Movie> getResultsList(Response<MoviesResult> response) {
        MoviesResult popularMoviesResult = response.body();
        return popularMoviesResult != null ? popularMoviesResult.getMovies() : null;
    }

}
