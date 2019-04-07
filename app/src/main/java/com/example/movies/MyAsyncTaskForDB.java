package com.example.movies;

import android.os.AsyncTask;

import java.util.List;

class MyAsyncTaskForDB extends AsyncTask<Void, Void, List<Movie>> {

    public interface TaskListener {
        List<Movie> execute();
    }

    private final TaskListener _taskListener;
    private AsyncTaskCallBack _asyncTaskCallBack;

    MyAsyncTaskForDB(TaskListener taskListener, AsyncTaskCallBack asyncTaskCallBack) {
        _taskListener = taskListener;
        _asyncTaskCallBack = asyncTaskCallBack;
    }

    @Override
    protected List<Movie> doInBackground(Void... params) {
        return _taskListener.execute();
    }

    @Override
    protected void onPostExecute(List<Movie> list) {
        super.onPostExecute(list);
        if (list != null)
            _asyncTaskCallBack.onDBResultReceived(list);
    }
}
