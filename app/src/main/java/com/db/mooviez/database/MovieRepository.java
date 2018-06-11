package com.db.mooviez.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * DevTalks 2018 - 30 minutes to build a modern Android app - Jetpack showcase app
 * <p>
 * Contact: vlad.voinescu@db.com
 * <p>
 * Copyright © 2018 Deutsche Bank
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class MovieRepository {

    private MovieDao movieDao;
    private LiveData<List<MovieDetails>> allMovies;

    MovieRepository(Application application) {
        MovieDatabase db = MovieDatabase.getDatabase(application);
        movieDao = db.movieDao();
        allMovies = movieDao.getAllMovies();
    }

    void insert(MovieDetails movie) {
        new InsertAsyncTask(movieDao).execute(movie);
    }

    LiveData<MovieDetails> getMovie(String imdbId) {
        return movieDao.getMovie(imdbId);
    }

    LiveData<List<MovieDetails>> getAllMovies() {
        return allMovies;
    }

    void setFavorite(String imdbId, boolean isFavorite) {
        new UpdateAsyncTask(movieDao).execute(new UpdateAsyncTaskParams(imdbId, isFavorite));
    }

    public LiveData<Integer> exists(String imdbId) {
        return movieDao.exists(imdbId);
    }

    private static class InsertAsyncTask extends AsyncTask<MovieDetails, Void, Void> {
        private MovieDao asyncTaskDao;

        InsertAsyncTask(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MovieDetails... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<UpdateAsyncTaskParams, Void, Void> {
        private MovieDao asyncTaskDao;

        UpdateAsyncTask(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UpdateAsyncTaskParams... params) {
            asyncTaskDao.setFavorite(params[0].imdbId, params[0].isFavorite);
            return null;
        }
    }

    private class UpdateAsyncTaskParams {
        private String imdbId;
        private boolean isFavorite;

        private UpdateAsyncTaskParams(String imdbId, boolean isFavorite) {
            this.imdbId = imdbId;
            this.isFavorite = isFavorite;
        }
    }
}
