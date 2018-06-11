package com.db.mooviez.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.db.mooviez.R;
import com.db.mooviez.database.MovieRepositoryViewModel;
import com.db.mooviez.databinding.ActivityMovieDetailsBinding;
import com.db.mooviez.network.interactors.GetMovieDetails;
import com.db.mooviez.network.models.GetMovieDetailsResponse;
import com.db.mooviez.database.MovieDetails;

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
public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE_IMDB_ID";

    private String movieImdbId;

    private ActivityMovieDetailsBinding binding;

    private MovieRepositoryViewModel movieRepository;

    private boolean alreadyExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initView();
        movieImdbId = getIntent().getStringExtra(EXTRA_MOVIE);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        binding.setHandler(this);

        observeMovieExists();
        observeMovieDetails();
    }

    private void initView() {
        movieRepository = ViewModelProviders.of(this).get(MovieRepositoryViewModel.class);
    }

    public void onFavoriteClick() {
        if (binding.getMovie() == null) {
            return;
        }

        binding.setMovie(binding.getMovie().toggleFavorite());

        if (alreadyExists) {
            movieRepository.setFavorite(movieImdbId, binding.getMovie().isFavorite());
        } else {
            movieRepository.insert(binding.getMovie());
        }
    }

    private void getMovieDetails(String imdbId) {
        GetMovieDetails.execute(new GetMovieDetails.GetMovieDetailsCallback() {
            @Override
            public void onSuccess(GetMovieDetailsResponse responseBody) {
                binding.setMovie(new MovieDetails(responseBody));
            }

            @Override
            public void onError() {
                Toast.makeText(MovieDetailsActivity.this, "GetMovieDetails failed.", Toast.LENGTH_LONG).show();
            }
        }, imdbId);
    }

    private void observeMovieExists() {
        movieRepository.exists(movieImdbId).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (Integer.valueOf(1).equals(integer)) {
                    alreadyExists = true;
                    return;
                }
                getMovieDetails(movieImdbId);
            }
        });
    }

    private void observeMovieDetails() {
        movieRepository.getMovie(movieImdbId).observe(this, new Observer<MovieDetails>() {
            @Override
            public void onChanged(@Nullable MovieDetails movieDetails) {
                binding.setMovie(movieDetails);
            }
        });
    }
}
