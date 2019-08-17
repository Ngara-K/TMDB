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
import me.ngarak.tmdb.model.Result;

import static me.ngarak.tmdb.model.movieImagePathBuilder.pathBuilder;

public class popularMovies_Adapter extends RecyclerView.Adapter<popularMovies_Adapter.MovieHolder> {

    private final MovieOnClickListener movieOnClickListener;
    private final List<Result> resultList;

    //Initializing Constructor
    public popularMovies_Adapter(List<Result> resultList, MovieOnClickListener movieOnClickListener) {
        this.movieOnClickListener = movieOnClickListener;
        this.resultList = resultList;
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
        holder.bind(resultList.get(position), movieOnClickListener);
    }

    @Override
    public int getItemCount() {
        return this.resultList.size();
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

        public void bind(final Result result, final MovieOnClickListener movieOnClickListener) {

            movieTitle.setText(result.getTitle());
            releaseDate.setText(result.getReleaseDate());
            voteCount.setText(String.valueOf(result.getVoteCount()));
            //Using Glide library to load image
            Glide.with(itemView)
                    .load(pathBuilder(result.getPosterPath()))
                    .placeholder(R.drawable.tmdb_placeholder)
                    .into(moviePoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movieOnClickListener.onClick(result);
                    Log.d("TAG", "onClick: " + result.getTitle());
                }
            });
        }
    }

    @Override
    public void onViewRecycled(@NonNull MovieHolder holder) {
        super.onViewRecycled(holder);
    }

    public interface MovieOnClickListener {
        void onClick(Result result);
    }
}
