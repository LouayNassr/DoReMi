package com.example.doremi.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doremi.Models.Song;
import com.example.doremi.Repos.SongsRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GalleryViewModel extends AndroidViewModel {
    private SongsRepository repository = new SongsRepository(getApplication());

    private LiveData<List<Song>> mSongs = repository.getSongs();

//    private final MutableLiveData<Song> selectedItem = new MutableLiveData<>();

    public GalleryViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public LiveData<List<Song>> getSongs() {
        return mSongs;
    }

//    public void selectSong(Song song) {
//        selectedItem.setValue(song);
//    }
//
//    public LiveData<Song> getSelectedSong() {
//        return selectedItem;
//    }
//
//    public boolean hasNext(int position) {
//        return position<mSongs.getValue().size()-1;
//    }
//
//    public boolean hasPrevious(int position) {
//        return position>0;
//    }

}