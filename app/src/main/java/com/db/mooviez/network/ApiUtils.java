package com.db.mooviez.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
public class ApiUtils {

    public static final String API_KEY = "13fd148b";

    private static final String BASE_URL = "http://www.omdbapi.com";

    private static Retrofit retrofitInstance;
    private static Api apiInstance;

    public static Api getApi() {
        if (apiInstance == null) {
            apiInstance = getRetrofitClient().create(Api.class);
        }

        return apiInstance;
    }

    private static Retrofit getRetrofitClient() {
        if (retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitInstance;
    }
}
