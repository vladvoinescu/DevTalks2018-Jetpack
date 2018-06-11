package com.db.mooviez.views.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.db.mooviez.network.models.Movie;

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
public class MovieCardViewModel extends BaseObservable {

    private Movie movie;

    public MovieCardViewModel(Movie movie) {
        this.movie = movie;
    }

    @Bindable
    public String getImdbId() {
        return movie.getImdbId();
    }

    @Bindable
    public String getTitle() {
        return movie.getTitle();
    }

    @Bindable
    public String getYear() {
        return movie.getYear();
    }

    @Bindable
    public String getPoster() {
        return movie.getPoster();
    }
}
