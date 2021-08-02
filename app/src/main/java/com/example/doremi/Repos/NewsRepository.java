package com.example.doremi.Repos;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.doremi.Constants;
import com.example.doremi.Models.News;
import com.example.doremi.Models.Playlist;
import com.example.doremi.Utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsRepository {

    private static final String LOG = PlaylistsRepository.class.getSimpleName();
    private MutableLiveData<List<News>> mNews = new MutableLiveData<>();
    Context ctx;

    public NewsRepository(@NonNull Context context) {
        ctx = context;
    }

    public LiveData<List<News>> getNews() {
        fetchNews();
        return mNews;
    }

    private void fetchNews() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Constants.newsApi, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<News> newsList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        News news = new News();
                        news.setTitle(object.getString("title"));
                        news.setDate(object.getString("date"));
                        news.setNewsUrl(object.getString("news_url"));
                        news.setCoverImage(object.getString("cover_image_url"));
                        newsList.add(news);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mNews.setValue(newsList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG, "error happened");
            }
        });
        VolleySingleton.getInstance(ctx.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }
}
