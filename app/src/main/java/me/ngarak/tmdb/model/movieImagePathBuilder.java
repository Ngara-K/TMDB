package me.ngarak.tmdb.model;

public class movieImagePathBuilder {

    public static String pathBuilder (String imagePath) {
        return "https://image.tmdb.org/t/p/" +
                "w500" +
                imagePath;
    }
}
