package com.acun.submission4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static android.provider.BaseColumns._ID;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_DATE;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_ID;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_LANGUAGE;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_OVERVIEW;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_POPULARITY;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_POSTER;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_SCORE;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_TABLE_NAME;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_TITLE;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_DATE;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_ID;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_LANGUAGE;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_OVERVIEW;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_POPULARITY;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_POSTER;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_SCORE;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_TABLE_NAME;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_TITLE;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbfavorite";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_MOVIE_TABLE =
            String.format("CREATE TABLE %s" +
                            " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s INTEGER," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL)",
                    MOVIE_TABLE_NAME,
                    _ID,
                    MOVIE_ID,
                    MOVIE_TITLE,
                    MOVIE_DATE,
                    MOVIE_SCORE,
                    MOVIE_POPULARITY,
                    MOVIE_LANGUAGE,
                    MOVIE_OVERVIEW,
                    MOVIE_POSTER
            );

    private static final String SQL_CREATE_TVSHOW_TABLE =
            String.format("CREATE TABLE %s" +
                            " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s INTEGER," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL)",
                    TVSHOW_TABLE_NAME,
                    _ID,
                    TVSHOW_ID,
                    TVSHOW_TITLE,
                    TVSHOW_DATE,
                    TVSHOW_SCORE,
                    TVSHOW_POPULARITY,
                    TVSHOW_LANGUAGE,
                    TVSHOW_OVERVIEW,
                    TVSHOW_POSTER
            );

    DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TVSHOW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TVSHOW_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
