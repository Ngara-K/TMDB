package me.ngarak.tmdb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.ngarak.tmdb.adapter.popularMovies_Adapter;
import me.ngarak.tmdb.model.Movie;
import me.ngarak.tmdb.model.MoviesResult;
import me.ngarak.tmdb.query.TMDB_Queries;
import retrofit2.Call;

import static me.ngarak.tmdb.model.movieImagePathBuilder.pathBuilder;

public class MovieDetails extends AppCompatActivity {

    Movie movie;
    ImageView bigHero;
    TextView title, overview;
    RecyclerView recyclerView;
    Call<MoviesResult> moviesResultCall;
    TMDB_Queries tmdb_queries;
    popularMovies_Adapter popularMoviesAdapter;
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

        bigHero = findViewById(R.id.big_hero);
        title = findViewById(R.id._title);
        overview = findViewById(R.id.overview);

        //RecyclerView item decorator
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        //Get Data from Bundle PopularMovies Activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        movie = (Movie) bundle.getSerializable("result");

        //Get Movie Details
        getMovieDetails(movie.getTitle(), movie.getOverview(),movie.getBackdropPath());
    }

    private void getMovieDetails(String _title, String _overview, String backdropPath) {
        title.setText(_title);
        overview.setText(_overview);
        Glide.with(this)
                .load(pathBuilder(backdropPath))
                .placeholder(R.drawable.tmdb_placeholder)
                .into(bigHero);
    }
}