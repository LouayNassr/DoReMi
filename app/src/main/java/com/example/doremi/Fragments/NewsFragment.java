package com.example.doremi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doremi.Adapters.NewsAdapter;
import com.example.doremi.R;
import com.example.doremi.ViewModels.NewsViewModel;
import com.example.doremi.databinding.FragmentNewsBinding;

public class NewsFragment extends Fragment {

    NewsAdapter adapter;
    private NewsViewModel viewModel;
    private FragmentNewsBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        binding.newsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getNewsList().observe(getViewLifecycleOwner(), news -> {
            adapter = new NewsAdapter(getContext(), news);
            binding.newsRecyclerview.setAdapter(adapter);
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}