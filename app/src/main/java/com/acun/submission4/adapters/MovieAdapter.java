package com.acun.submission4.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.acun.submission4.R;
import com.acun.submission4.details.MovieDetailActivity;
import com.acun.submission4.models.Movie;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> data = new ArrayList<>();

    public MovieAdapter(FragmentActivity activity) {

    }

    public MovieAdapter() {
    }

    public ArrayList<Movie> getData() {
        return data;
    }

    public void setData(ArrayList<Movie> item) {
        data.clear();
        data.addAll(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_view_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imgPoster;
        TextView tvTitle, tvRelease, tvUserscore, tvPopularity;

        private MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRelease = itemView.findViewById(R.id.tv_release);
            tvUserscore = itemView.findViewById(R.id.tv_user_score);
            tvPopularity = itemView.findViewById(R.id.tv_popularity);

            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {

            tvTitle.setText(movie.getTitle());
            tvRelease.setText(movie.getRelease());
            tvUserscore.setText(movie.getScore()+"/10");
            tvPopularity.setText(movie.getPopularity());

            Glide.with(itemView.getContext())
                    .load(movie.getPoster())
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPoster);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie movie = data.get(position);

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), MovieDetailActivity.class);
            moveWithObjectIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
            itemView.getContext().startActivity(moveWithObjectIntent);
        }
    }
}
