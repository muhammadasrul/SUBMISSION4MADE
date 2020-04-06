package com.acun.submission4.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static final class FavMovieColumns implements BaseColumns {
        public static final String MOVIE_TABLE_NAME = "favorite_movie";
        public static final String MOVIE_ID = "movie_id";
        public static final String MOVIE_TITLE = "title";
        public static final String MOVIE_DATE = "date";
        public static final String MOVIE_SCORE = "score";
        public static final String MOVIE_POPULARITY = "popularity";
        public static final String MOVIE_LANGUAGE = "language";
        public static final String MOVIE_OVERVIEW = "overview";
        public static final String MOVIE_POSTER = "poster";
    }

    public static final class FavTvShowColumns implements BaseColumns {
        public static final String TVSHOW_TABLE_NAME = "favorite_tvshow";
        public static final String TVSHOW_ID = "tvshow_id";
        public static final String TVSHOW_TITLE = "title";
        public static final String TVSHOW_DATE = "date";
        public static final String TVSHOW_SCORE = "score";
        public static final String TVSHOW_POPULARITY = "popularity";
        public static final String TVSHOW_LANGUAGE = "language";
        public static final String TVSHOW_OVERVIEW = "overview";
        public static final String TVSHOW_POSTER = "poster";
    }
}
