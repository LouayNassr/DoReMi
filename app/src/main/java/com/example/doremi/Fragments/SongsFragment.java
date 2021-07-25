package com.example.doremi.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.doremi.Adapters.SongsAdapter;
import com.example.doremi.Models.Song;
import com.example.doremi.ViewModels.SongsPlayerSharedViewModel;
import com.example.doremi.databinding.FragmentSongsBinding;

import java.util.List;

public class SongsFragment extends Fragment {

    private static final String LOG = SongsFragment.class.getSimpleName();
    SongsAdapter adapter;
    SongsPlayerSharedViewModel viewModel;
    private FragmentSongsBinding binding;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSongsBinding.inflate(inflater, container, false);
        binding.songsListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel = new ViewModelProvider(requireActivity()).get(SongsPlayerSharedViewModel.class);

        String url = getPlaylistUrl();
        if (url != null && url != "") {
            viewModel.setPlaylistUrl(url);
        }
        viewModel.setSongs();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getSongs().observe(getViewLifecycleOwner(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {

                adapter = new SongsAdapter(getContext(), songs);
                binding.songsListRecyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // this method gets the url for array of songs the comes from playlist fragment
    private String getPlaylistUrl() {
        try {
            String url = getArguments().getString("playlistUrl");
            if (url != null && url != "") {
                return url;
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }
}