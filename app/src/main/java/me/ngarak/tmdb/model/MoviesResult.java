package me.ngarak.tmdb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/*
* * POJO CLASS FOR RETRIEVING LIST OF POPULAR MOVIES // GETTERS AND SETTERS
* */

public class MoviesResult implements Serializable {

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    @SerializedName("results")
    @Expose
    private List<Movie> movies = null;
    private final static long serialVersionUID = -995812242794266891L;

    public MoviesResult() {
    }

    public MoviesResult(int page, int totalResults, int totalPages, List<Movie> movies) {
        super();
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.movies = movies;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public MoviesResult withPage(int page) {
        this.page = page;
        return this;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public MoviesResult withTotalResults(int totalResults) {
        this.totalResults = totalResults;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public MoviesResult withTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public MoviesResult withResults(List<Movie> movies) {
        this.movies = movies;
        return this;
    }
}
