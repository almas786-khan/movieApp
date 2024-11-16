package com.example.a2_khan.model;

public class MovieModel {
    private String title;
    private String year;
    private String plot;
    private String posterUrl;
    private String movieId;

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    private String imdbRating;


    public MovieModel(String title, String year, String posterUrl, String movieId) {
        this.title = title;
        this.year = year;
        this.posterUrl = posterUrl;
        this.movieId = movieId;
    }

    public MovieModel(String title, String year, String posterUrl, String plot, String movieId) {
        this.title = title;
        this.year = year;
        this.posterUrl = posterUrl;
        this.plot = plot;
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

}
