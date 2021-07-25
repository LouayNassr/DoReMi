package com.example.doremi.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.doremi.Adapters.PlaylistsAdapter;
import com.example.doremi.ViewModels.PlaylistViewModel;
import com.example.doremi.databinding.FragmentPlaylistsBinding;

public class PlaylistFragment extends Fragment {

    PlaylistsAdapter adapter;
    private PlaylistViewModel viewModel;
    private FragmentPlaylistsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(PlaylistViewModel.class);

        binding = FragmentPlaylistsBinding.inflate(inflater, container, false);
        binding.playlistsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getPlaylists().observe(getViewLifecycleOwner(), playlists -> {
            adapter = new PlaylistsAdapter(getContext(), playlists);
            binding.playlistsRecyclerview.setAdapter(adapter);
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}