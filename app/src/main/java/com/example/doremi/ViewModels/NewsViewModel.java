package com.example.doremi.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doremi.Models.News;
import com.example.doremi.Repos.NewsRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    public NewsViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    private NewsRepository repository = new NewsRepository(getApplication());
    private LiveData<List<News>> mNewsList = repository.getNews();

    public LiveData<List<News>> getNewsList() {
        return mNewsList;
    }

}