package com.db.mooviez.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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
@Dao
interface MovieDao {

    @Insert
    void insert(MovieDetails movie);

    @Query("SELECT * FROM favorite_movies_table WHERE imdbId = :imdbId")
    LiveData<MovieDetails> getMovie(String imdbId);

    @Query("SELECT EXISTS (SELECT * FROM favorite_movies_table WHERE imdbId = :imdbId)")
    LiveData<Integer> exists(String imdbId);

    @Query("UPDATE favorite_movies_table SET favorite = :favorite WHERE imdbId = :imdbId")
    void setFavorite(String imdbId, boolean favorite);

    @Query("SELECT * FROM favorite_movies_table ORDER BY year DESC")
    LiveData<List<MovieDetails>> getAllMovies();

}
