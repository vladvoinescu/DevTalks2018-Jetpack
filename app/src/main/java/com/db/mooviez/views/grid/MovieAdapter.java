package com.db.mooviez.views.grid;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.mooviez.R;
import com.db.mooviez.databinding.MovieCardBinding;
import com.db.mooviez.views.models.MovieCardViewModel;

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
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<MovieCardViewModel> moviesList;
    private LayoutInflater layoutInflater;

    private OnItemClicked onClick;

    public void setData(List<MovieCardViewModel> moviesList) {
        this.moviesList = moviesList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        MovieCardBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.movie_card, parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {
        holder.binding.setModel(moviesList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (moviesList == null) {
            return 0;
        }
        return moviesList.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private final MovieCardBinding binding;

        MovieViewHolder(final MovieCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}