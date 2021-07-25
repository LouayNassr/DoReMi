package com.example.doremi.Fragments;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.doremi.Models.Song;
import com.example.doremi.R;
import com.example.doremi.ViewModels.SongsViewModel;
import com.example.doremi.databinding.FragmentPlayerBinding;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.exoplayer2.C.CONTENT_TYPE_MUSIC;


public class PlayerFragment extends Fragment {
    ImageButton btnFullScreen, btnSettings;
    boolean flag = false;// for fullscreen
    private FragmentPlayerBinding binding;
    private SongsViewModel viewModel;
    private PlayerNotificationManager playerNotificationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPlayerBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        btnFullScreen = binding.audioPlayerView.findViewById(R.id.exo_fullscreen_button);
        btnSettings = binding.audioPlayerView.findViewById(R.id.exo_quality_settings);

        //make activity fullscreen
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        viewModel = new ViewModelProvider(requireActivity()).get(SongsViewModel.class);
        viewModel.setSongs();
        final int[] position = {getArguments().getInt("position")};

        Activity activity = getActivity();// this line is used to create only one instance of exoplayer

        DefaultTrackSelector trackSelector = new DefaultTrackSelector(getContext());
        trackSelector.setParameters(trackSelector.getParameters());

        SimpleExoPlayer player = new SimpleExoPlayer.Builder(activity).setTrackSelector(trackSelector).build();

        // audioAttributes is used to add audio focus configuration
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
                player.setPlayWhenReady(true);

                binding.audioPlayerView.findViewById(R.id.exo_fullscreen_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag) {
                            btnFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        } else {
                            btnFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        }
                        flag = !flag;
                    }
                });

                binding.audioPlayerView.findViewById(R.id.exo_quality_settings).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                        if (mappedTrackInfo != null) {
                            int rendererIndex = 0;
                            int rendererType = mappedTrackInfo.getRendererType(rendererIndex);
                            boolean allowAdaptiveSelections =
                                    rendererType == C.TRACK_TYPE_VIDEO
                                            || (rendererType == C.TRACK_TYPE_AUDIO
                                            && mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
                                            == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_NO_TRACKS);
                            TrackSelectionDialogBuilder builder = new TrackSelectionDialogBuilder(getActivity(), "select one", trackSelector, rendererIndex);
                            builder.setAllowAdaptiveSelections(allowAdaptiveSelections);
                            builder.build().show();
                        }
                    }
                });
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
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        // TODO: 7/13/2021 need to set playerNotificationManager to null if it is not null also release the player and set it to null
    }
}