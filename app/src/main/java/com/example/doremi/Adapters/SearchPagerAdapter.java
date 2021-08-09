package com.example.doremi.Adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.doremi.Fragments.SearchFragment;
import com.example.doremi.Fragments.SongsFragment;

import org.jetbrains.annotations.NotNull;

public class SearchPagerAdapter extends FragmentStateAdapter {

    private final int pagesCount = 5;

    public SearchPagerAdapter(@NonNull @NotNull SearchFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                // TODO: 8/4/2021 replace songsfragment with empty activity
                return new SongsFragment();
            case 1:
                return new SongsFragment();
            case 2:
                return new SongsFragment();
            case 3:
                return new SongsFragment();
        }
        return new SongsFragment();
    }

    @Override
    public int getItemCount() {
        return pagesCount;
    }
}
