package me.ngarak.tmdb.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.ngarak.tmdb.R;
import me.ngarak.tmdb.model.Movie;

import static me.ngarak.tmdb.model.movieImagePathBuilder.pathBuilder;

public class popularMovies_Adapter extends RecyclerView.Adapter<popularMovies_Adapter.MovieHolder> {

    private final MovieOnClickListener movieOnClickListener;
    private final List<Movie> movieList;

    //Initializing Constructor
    public popularMovies_Adapter(List<Movie> movieList, MovieOnClickListener movieOnClickListener) {
        this.movieOnClickListener = movieOnClickListener;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_card, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        //Bind received Data
        holder.bind(movieList.get(position), movieOnClickListener);
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        //Defining views
        TextView movieTitle;
        TextView releaseDate;
        TextView voteCount;
        ImageView moviePoster;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            movieTitle = itemView.findViewById(R.id.movie_title);
            releaseDate = itemView.findViewById(R.id.release_date);
            voteCount = itemView.findViewById(R.id.vote_count);
            moviePoster = itemView.findViewById(R.id.movie_poster);
        }

        public void bind(final Movie movie, final MovieOnClickListener movieOnClickListener) {

            movieTitle.setText(movie.getTitle());
            releaseDate.setText(movie.getReleaseDate());
            voteCount.setText(String.valueOf(movie.getVoteCount()));
            //Using Glide library to load image
            Glide.with(itemView)
                    .load(pathBuilder(movie.getPosterPath()))
                    .placeholder(R.drawable.tmdb_placeholder)
                    .into(moviePoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movieOnClickListener.onClick(movie);
                    Log.d("TAG", "onClick: " + movie.getTitle());
                }
            });
        }
    }

    @Override
    public void onViewRecycled(@NonNull MovieHolder holder) {
        super.onViewRecycled(holder);
    }

    public interface MovieOnClickListener {
        void onClick(Movie movie);
    }
}
