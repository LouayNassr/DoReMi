package com.example.doremi.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.doremi.Models.Song;
import com.example.doremi.ViewModels.GalleryViewModel;
import com.example.doremi.databinding.FragmentPlayerBinding;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.exoplayer2.C.CONTENT_TYPE_MUSIC;


public class PlayerFragment extends Fragment {
    private FragmentPlayerBinding binding;

    private GalleryViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPlayerBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireParentFragment()).get(GalleryViewModel.class);

        final int[] position = {getArguments().getInt("position")};

        Activity activity = getActivity();// this line is used to create only one instance of exoplayer
        SimpleExoPlayer player = new SimpleExoPlayer.Builder(activity).build();
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(CONTENT_TYPE_MUSIC)
                .build();
        binding.audioPlayerView.setPlayer(player);
        player.setAudioAttributes(audioAttributes, true);

        viewModel.getSongs().observe(getViewLifecycleOwner(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                List<MediaItem> songsMediaItems = new ArrayList<>();
                for (int i = 0; i < songs.size(); i++) {
                    songsMediaItems.add(MediaItem.fromUri(songs.get(i).getSong_url()));
                }
                player.setMediaItems(songsMediaItems, true);
                player.seekTo(position[0], C.INDEX_UNSET);
                player.prepare();
                player.play();
            }
        });

//        MediaItem mediaItem = MediaItem.fromUri("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4");
//        player.setMediaItem(mediaItem, true);
////        player.seekTo(position[0], C.INDEX_UNSET);
//        player.prepare();
//        player.play();

//        PlayerNotificationManager notificationManager = new PlayerNotificationManager(getContext(), "sd", ).setPlayer(player);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}