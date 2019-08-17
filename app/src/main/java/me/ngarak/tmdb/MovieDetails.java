package me.ngarak.tmdb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import me.ngarak.tmdb.model.Result;

import static me.ngarak.tmdb.model.movieImagePathBuilder.pathBuilder;

public class MovieDetails extends AppCompatActivity {

    Result result;
    ImageView bigHero;
    TextView title, overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        bigHero = findViewById(R.id.big_hero);
        title = findViewById(R.id._title);
        overview = findViewById(R.id.overview);

        //Get Data from Bundle PopularMovies Activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        result = (Result) bundle.getSerializable("result");

        //Get Movie Details
        getMovieDetails(result.getTitle(), result.getOverview(),result.getBackdropPath());

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
