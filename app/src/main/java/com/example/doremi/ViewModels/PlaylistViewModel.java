package com.example.doremi.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doremi.Models.Playlist;
import com.example.doremi.Repos.PlaylistsRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlaylistViewModel extends AndroidViewModel {

    private PlaylistsRepository repository = new PlaylistsRepository(getApplication());
    private LiveData<List<Playlist>> mPlaylists = repository.getPlaylists();

    public PlaylistViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public LiveData<List<Playlist>> getPlaylists() {
        return mPlaylists;
    }
}