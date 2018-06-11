package com.db.mooviez.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

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
public class MovieRepositoryViewModel extends AndroidViewModel {

    private MovieRepository repository;
    private LiveData<List<MovieDetails>> allMovies;

    public MovieRepositoryViewModel(@NonNull Application application) {
        super(application);

        repository = new MovieRepository(application);
        allMovies = repository.getAllMovies();
    }

    public void insert(MovieDetails movie) {
        repository.insert(movie);
    }

    public LiveData<Integer> exists(String imdbId) {
        return repository.exists(imdbId);
    }

    public LiveData<MovieDetails> getMovie(String imdbId) {
        return repository.getMovie(imdbId);
    }

    public LiveData<List<MovieDetails>> getAllMovies() {
        return allMovies;
    }

    public void setFavorite(String imdbId, boolean isFavorite) {
        repository.setFavorite(imdbId, isFavorite);
    }
}
