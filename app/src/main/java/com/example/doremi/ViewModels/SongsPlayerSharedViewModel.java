package com.example.doremi.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doremi.Constants;
import com.example.doremi.Models.Song;
import com.example.doremi.Repos.SongsRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SongsPlayerSharedViewModel extends AndroidViewModel {

    public String playlistUrl = Constants.songsListApi;
    private LiveData<List<Song>> mSongs;
    private SongsRepository repository = new SongsRepository(getApplication());

    public SongsPlayerSharedViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public String getPlaylistUrl() {
        return playlistUrl;
    }

    public void setPlaylistUrl(String url) {
        playlistUrl = url;
    }

    public LiveData<List<Song>> getSongs() {
        return mSongs;
    }

    public void setSongs() {
        mSongs = repository.getSongs(getPlaylistUrl());
    }
}