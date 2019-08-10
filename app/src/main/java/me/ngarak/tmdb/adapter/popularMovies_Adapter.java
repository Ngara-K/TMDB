package me.ngarak.tmdb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.ngarak.tmdb.PopularMovies;
import me.ngarak.tmdb.R;
import me.ngarak.tmdb.model.Result;

import static me.ngarak.tmdb.model.movieImagePathBuilder.pathBuilder;

public class popularMovies_Adapter extends RecyclerView.Adapter<popularMovies_Adapter.MovieHolder> {

    Context context;
    private final List<Result> resultList;

    public popularMovies_Adapter(PopularMovies popularMovies, List<Result> resultList) {
        this.context = popularMovies;
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
        holder.movieTitle.setText(resultList.get(position).getTitle());
        holder.releaseDate.setText(resultList.get(position).getReleaseDate());
        holder.voteCount.setText(resultList.get(position).getVoteCount());

        Glide.with(context).load(pathBuilder(resultList.get(position).getPosterPath())).into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {

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
    }

    @Override
    public void onViewRecycled(@NonNull MovieHolder holder) {
        super.onViewRecycled(holder);
    }
}
