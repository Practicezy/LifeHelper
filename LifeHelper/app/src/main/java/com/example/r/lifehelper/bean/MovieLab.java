package com.example.r.lifehelper.bean;

import com.example.r.lifehelper.data.MovieAsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieLab {
    private static MovieLab sMovieLab;
    private List<Movie> mMovieList;

    public static MovieLab getMovieLab() {
        if (sMovieLab == null){
            sMovieLab = new MovieLab();
        }
        return sMovieLab;
    }

    private MovieLab() {
        MovieAsyncTask asyncTask = new MovieAsyncTask();
        asyncTask.execute("http://baobab.kaiyanapp.com/api/v4/tabs/selected");
        try {
            mMovieList = asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Movie getMovie(int id){
        for (Movie movie:mMovieList
             ) {
            if (movie.getId() == id){
                return movie;
            }
        }
        return null;
    }

    public List<Movie> getMovieList() {
        return mMovieList;
    }

    public void updateMovieList(List<Movie> movieList) {
        mMovieList = movieList;
    }
}
