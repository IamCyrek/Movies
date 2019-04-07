package com.example.movies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.List;

interface AsyncTaskCallBack {
    void onSearchResultReceived(SearchResult searchResult);
    void onDBResultReceived(List<Movie> list);
}

interface MovieListener {
    void onItemClick(final Movie movie);
}

interface ListOfMoviesListener {
    void onItemClick(boolean isAdding, final Movie movie);
}

public class MainActivity extends AppCompatActivity implements MovieListener {
    Fragment _listOfMovies;
    Fragment _movieFragment;
    FragmentTransaction _transaction;
    private static final String MOVIE = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        _listOfMovies = new ListOfMovies();

        _transaction = getSupportFragmentManager().beginTransaction();
        _transaction.replace(R.id.listOfMovies, _listOfMovies);
        _transaction.commit();
    }

    @Override
    public void onItemClick(Movie movie) {
        _movieFragment = MovieFragment.newInstance(movie);

        _transaction = getSupportFragmentManager().beginTransaction();
        _transaction.replace(R.id.listOfMovies, _movieFragment);
        _transaction.addToBackStack(null);
        _transaction.commit();
    }
}
