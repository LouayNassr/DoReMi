package com.example.doremi.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

public class SongsModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final Application application;
    private final String url;

    public SongsModelFactory(@NonNull Application application, String url) {
        this.application = application;
        this.url = url;
    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if (modelClass == SongsViewModel.class) {
            return (T) new SongsViewModel(application, url);
        }
        return null;
    }
}
