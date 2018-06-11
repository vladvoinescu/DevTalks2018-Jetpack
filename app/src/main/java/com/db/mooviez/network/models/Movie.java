package com.db.mooviez.network.models;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

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
public class Movie extends BaseObservable {

    @SerializedName("imdbID")
    private String imdbId;

    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Poster")
    private String poster;

    public Movie(String imdbId, String title, String year, String poster) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.poster = poster;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(@NonNull String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "Movie{" + "imdbId='" + imdbId + '\'' + ", title='" + title + '\'' + ", year='" + year + '\'' + ", poster='" + poster +
                '\'' + '}';
    }
}
