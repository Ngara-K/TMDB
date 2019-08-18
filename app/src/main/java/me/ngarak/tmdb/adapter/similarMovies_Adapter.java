package me.ngarak.tmdb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.ngarak.tmdb.R;
import me.ngarak.tmdb.model.Movie;

import static me.ngarak.tmdb.model.movieImagePathBuilder.pathBuilder;

public class similarMovies_Adapter extends RecyclerView.Adapter<similarMovies_Adapter.MovieHolder> {

    private final List<Movie> movieList;

    //Initializing Constructor
    public similarMovies_Adapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_movies_card, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        //Bind received Data
        holder.bind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        //Defining views
        ImageView moviePoster;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.similar_movie_poster);
        }

        public void bind(final Movie movie) {
            //Using Glide library to load image
            Glide.with(itemView)
                    .load(pathBuilder(movie.getPosterPath()))
                    .placeholder(R.drawable.tmdb_placeholder)
                    .into(moviePoster);

        }
    }

    @Override
    public void onViewRecycled(@NonNull MovieHolder holder) {
        super.onViewRecycled(holder);
    }
}
