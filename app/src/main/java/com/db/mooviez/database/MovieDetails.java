package com.db.mooviez.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.db.mooviez.BR;
import com.db.mooviez.network.models.GetMovieDetailsResponse;
import com.db.mooviez.network.models.Movie;
import com.db.mooviez.network.models.MovieRatings;
import com.db.mooviez.network.models.Rating;

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
@Entity(tableName = "favorite_movies_table")
public class MovieDetails extends BaseObservable {

    private static final String IMDB_RATING = "Internet Movie Database";
    private static final String RT_RATING = "Rotten Tomatoes";
    private static final String METACRITIC_RATING = "Metacritic";

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String imdbId;
    private String title;
    private String year;
    private String poster;
    private String released;
    private String rated;
    private String runtime;
    private MovieRatings movieRatings;
    private String plot;
    private boolean favorite;

    public MovieDetails() {
    }

    public MovieDetails(GetMovieDetailsResponse response) {
        this.imdbId = response.getImdbId();
        this.title = response.getTitle();
        this.year = response.getYear();
        this.poster = response.getPoster();
        this.released = response.getReleased();
        this.rated = response.getRated();
        this.runtime = response.getRuntime();
        this.movieRatings = new MovieRatings(response.getRatings());
        this.plot = response.getPlot();
        this.favorite = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Bindable
    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
        notifyPropertyChanged(BR.poster);
    }

    @Bindable
    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
        notifyPropertyChanged(BR.released);
    }

    @Bindable
    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
        notifyPropertyChanged(BR.rated);
    }

    @Bindable
    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
        notifyPropertyChanged(BR.runtime);
    }

    @Bindable
    public MovieRatings getMovieRatings() {
        return movieRatings;
    }

    public void setMovieRatings(MovieRatings movieRatings) {
        this.movieRatings = movieRatings;
        notifyPropertyChanged(BR.movieRatings);
    }

    @Bindable
    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
        notifyPropertyChanged(BR.plot);
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public MovieDetails toggleFavorite() {
        favorite = !favorite;
        return this;
    }

    public Movie getMovie() {
        return new Movie(this.getImdbId(), this.getTitle(), this.getYear(), this.getPoster());
    }

    @Override
    public String toString() {
        return "MovieDetails{" + "id=" + id + ", imdbId='" + imdbId + '\'' + ", title='" + title + '\'' + ", year='" + year + '\'' + ", "
                + "poster='" + poster + '\'' + ", released='" + released + '\'' + ", rated='" + rated + '\'' + ", runtime='" + runtime +
                '\'' + ", movieRatings=" + movieRatings + ", plot='" + plot + '\'' + '}';
    }

    @BindingAdapter("app:imageUrl")
    public static void setPoster(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

    @BindingAdapter("app:imdbRating")
    public static void setImdbRating(TextView textView, MovieDetails movieDetails) {
        if (movieDetails != null) {
            textView.setText(getRating(movieDetails, IMDB_RATING));
        }
    }

    @BindingAdapter("app:rtRating")
    public static void setRtRating(TextView textView, MovieDetails movieDetails) {
        if (movieDetails != null) {
            textView.setText(getRating(movieDetails, RT_RATING));
        }
    }

    @BindingAdapter("app:metaRating")
    public static void setMetacriticRating(TextView textView, MovieDetails movieDetails) {
        if (movieDetails != null) {
            textView.setText(getRating(movieDetails, METACRITIC_RATING));
        }
    }

    private static String getRating(@NonNull MovieDetails movieDetails, String ratingType) {
        for (Rating rating : movieDetails.getMovieRatings().getRatings()) {
            if (ratingType.equals(rating.getSource())) {
                return rating.getValue();
            }
        }
        return "";
    }
}
