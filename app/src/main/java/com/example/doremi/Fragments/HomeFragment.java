package com.example.doremi.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.doremi.Adapters.HomeNewsAdapter;
import com.example.doremi.Adapters.HomePlaylistsAdapter;
import com.example.doremi.Adapters.HomeTopSongsAdapter;
import com.example.doremi.Constants;
import com.example.doremi.Models.Song;
import com.example.doremi.R;
import com.example.doremi.ViewModels.HomeFragmentViewModel;
import com.example.doremi.ViewModels.NewsViewModel;
import com.example.doremi.ViewModels.PlaylistViewModel;
import com.example.doremi.ViewModels.SongsPlayerSharedViewModel;
import com.example.doremi.databinding.FragmentHomeBinding;

import java.util.List;


public class HomeFragment extends Fragment {

    private HomeFragmentViewModel homeFragmentViewModel;
    private SongsPlayerSharedViewModel songsPlayerSharedViewModel;
    private PlaylistViewModel playlistViewModel;
    private NewsViewModel newsViewModel;

    private HomeTopSongsAdapter topSongsAdapter;
    private HomePlaylistsAdapter playlistsAdapter;
    private HomeNewsAdapter newsAdapter;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        songsPlayerSharedViewModel = new ViewModelProvider(requireActivity()).get(SongsPlayerSharedViewModel.class);
        playlistViewModel = new ViewModelProvider(requireActivity()).get(PlaylistViewModel.class);
        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        songsPlayerSharedViewModel.setPlaylistUrl(Constants.songsListApi);
        songsPlayerSharedViewModel.setSongs();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        binding.homeFragmentTopSongsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        View root = binding.getRoot();

        initOnClickListener();

        return root;
    }

    private void initOnClickListener() {
        binding.topSongsBtn.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_nav_home_to_nav_songs));
        binding.playlistsBtn.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_nav_home_to_nav_playlists));
        binding.newsBtn.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_nav_home_to_newsFragment));
    }

    @Override
    public void onResume() {
        super.onResume();
        songsPlayerSharedViewModel.getSongs().observe(getViewLifecycleOwner(), songs -> {
            topSongsAdapter = new HomeTopSongsAdapter(getContext(), songs);
            binding.homeFragmentTopSongsRecyclerView.setAdapter(topSongsAdapter);
        });

        playlistViewModel.getPlaylists().observe(getViewLifecycleOwner(), playlists -> {
            playlistsAdapter = new HomePlaylistsAdapter(getContext(), playlists);
            binding.homeFragmentPlaylistsRecyclerView.setAdapter(playlistsAdapter);
        });

        newsViewModel.getNewsList().observe(getViewLifecycleOwner(), news -> {
            newsAdapter = new HomeNewsAdapter(getContext(), news);
            binding.homeFragmentNewsRecyclerView.setAdapter(newsAdapter);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}