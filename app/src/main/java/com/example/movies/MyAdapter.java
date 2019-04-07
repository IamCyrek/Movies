package com.example.movies;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private SearchResult _searchResults = new SearchResult();
    private List<Movie> _favourites = new ArrayList<>();
    private final ListOfMoviesListener _listOfMovies_listener;
    private final FragmentActivity _activity;

    public MyAdapter(ListOfMoviesListener listOfMoviesListener, FragmentActivity activity) {
        _searchResults.results = new ArrayList<>();
        _listOfMovies_listener = listOfMoviesListener;
        _activity = activity;
    }

    private void updateMovies() {
        for (Movie favourites: _favourites) {
            for (Movie oldMovie : _searchResults.results) {
                if (oldMovie.equals(favourites)) {
                    oldMovie.setIsSaved(true);
                    favourites.setIsSaved(true);
                    break;
                }
            }
        }
    }

    public void setSearchResults(SearchResult searchResults) {
        if (searchResults == null) {
            _searchResults = new SearchResult();
            _searchResults.results = new ArrayList<>();
            return;
        }

        _searchResults = searchResults;
        updateMovies();
    }

    public void setDBResults(List<Movie> movies) {
        if (movies == null)
            return;

        _favourites = movies;
        updateMovies();
    }

    public void changeMoviesToFavourites() {
        _searchResults.results = _favourites;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Movie movie = _searchResults.results.get(i);

        viewHolder.voteAverage.setText(Double.toString(movie.getVote_average()));
        viewHolder.title.setText(movie.getTitle());
        Picasso.get().load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" +
                movie.getPoster_path()).into(viewHolder.imageView);
        viewHolder.releaseDate.setText(movie.getRelease_date());
        viewHolder.isSaved.setChecked(movie.getIsSaved());
        viewHolder.isSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _listOfMovies_listener.onItemClick(((Switch) v).isChecked(), movie);
            }
        });

        if (_activity instanceof MovieListener) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MovieListener) _activity).onItemClick(movie);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (_searchResults == null || _searchResults.results == null)
            return 0;

        return _searchResults.results.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView voteAverage;
        TextView title;
        ImageView imageView;
        TextView releaseDate;
        Switch isSaved;

        ViewHolder(View itemView) {
            super(itemView);

            voteAverage = itemView.findViewById(R.id.voteAverage);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.icon);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            isSaved = itemView.findViewById(R.id.isSaved);
            isSaved.setVisibility(View.VISIBLE);
        }
    }
}
