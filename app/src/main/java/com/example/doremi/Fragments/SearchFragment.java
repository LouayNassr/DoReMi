package com.example.doremi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doremi.Adapters.SearchPagerAdapter;
import com.example.doremi.R;
import com.example.doremi.ViewModels.SearchViewModel;
import com.example.doremi.databinding.FragmentSearchBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class SearchFragment extends Fragment {

    private final String[] titles = {"Top", "Songs", "Artists", "Albums", "Playlists"};
    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        binding.searchViewPager.setAdapter(new SearchPagerAdapter(this));

        new TabLayoutMediator(binding.searchTabLayout, binding.searchViewPager, ((tab, position) -> tab.setText(titles[position]))).attach();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}