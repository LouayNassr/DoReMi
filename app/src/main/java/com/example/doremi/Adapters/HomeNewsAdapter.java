package com.example.doremi.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doremi.Models.News;
import com.example.doremi.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.ViewHolder> {

    final int newsCountLimit = 2;
    LayoutInflater inflater;
    List<News> newsList;

    public HomeNewsAdapter(Context ctx, List<News> newsList) {
        inflater = LayoutInflater.from(ctx);
        this.newsList = newsList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_news_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String newsUrl = newsList.get(position).getNewsUrl();
        String newsTitle = newsList.get(position).getTitle();
        String coverImage = newsList.get(position).getCoverImage();

        holder.newsTitleTextview.setText(newsTitle);
        Picasso.get().load(coverImage).into(holder.newsCoverImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsUrl));
                inflater.getContext().startActivity(browseIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (newsList.size() > newsCountLimit) {
            return newsCountLimit;
        } else {
            return newsList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitleTextview;
        ImageView newsCoverImage;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            newsTitleTextview = itemView.findViewById(R.id.news_title_tv);
            newsCoverImage = itemView.findViewById(R.id.news_coverImage);
        }
    }
}
