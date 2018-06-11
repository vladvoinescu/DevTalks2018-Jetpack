package com.db.mooviez.database;

import android.arch.persistence.room.TypeConverter;

import com.db.mooviez.network.models.MovieRatings;
import com.db.mooviez.network.models.Rating;

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
public class RatingsConverter {

    @TypeConverter
    public MovieRatings storedStringToRatings(String value) {
        List<Rating> ratings = new ArrayList<>();
        for (String ratingString : Arrays.asList(value.split("\\s*,\\s*"))) {
            final List<String> ratingStringAsList = Arrays.asList(ratingString.split("\\s*:\\s*"));
            String ratingSource = ratingStringAsList.get(0);
            String ratingValue = ratingStringAsList.get(1);
            ratings.add(new Rating(ratingSource, ratingValue));
        }

        return new MovieRatings(ratings);
    }

    @TypeConverter
    public String ratingsToStoredString(MovieRatings ratings) {
        StringBuilder value = new StringBuilder();

        for (Rating rating : ratings.getRatings()) {
            value.append(rating.getSource()).append(":").append(rating.getValue()).append(",");
        }

        return value.toString();
    }

}
