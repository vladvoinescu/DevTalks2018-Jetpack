package com.db.mooviez.views.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.mooviez.R;
import com.db.mooviez.database.MovieRepositoryViewModel;
import com.db.mooviez.network.models.Movie;
import com.db.mooviez.database.MovieDetails;
import com.db.mooviez.views.MovieDetailsActivity;
import com.db.mooviez.views.grid.MovieAdapter;
import com.db.mooviez.views.grid.MovieGridItemDecoration;
import com.db.mooviez.views.models.MovieCardViewModel;

import java.util.ArrayList;
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
public class FavoritesFragment extends Fragment {

    MovieAdapter adapter;
    private List<Movie> movies;

    public FavoritesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        initRecyclerView(view);
        initModelView();

        return view;
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.favorites_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        adapter = new MovieAdapter();
        adapter.setOnClick(new MovieAdapter.OnItemClicked() {
            @Override
            public void onItemClick(int position) {
                goToDetails(movies.get(position).getImdbId());
            }
        });
        recyclerView.setAdapter(adapter);
        int spacing = getResources().getDimensionPixelSize(R.dimen.grid_item_spacing);
        recyclerView.addItemDecoration(new MovieGridItemDecoration(spacing));
    }

    private void goToDetails(String selectedMovieImdbId) {
        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, selectedMovieImdbId);
        startActivity(intent);
    }

    private void initModelView() {
        // Get the ViewModel.
        MovieRepositoryViewModel movieRepository = ViewModelProviders.of(this).get(MovieRepositoryViewModel.class);

        // Create the observer which updates the UI.
        movieRepository.getAllMovies().observe(this, new Observer<List<MovieDetails>>() {
            @Override
            public void onChanged(@Nullable final List<MovieDetails> moviesWithDetails) {
                if (moviesWithDetails == null) {
                    return;
                }

                List<Movie> movies = new ArrayList<>();
                List<MovieCardViewModel> movieModels = new ArrayList<>();
                for (MovieDetails movieDetails : moviesWithDetails) {
                    if (movieDetails.isFavorite()) {
                        Movie movie = movieDetails.getMovie();
                        movies.add(movie);
                        movieModels.add(new MovieCardViewModel(movie));
                    }
                }

                adapter.setData(movieModels);
                FavoritesFragment.this.movies = movies;
            }
        });
    }
}
