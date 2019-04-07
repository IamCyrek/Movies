package com.example.movies;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

class MyAsyncTaskForSearching extends AsyncTask<String, Void, SearchResult> {
    private AsyncTaskCallBack _asyncTaskCallBack;

    MyAsyncTaskForSearching(AsyncTaskCallBack asyncTaskCallBack) {
        _asyncTaskCallBack = asyncTaskCallBack;
    }

    @Override
    protected SearchResult doInBackground(String... strings) {
        try {
            InputStream stream = new URL(strings[0]).openConnection().getInputStream();
            return new Gson().fromJson( new BufferedReader(new InputStreamReader(stream)).readLine(),
                    SearchResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(SearchResult searchResult) {
        super.onPostExecute(searchResult);
        _asyncTaskCallBack.onSearchResultReceived(searchResult);
    }
}
