package com.example.doremi.Fragments;

import android.app.Activity;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.doremi.Models.Song;
import com.example.doremi.R;
import com.example.doremi.ViewModels.GalleryViewModel;
import com.example.doremi.databinding.FragmentPlayerBinding;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.exoplayer2.C.CONTENT_TYPE_MUSIC;


public class PlayerFragment extends Fragment {
    private FragmentPlayerBinding binding;

    private GalleryViewModel viewModel;
    private PlayerNotificationManager playerNotificationManager;

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
                final int[] currentIndex = {player.getCurrentWindowIndex()};
                PlayerNotificationManager.MediaDescriptionAdapter mediaDescriptionAdapter = new PlayerNotificationManager.MediaDescriptionAdapter() {
                    @Override
                    public String getCurrentSubText(Player player) {
                        return "";
                    }

                    @Override
                    public String getCurrentContentTitle(Player player) {
                        return songs.get(currentIndex[0]).getSong_en_title();
                    }

                    @Override
                    public PendingIntent createCurrentContentIntent(Player player) {
//                        int window = player.getCurrentWindowIndex();
//                        return createPendingIntent(window);
                        return null;
                    }

                    @Override
                    public String getCurrentContentText(Player player) {
                        return songs.get(currentIndex[0]).getSong_artist_name();
                    }

                    @Override
                    public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
//                        return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher_foreground);
//                            return Picasso.get().load(songs.get(position[0]).getSong_cover_img()).into(bitmap);
                        return null;
                    }
                };
                playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(getContext(),
                        "channelId",
                        R.string.notificationId,
                        123,
                        mediaDescriptionAdapter);
                playerNotificationManager.setPlayer(player);
                player.addListener(new Player.Listener() {
                    @Override
                    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                        currentIndex[0] = player.getCurrentWindowIndex();
                    }
                });
            }
        });

//        MediaItem mediaItem = MediaItem.fromUri("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4");
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        // TODO: 7/13/2021 need to set playerNotificationManager to null if it is not null also release the player and set it to null
    }
}