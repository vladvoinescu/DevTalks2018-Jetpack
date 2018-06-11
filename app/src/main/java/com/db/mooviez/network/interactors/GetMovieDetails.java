package com.db.mooviez.network.interactors;

import android.support.annotation.NonNull;

import com.db.mooviez.network.ApiUtils;
import com.db.mooviez.network.models.GetMovieDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
public class GetMovieDetails {

    public static void execute(final GetMovieDetailsCallback callback, String id) {
        ApiUtils.getApi().getMovieDetails(ApiUtils.API_KEY, id).enqueue(new Callback<GetMovieDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetMovieDetailsResponse> call, @NonNull Response<GetMovieDetailsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError();
                    }
                } else {
                    callback.onError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetMovieDetailsResponse> call, @NonNull Throwable t) {
                callback.onError();
            }
        });
    }

    public interface GetMovieDetailsCallback {
        void onSuccess(GetMovieDetailsResponse responseBody);

        void onError();
    }
}
