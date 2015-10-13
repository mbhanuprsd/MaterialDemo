package com.xcubelabs.bhanuprasadm.materialdemo.extras;

import com.xcubelabs.bhanuprasadm.materialdemo.pojo.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by bhanuprasadm on 13/10/15.
 */
public class MovieSorter {
    public void sortListByName(ArrayList<Movie> movies){
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
    }

    public void sortListByDate(ArrayList<Movie> movies){
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return rhs.getReleaseDateTheater().compareTo(lhs.getReleaseDateTheater());
            }
        });
    }

    public void sortListByRating(ArrayList<Movie> movies){
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return rhs.getAudienceScore() - lhs.getAudienceScore();
            }
        });
    }
}
