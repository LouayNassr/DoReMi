package com.example.doremi.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doremi.Models.Playlist;
import com.example.doremi.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomePlaylistsAdapter extends RecyclerView.Adapter<HomePlaylistsAdapter.ViewHolder> {

    final int playlistsCountLimit = 5;
    LayoutInflater inflater;
    List<Playlist> playlists;

    public HomePlaylistsAdapter(Context ctx, List<Playlist> playlists) {
        inflater = LayoutInflater.from(ctx);
        this.playlists = playlists;
    }

    @NonNull
    @NotNull
    @Override
    public HomePlaylistsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_playlist_item_layout, parent, false);
        return new HomePlaylistsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String playlistUrl = playlists.get(position).getUrl();
        String playlistName = playlists.get(position).getName();
        String songsCount = playlists.get(position).getSongsCount();
        String playlistCoverImageUrl = playlists.get(position).getCoverImageUrl();

        holder.playlistNameTextview.setText(playlistName);
        holder.songsCountTextview.setText(songsCount);
        Picasso.get().load(playlistCoverImageUrl).into(holder.playlistCoverImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("playlistUrl", playlistUrl);
                Navigation.findNavController(holder.itemView).navigate(R.id.action_nav_home_to_nav_songs, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (playlists.size() > playlistsCountLimit) {
            return playlistsCountLimit;
        } else {
            return playlists.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView playlistNameTextview, songsCountTextview;
        ImageView playlistCoverImage;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            playlistNameTextview = itemView.findViewById(R.id.playlist_name_tv);
            songsCountTextview = itemView.findViewById(R.id.songs_count_tv);
            playlistCoverImage = itemView.findViewById(R.id.playlist_coverImage);
        }
    }
}
