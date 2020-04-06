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
import com.acun.submission4.details.TvShowDetailActivity;
import com.acun.submission4.models.TvShow;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    private ArrayList<TvShow> data = new ArrayList<>();

    public TvShowAdapter(FragmentActivity activity) {

    }

    public TvShowAdapter() {
    }

    public ArrayList<TvShow> getData() {
        return data;
    }

    public void setData(ArrayList<TvShow> item) {
        data.clear();
        data.addAll(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tvshow_card_view_item, parent, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle, tvRelease, tvScore, tvPopularity;
        ImageView imgPoster;

        private TvShowViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_show_title);
            tvRelease = itemView.findViewById(R.id.tv_show_release);
            tvScore = itemView.findViewById(R.id.tv_show_user_score);
            tvPopularity = itemView.findViewById(R.id.tv_show_popularity);
            imgPoster = itemView.findViewById(R.id.img_tvshow_poster);

            itemView.setOnClickListener(this);
        }

        void bind(TvShow tvShow) {
            tvTitle.setText(tvShow.getTitle());
            tvRelease.setText(tvShow.getRelease());
            tvScore.setText(tvShow.getScore()+"/10");
            tvPopularity.setText(tvShow.getPopularity());

            Glide.with(itemView.getContext())
                    .load(tvShow.getPoster())
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPoster);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            TvShow tvShow = data.get(position);

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), TvShowDetailActivity.class);
            moveWithObjectIntent.putExtra(TvShowDetailActivity.EXTRA_TVSHOW, tvShow);
            itemView.getContext().startActivity(moveWithObjectIntent);
        }
    }
}
