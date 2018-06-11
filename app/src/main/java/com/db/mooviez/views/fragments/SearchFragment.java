package com.db.mooviez.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.db.mooviez.R;
import com.db.mooviez.network.interactors.GetMovies;
import com.db.mooviez.network.models.GetMoviesResponse;
import com.db.mooviez.network.models.Movie;
import com.db.mooviez.views.MovieDetailsActivity;
import com.db.mooviez.views.grid.MovieAdapter;
import com.db.mooviez.views.grid.MovieGridItemDecoration;
import com.db.mooviez.views.models.MovieCardViewModel;

import java.util.ArrayList;
import java.util.Arrays;
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
public class SearchFragment extends Fragment {

    private EditText searchText;
    private MovieAdapter adapter;
    private List<Movie> movies;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        initView(view);
        initRecyclerView(view);

        return view;
    }

    private void initView(View view) {
        searchText = view.findViewById(R.id.search_text);
        ImageView searchButton = view.findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForMovies(searchText.getText().toString());
            }
        });
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.search_recycler_view);
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

    private void setData(List<Movie> movies) {
        this.movies = movies;

        List<MovieCardViewModel> movieModels = new ArrayList<>();
        for (Movie movie : movies) {
            movieModels.add(new MovieCardViewModel(movie));
        }
        adapter.setData(movieModels);
    }

    private void searchForMovies(String movieTitle) {
        GetMovies.execute(new GetMovies.GetMoviesCallback() {
            @Override
            public void onSuccess(GetMoviesResponse responseBody) {
                setData(new ArrayList<>(Arrays.asList(responseBody.getMovies())));
            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "GetMovies failed.", Toast.LENGTH_LONG).show();
            }
        }, movieTitle);
    }
}
