package com.example.doremi.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doremi.Models.Song;
import com.example.doremi.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Song> songs;

    public SongsAdapter(Context ctx, List<Song> songs) {
        this.inflater = LayoutInflater.from(ctx);
        this.songs = songs;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.song_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String imageUrl = songs.get(position).getSong_cover_img();
        String songTitle = songs.get(position).getSong_en_title();
        String artistName = songs.get(position).getSong_artist_name();
        String songUrl = songs.get(position).getSong_url();

        holder.songTitle.setText(songTitle);
        holder.songArtist.setText(artistName);
        Picasso.get().load(imageUrl).into(holder.songCoverImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("imageUrl", imageUrl);
                bundle.putString("songTitle", songTitle);
                bundle.putString("artistName", artistName);
                bundle.putString("songUrl", songUrl);
                bundle.putInt("position", position);
                Navigation.findNavController(holder.itemView).navigate(R.id.action_nav_songs_to_playerFragment,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView songTitle, songArtist;
        ImageView songCoverImage;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            songTitle = itemView.findViewById(R.id.song_title_tv);
            songArtist = itemView.findViewById(R.id.song_artist_tv);
            songCoverImage = itemView.findViewById(R.id.song_coverImage);
        }
    }

}