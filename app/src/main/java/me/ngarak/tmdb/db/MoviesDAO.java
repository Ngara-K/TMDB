package me.ngarak.tmdb.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import me.ngarak.tmdb.model.Movie;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MoviesDAO {
    @Insert (onConflict = REPLACE)
    void InsertMovie (Movie movie);

    @Query ("select * from popular_movies")
    LiveData<List<Movie>> getMovies ();
}
