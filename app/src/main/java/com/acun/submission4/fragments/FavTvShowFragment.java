package com.acun.submission4.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.acun.submission4.R;
import com.acun.submission4.adapters.TvShowAdapter;
import com.acun.submission4.db.TvShowHelper;
import com.acun.submission4.models.TvShow;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavTvShowFragment extends Fragment implements LoadTvShowCallback{

    private TvShowAdapter adapter;
    private ProgressBar progressBar;
    private TvShowHelper tvShowHelper;
    private RecyclerView rvFavTvShow;

    private static final String EXTRA_STATE = "extra_state";

    public FavTvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        rvFavTvShow = view.findViewById(R.id.rv_tvshow);
        rvFavTvShow.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvFavTvShow.setHasFixedSize(true);

        tvShowHelper = TvShowHelper.getInstance(getActivity());
        tvShowHelper.open();

        adapter = new TvShowAdapter(getActivity());
        rvFavTvShow.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadTvShowAsync(tvShowHelper, this).execute();
        } else {
            ArrayList<TvShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setData(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getData());
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(ArrayList<TvShow> tvShows) {
        progressBar.setVisibility(View.INVISIBLE);
        if (tvShows.size() > 0) {
            adapter.setData(tvShows);
        } else {
            String msg = String.format(getString(R.string.no_fav_tvshow));
            adapter.setData(new ArrayList<TvShow>());
            showSnackbarMessage(msg);
        }
    }

    private static class LoadTvShowAsync extends AsyncTask<Void, Void, ArrayList<TvShow>> {

        private final WeakReference<TvShowHelper> weakTvShowHelper;
        private final WeakReference<LoadTvShowCallback> weakCallback;

        private LoadTvShowAsync(TvShowHelper tvShowHelper, LoadTvShowCallback callback) {
            weakTvShowHelper = new WeakReference<>(tvShowHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<TvShow> doInBackground(Void... voids) {
            return weakTvShowHelper.get().queryAll();
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> tvShows) {
            super.onPostExecute(tvShows);
            weakCallback.get().postExecute(tvShows);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvShowHelper.close();
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvFavTvShow, message, Snackbar.LENGTH_SHORT).show();
    }
}

interface LoadTvShowCallback {
    void preExecute();
    void postExecute(ArrayList<TvShow> tvShows);
}