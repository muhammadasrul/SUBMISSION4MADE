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
import com.acun.submission4.db.TvShowHelper;
import com.acun.submission4.models.TvShow;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_DATE;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_ID;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_LANGUAGE;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_OVERVIEW;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_POPULARITY;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_POSTER;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_SCORE;
import static com.acun.submission4.db.DatabaseContract.FavTvShowColumns.TVSHOW_TITLE;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvTitle, tvRelease, tvPopularity, tvScore, tvLanguage, tvOverview;
    private ImageView imgPoster;
    private FloatingActionButton fabFavAdd, fabFavDel;

    private TvShowHelper tvShowHelper;

    public static final String EXTRA_TVSHOW = "extra_tvshow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        String tvShowId = Integer.toString(tvShow.getId());
        tvShowHelper = TvShowHelper.getInstance(getApplicationContext());
        tvShowHelper.open();

        tvTitle = findViewById(R.id.txt_tvshow_title_detail);
        tvRelease = findViewById(R.id.txt_tvshow_release_detail);
        tvScore = findViewById(R.id.txt_tvshow_userscore_detail);
        tvPopularity = findViewById(R.id.txt_tvshow_popularity_detail);
        tvLanguage = findViewById(R.id.txt_tvshow_language);
        tvOverview = findViewById(R.id.txt_tvshow_overview);
        imgPoster = findViewById(R.id.img_tvshow_detail);

        fabFavAdd = findViewById(R.id.fab_fav_add);
        fabFavDel = findViewById(R.id.fab_fav_del);
        fabFavAdd.setOnClickListener(this);
        fabFavDel.setOnClickListener(this);

        ProgressBar progressBar = findViewById(R.id.td_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        if (tvShowHelper.checkTvShow(tvShowId)){
            fabFavAdd.setVisibility(View.GONE);
            fabFavDel.setVisibility(View.VISIBLE);
        }

        getTvShow();

        progressBar.setVisibility(View.GONE);

    }

    private void getTvShow() {
        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);

        String tvshow_detail = String.format(getResources().getString(R.string.tv_show_detail));
        setTitle(tvshow_detail);

        tvTitle.setText(tvShow.getTitle());
        tvRelease.setText(tvShow.getRelease());
        tvScore.setText(tvShow.getScore()+"/10");
        tvPopularity.setText(tvShow.getPopularity());
        tvLanguage.setText(tvShow.getLanguage());
        tvOverview.setText(tvShow.getOverview());

        Glide.with(TvShowDetailActivity.this)
                .load(tvShow.getPoster())
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(imgPoster);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_fav_add) {
            String toastAdd = getString(R.string.addedtofav);
            String toastAddf = getString(R.string.failed);

            TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
            Integer tvShowId = tvShow.getId();
            String title = tvShow.getTitle();
            String date = tvShow.getRelease();
            String score = tvShow.getScore();
            String popularity = tvShow.getPopularity();
            String language = tvShow.getLanguage();
            String overview = tvShow.getOverview();
            String poster = tvShow.getPoster();

            ContentValues values = new ContentValues();
            values.put(TVSHOW_ID, tvShowId);
            values.put(TVSHOW_TITLE, title);
            values.put(TVSHOW_DATE, date);
            values.put(TVSHOW_SCORE, score);
            values.put(TVSHOW_POPULARITY, popularity);
            values.put(TVSHOW_LANGUAGE, language);
            values.put(TVSHOW_OVERVIEW, overview);
            values.put(TVSHOW_POSTER, poster);
            long result = tvShowHelper.insert(values);
            if (result > 0) {
                fabFavAdd.setVisibility(View.GONE);
                fabFavDel.setVisibility(View.VISIBLE);
                Toast.makeText(TvShowDetailActivity.this, toastAdd, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TvShowDetailActivity.this, toastAddf, Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.fab_fav_del) {
            TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
            String tvShowId = Integer.toString(tvShow.getId());

            String toastDel = getString(R.string.deletedfromfav);
            String toastDelf = getString(R.string.failed);

            long result = tvShowHelper.deleteById(tvShowId);
            if (result > 0) {
                Toast.makeText(TvShowDetailActivity.this, toastDel, Toast.LENGTH_SHORT).show();
                fabFavDel.setVisibility(View.GONE);
                fabFavAdd.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(TvShowDetailActivity.this, toastDelf, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvShowHelper.close();
    }
}
