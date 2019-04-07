package com.example.movies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieFragment extends Fragment {
    private static final String MOVIE = "movie";

    private Movie _movie;

    public static MovieFragment newInstance(final Movie movie) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putSerializable(MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _movie = (Movie) getArguments().getSerializable(MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        ((TextView)view.findViewById(R.id.title)).setText(_movie.getTitle());

        ((TextView)view.findViewById(R.id.voteAverage)).setText(_movie.getVote_average().toString());
        ((TextView)view.findViewById(R.id.title)).setText(_movie.getTitle());
        Picasso.get().load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" +
                _movie.getPoster_path()).into((ImageView) view.findViewById(R.id.icon));
        ((TextView)view.findViewById(R.id.releaseDate)).setText(_movie.getRelease_date());
        ((TextView)view.findViewById(R.id.overview)).setText(_movie.getOverview());

        return view;
    }
}
