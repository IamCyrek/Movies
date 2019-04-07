package com.example.movies;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

public class MyAsyncTaskHelper {
    public static void emptySearching(AsyncTaskCallBack callBack) {
        MyAsyncTaskForSearching myAsyncTaskForSearching = new MyAsyncTaskForSearching(callBack);
        myAsyncTaskForSearching.execute("https://api.themoviedb.org/3/discover/movie?api_key=a855d05c9943743d1fff837f5a7eaa88");
        //https://api.themoviedb.org/3/search/movie?api_key=a855d05c9943743d1fff837f5a7eaa88&query=Jack+Reacher
    }

    public static void searching(AsyncTaskCallBack callBack, final String searchField) {
        MyAsyncTaskForSearching myAsyncTaskForSearching = new MyAsyncTaskForSearching(callBack);
        myAsyncTaskForSearching.execute("https://api.themoviedb.org/3/search/movie?api_key=a855d05c9943743d1fff837f5a7eaa88&query=" + searchField);
    }

    public enum OperationWithDB {
        getAll,
        insert,
        delete
    }

    public static void dbHelper(final Context context, final String dbName,
                                final OperationWithDB operationWithDB, final Movie movie,
                                AsyncTaskCallBack callBack) {
        MyAsyncTaskForDB myAsyncTaskForDB = new MyAsyncTaskForDB(new MyAsyncTaskForDB.TaskListener() {
            @Override
            public List<Movie> execute() {
                MyDatabase database = Room.databaseBuilder(context, MyDatabase.class, dbName).build();

                switch (operationWithDB) {
                    case getAll: break;
                    case insert:
                        if (movie != null)
                            database.movieDAO().insertMovie(movie);
                        else
                            throw new IllegalArgumentException(
                                    "Inserted movie is undefined.");
                        break;
                    case delete:
                        if (movie != null)
                            database.movieDAO().deleteMovie(movie);
                        else
                            throw new IllegalArgumentException(
                                    "Removable movie is undefined.");
                        break;
                }

                return database.movieDAO().getAll();
            }
        }, callBack);
        myAsyncTaskForDB.execute();
    }
}
