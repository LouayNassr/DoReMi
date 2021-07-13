package com.example.doremi.Fragments.GalleryFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.doremi.Models.Song;
import com.example.doremi.ViewModels.GalleryViewModel;
import com.example.doremi.databinding.FragmentGalleryBinding;

import java.util.List;

public class GalleryFragment extends Fragment {

    private static final String LOG = GalleryFragment.class.getSimpleName();
    SongsAdapter adapter;
    private GalleryViewModel viewModel;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        binding.songsListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.getSongs().observe(getViewLifecycleOwner(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                adapter = new SongsAdapter(getContext(),songs);
                binding.songsListRecyclerView.setAdapter(adapter);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}