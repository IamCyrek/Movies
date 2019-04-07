package com.example.movies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import static com.example.movies.MyAsyncTaskHelper.*;

public class ListOfMovies extends Fragment implements
        AsyncTaskCallBack, ListOfMoviesListener, View.OnClickListener {
    private MyAdapter _myAdapter;
    private final String DATABASE_NAME = "Favourites";
    private Menu _menu;

    public ListOfMovies() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _myAdapter = new MyAdapter(this, getActivity());

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_movies, container, false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(_myAdapter);

        view.findViewById(R.id.search).setOnClickListener(this);

        emptySearching(this);
        dbHelper(getContext(), DATABASE_NAME, OperationWithDB.getAll, null, this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        _menu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.favourites) {
            if (item.getIcon().getConstantState().equals(
                    getResources().getDrawable(android.R.drawable.btn_star_big_off).getConstantState()
            )) {
                item.setIcon(android.R.drawable.btn_star_big_on);

                dbHelper(getContext(), DATABASE_NAME,
                        OperationWithDB.getAll, null, this);

                _myAdapter.changeMoviesToFavourites();
                _myAdapter.notifyDataSetChanged();
            } else {
                item.setIcon(android.R.drawable.btn_star_big_off);

                searchingMovies();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchResultReceived(SearchResult searchResult) {
        _myAdapter.setSearchResults(searchResult);
        _myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDBResultReceived(List<Movie> list) {
        _myAdapter.setDBResults(list);
        _myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(boolean isAdding, Movie movie) {
        movie.setIsSaved(!movie.getIsSaved());

        dbHelper(getContext(), DATABASE_NAME,
                 isAdding ? OperationWithDB.insert : OperationWithDB.delete,
                 movie, this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search) {
            searchingMovies();

            _menu.findItem(R.id.favourites)
                    .setIcon(android.R.drawable.btn_star_big_off);
        }
    }

    private void searchingMovies() {
        Editable searchField = ((EditText)getView().findViewById(R.id.searchField)).getText();

        if (searchField.length() == 0)
            emptySearching(this);
        else
            searching(this, searchField.toString());
    }
}
