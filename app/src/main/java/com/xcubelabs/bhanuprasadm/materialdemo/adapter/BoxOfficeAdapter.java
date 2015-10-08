package com.xcubelabs.bhanuprasadm.materialdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.xcubelabs.bhanuprasadm.materialdemo.R;
import com.xcubelabs.bhanuprasadm.materialdemo.extras.Constants;
import com.xcubelabs.bhanuprasadm.materialdemo.logging.L;
import com.xcubelabs.bhanuprasadm.materialdemo.network.VolleySingleton;
import com.xcubelabs.bhanuprasadm.materialdemo.pojo.Movie;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BoxOfficeAdapter extends RecyclerView.Adapter<BoxOfficeAdapter.BOViewHolder> {
    LayoutInflater layoutInflater;
    ArrayList<Movie> movieList = new ArrayList<>();

    public BoxOfficeAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(ArrayList<Movie> list) {
        this.movieList = list;
        notifyItemRangeChanged(0, movieList.size());
    }

    @Override
    public BOViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_box_office_item, parent, false);
        return new BOViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BOViewHolder holder, int position) {
        Movie current = movieList.get(position);
        L.m(current.toString());
        holder.tvTitle.setText(current.getTitle());
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        holder.tvReleaseDate.setText((current.getReleaseDateTheater() != null) ? dateFormat.format(current.getReleaseDateTheater()) : Constants.NA);
        if (current.getAudienceScore() == -1) {
            holder.rbAudRating.setRating(0.0F);
            holder.rbAudRating.setAlpha(0.5F);
        } else {
            holder.rbAudRating.setRating(current.getAudienceScore() / 20.0F);
        }
        ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();
        String imageUrl = current.getUrlThumbnail();
        if (!imageUrl.equals(Constants.NA)) {
            imageLoader.get(current.getUrlThumbnail(), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.ivTumb.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    holder.ivTumb.setImageResource(R.drawable.movies);
                }
            });
        } else {
            holder.ivTumb.setImageResource(R.drawable.movies);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class BOViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvReleaseDate;
        RatingBar rbAudRating;
        ImageView ivTumb;

        public BOViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            tvReleaseDate = (TextView) itemView.findViewById(R.id.tv_release_date);
            rbAudRating = (RatingBar) itemView.findViewById(R.id.rb_movie_rating);
            ivTumb = (ImageView) itemView.findViewById(R.id.iv_movie_image);
        }
    }
}
