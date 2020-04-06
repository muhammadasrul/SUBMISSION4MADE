package com.acun.submission4.details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.acun.submission4.R;
import com.acun.submission4.db.MovieHelper;
import com.acun.submission4.models.Movie;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_DATE;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_ID;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_LANGUAGE;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_OVERVIEW;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_POPULARITY;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_POSTER;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_SCORE;
import static com.acun.submission4.db.DatabaseContract.FavMovieColumns.MOVIE_TITLE;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle, tvRelease, tvPopularity, tvUserscore, tvLanguage, tvOverview;
    private ImageView imgPoster;
    private FloatingActionButton fabFavAdd, fabFavDel;

    private MovieHelper movieHelper;

    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        String movieId = Integer.toString(movie.getId());

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        tvTitle = findViewById(R.id.txt_movie_title_detail);
        tvRelease = findViewById(R.id.txt_movie_release_detail);
        tvPopularity = findViewById(R.id.txt_movie_popularity_detail);
        tvUserscore = findViewById(R.id.txt_movie_userscore_detail);
        tvLanguage = findViewById(R.id.txt_movie_language);
        tvOverview = findViewById(R.id.txt_movie_overview);
        imgPoster = findViewById(R.id.img_movie_detail);

        fabFavAdd = findViewById(R.id.fab_fav_add);
        fabFavDel = findViewById(R.id.fab_fav_del);
        fabFavAdd.setOnClickListener(this);
        fabFavDel.setOnClickListener(this);

        ProgressBar progressBar = findViewById(R.id.md_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        if (movieHelper.checkMovie(movieId)){
            fabFavAdd.setVisibility(View.GONE);
            fabFavDel.setVisibility(View.VISIBLE);
        }

        getMovie();

        progressBar.setVisibility(View.GONE);

    }

    public void getMovie() {
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        String movieDetail = String.format(getResources().getString(R.string.movie_detail));
        setTitle(movieDetail);
        tvTitle.setText(movie.getTitle());
        tvRelease.setText(movie.getRelease());
        tvPopularity.setText(movie.getPopularity());
        tvUserscore.setText(movie.getScore()+"/10");
        tvLanguage.setText(movie.getLanguage());
        tvOverview.setText(movie.getOverview());
        Glide.with(MovieDetailActivity.this)
                .load(movie.getPoster())
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(imgPoster);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_fav_add) {
            String toastAdd = getString(R.string.addedtofav);
            String toastAddf = getString(R.string.failed);

            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            int movieId = movie.getId();
            String title = movie.getTitle();
            String date = movie.getRelease();
            String score = movie.getScore();
            String popularity = movie.getPopularity();
            String language = movie.getLanguage();
            String overview = movie.getOverview();
            String poster = movie.getPoster();

            ContentValues values = new ContentValues();
            values.put(MOVIE_ID, movieId);
            values.put(MOVIE_TITLE, title);
            values.put(MOVIE_DATE, date);
            values.put(MOVIE_SCORE, score);
            values.put(MOVIE_POPULARITY, popularity);
            values.put(MOVIE_LANGUAGE, language);
            values.put(MOVIE_OVERVIEW, overview);
            values.put(MOVIE_POSTER, poster);
            long result = movieHelper.insert(values);
            if (result > 0) {
                fabFavAdd.setVisibility(View.GONE);
                fabFavDel.setVisibility(View.VISIBLE);
                Toast.makeText(MovieDetailActivity.this, toastAdd, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MovieDetailActivity.this, toastAddf, Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.fab_fav_del) {
            String toastDel = getString(R.string.deletedfromfav);
            String toastDelf = getString(R.string.failed);
            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            long result = movieHelper.deleteById(String.valueOf(movie.getId()));
            if (result > 0) {
                Toast.makeText(MovieDetailActivity.this, toastDel, Toast.LENGTH_SHORT).show();
                fabFavDel.setVisibility(View.GONE);
                fabFavAdd.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(MovieDetailActivity.this, toastDelf, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }
}
