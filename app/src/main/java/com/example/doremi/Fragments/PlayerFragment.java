package com.example.doremi.Fragments;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.doremi.Fragments.GalleryFragment.GalleryFragment;
import com.example.doremi.Models.Song;
import com.example.doremi.databinding.FragmentPlayerBinding;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.google.android.exoplayer2.C.CONTENT_TYPE_MUSIC;


public class PlayerFragment extends Fragment {
    private FragmentPlayerBinding binding;

    List<Song> songs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlayerBinding.inflate(inflater, container, false);
//        Picasso.get().load(getArguments().getString("imageUrl")).into(binding.coverImageView);

        Activity activity = getActivity();// this line is used to create only one instance of exoplayer
        SimpleExoPlayer player = new SimpleExoPlayer.Builder(activity).build();
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(CONTENT_TYPE_MUSIC)
                .build();
        player.setAudioAttributes(audioAttributes, true);
        binding.audioPlayerView.setPlayer(player);
//        Image image = Picasso.get().load(getArguments().getString("imageUrl"));
//        binding.audioPlayerView.setDefaultArtwork(Picasso.get().load(getArguments().getString("imageUrl")).into());
        MediaItem mediaItem = MediaItem.fromUri(getArguments().getString("songUrl"));
        player.setMediaItem(mediaItem);
        player.prepare();

        player.play();

//        PlayerNotificationManager notificationManager = new PlayerNotificationManager(getContext(), "sd", ).setPlayer(player);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}