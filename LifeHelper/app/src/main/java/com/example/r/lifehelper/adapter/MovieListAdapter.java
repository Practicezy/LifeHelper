package com.example.r.lifehelper.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.r.lifehelper.R;
import com.example.r.lifehelper.bean.Book;
import com.example.r.lifehelper.bean.Movie;
import com.example.r.lifehelper.bean.MovieLab;
import com.example.r.lifehelper.utils.ImageLoader;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListHolder> {
    private List<Movie> mMovieList;
    private Context mContext;
    private static final int TYPE_SPECIAL = 0;
    private static final int TYPE_NORMAL = 1;
    private static final String TAG = "MovieListAdapter";


    public MovieListAdapter(List<Movie> movieList, Context context) {
        mMovieList = movieList;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position %3 == 0){
            return TYPE_SPECIAL;
        }else {
            return TYPE_NORMAL;
        }
    }

    @NonNull
    @Override
    public MovieListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieListHolder holder = null;
        switch (viewType){
            case TYPE_SPECIAL:
                View view0 = LayoutInflater.from(mContext).inflate(R.layout.item0_list_movie,parent,false);
                holder = new MovieListHolder(view0);
                break;
            case TYPE_NORMAL:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.item1_list_movie,parent,false);
                holder = new MovieListHolder(view1);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieListHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        holder.tvMovieTitle.setText(movie.getTitle());
        holder.tvMovieCategory.setText(movie.getCategory());
        holder.tvMovieTags.setText(movie.getTags());
        Glide.with(mContext).load(movie.getCoverUrl()).into(holder.ivMovieCover);
        Glide.with(mContext).asBitmap().load(movie.getAvatarUrl()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(mContext.getResources(),resource);
                drawable.setCircular(true);
                holder.ivMovieAvatar.setImageDrawable(drawable);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    /*刷新列表内容和视图*/
    public void updateAdapter(List<Movie> movies) {
        mMovieList = movies;
        MovieLab.getMovieLab().updateMovieList(mMovieList);
        notifyDataSetChanged();
    }

    /*添加列表内容和新视图*/
    public void insertItems(int position, List<Movie> movies) {
        for (int i = 0; i < movies.size(); i++) {
            mMovieList.add(position + i, movies.get(i));
            notifyItemInserted(position + i);
            notifyItemChanged(position, mMovieList.size() - (position + i));
        }
    }

    class MovieListHolder extends RecyclerView.ViewHolder {
        private TextView tvMovieTitle,tvMovieTags,tvMovieCategory;
        private ImageView ivMovieCover,ivMovieAvatar;

        public MovieListHolder(View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tv_movie_title);
            tvMovieTags = itemView.findViewById(R.id.tv_movie_tag);
            tvMovieCategory = itemView.findViewById(R.id.tv_movie_category);
            ivMovieCover = itemView.findViewById(R.id.iv_movie_cover);
            ivMovieAvatar = itemView.findViewById(R.id.iv_movie_avatar);
        }
    }
}
