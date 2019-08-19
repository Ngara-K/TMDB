package me.ngarak.tmdb.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/*
 * POJO CLASS FOR RETRIEVING MOVIE DETAILS
 * GETTERS AND SETTERS
 * */
@Entity (tableName = "popular_movies")
public class Movie implements Serializable {

    @ColumnInfo (name = "vote_count")
    @SerializedName ("vote_count")
    @Expose
    private Integer voteCount;

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id")
    @SerializedName ("id")
    @Expose
    private Integer id;

    @ColumnInfo (name = "video")
    @SerializedName ("video")
    @Expose
    private Boolean video;

    @ColumnInfo (name = "vote_average")
    @SerializedName ("vote_average")
    @Expose
    private Double voteAverage;

    @ColumnInfo (name = "title")
    @SerializedName ("title")
    @Expose
    private String title;

    @ColumnInfo (name = "popularity")
    @SerializedName ("popularity")
    @Expose
    private Double popularity;

    @ColumnInfo (name = "poster_path")
    @SerializedName ("poster_path")
    @Expose
    private String posterPath;

    @ColumnInfo (name = "original_language")
    @SerializedName ("original_language")
    @Expose
    private String originalLanguage;

    @ColumnInfo (name = "original_title")
    @SerializedName ("original_title")
    @Expose
    private String originalTitle;

//    @ColumnInfo (name = "genre_ids")
//    @SerializedName ("genre_ids")
//    @Expose
//    private List<Integer> genreIds = null;

    @ColumnInfo (name = "backdrop_path")
    @SerializedName ("backdrop_path")
    @Expose
    private String backdropPath;

    @ColumnInfo (name = "adult")
    @SerializedName ("adult")
    @Expose
    private Boolean adult;

    @ColumnInfo (name = "overview")
    @SerializedName ("overview")
    @Expose
    private String overview;

    @ColumnInfo (name = "release_date")
    @SerializedName ("release_date")
    @Expose
    private String releaseDate;

    private final static long serialVersionUID = -6786212654509805234L;

    public Movie () {
    }

    public Movie (Integer voteCount, Integer id, Boolean video, Double voteAverage, String title, Double popularity, String posterPath, String originalLanguage, String originalTitle, List<Integer> genreIds, String backdropPath, Boolean adult, String overview, String releaseDate) {
        super();
        this.voteCount = voteCount;
        this.id = id;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
//        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public Integer getVoteCount () {
        return voteCount;
    }

    public void setVoteCount (Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Movie withVoteCount (Integer voteCount) {
        this.voteCount = voteCount;
        return this;
    }

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Movie withId (Integer id) {
        this.id = id;
        return this;
    }

    public Boolean getVideo () {
        return video;
    }

    public void setVideo (Boolean video) {
        this.video = video;
    }

    public Movie withVideo (Boolean video) {
        this.video = video;
        return this;
    }

    public Double getVoteAverage () {
        return voteAverage;
    }

    public void setVoteAverage (Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Movie withVoteAverage (Double voteAverage) {
        this.voteAverage = voteAverage;
        return this;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public Movie withTitle (String title) {
        this.title = title;
        return this;
    }

    public Double getPopularity () {
        return popularity;
    }

    public void setPopularity (Double popularity) {
        this.popularity = popularity;
    }

    public Movie withPopularity (Double popularity) {
        this.popularity = popularity;
        return this;
    }

    public String getPosterPath () {
        return posterPath;
    }

    public void setPosterPath (String posterPath) {
        this.posterPath = posterPath;
    }

    public Movie withPosterPath (String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public String getOriginalLanguage () {
        return originalLanguage;
    }

    public void setOriginalLanguage (String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Movie withOriginalLanguage (String originalLanguage) {
        this.originalLanguage = originalLanguage;
        return this;
    }

    public String getOriginalTitle () {
        return originalTitle;
    }

    public void setOriginalTitle (String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Movie withOriginalTitle (String originalTitle) {
        this.originalTitle = originalTitle;
        return this;
    }

//    public List<Integer> getGenreIds () {
//        return genreIds;
//    }
//
//    public void setGenreIds (List<Integer> genreIds) {
//        this.genreIds = genreIds;
//    }
//
//    public Movie withGenreIds (List<Integer> genreIds) {
//        this.genreIds = genreIds;
//        return this;
//    }

    public String getBackdropPath () {
        return backdropPath;
    }

    public void setBackdropPath (String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Movie withBackdropPath (String backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    public Boolean getAdult () {
        return adult;
    }

    public void setAdult (Boolean adult) {
        this.adult = adult;
    }

    public Movie withAdult (Boolean adult) {
        this.adult = adult;
        return this;
    }

    public String getOverview () {
        return overview;
    }

    public void setOverview (String overview) {
        this.overview = overview;
    }

    public Movie withOverview (String overview) {
        this.overview = overview;
        return this;
    }

    public String getReleaseDate () {
        return releaseDate;
    }

    public void setReleaseDate (String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Movie withReleaseDate (String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }
}
