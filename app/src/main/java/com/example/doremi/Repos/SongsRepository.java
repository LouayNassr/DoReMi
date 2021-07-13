package com.example.doremi.Repos;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.doremi.Constants;
import com.example.doremi.Fragments.GalleryFragment.SongsAdapter;
import com.example.doremi.Models.Song;
import com.example.doremi.Utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SongsRepository {

    private static final String LOG = SongsRepository.class.getSimpleName();
    private MutableLiveData<List<Song>> mSongs = new MutableLiveData<>();
    Context ctx;

    public SongsRepository(@NonNull Context context) {
        ctx = context;
    }
    public LiveData<List<Song>> getSongs() {
        querySongs();
        return mSongs;
    }

    private void querySongs() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Constants.songsListApi, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Song> songs = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject songObject = response.getJSONObject(i);
                        Song song = new Song();
                        song.setSong_en_title(songObject.getString("song_ar_title"));
                        song.setSong_artist_name(songObject.getString("song_artist_name"));
                        song.setSong_cover_img(songObject.getString("song_cover_img"));
                        song.setSong_url(songObject.getString("song_url"));
                        songs.add(song);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                mSongs.postValue(songs);
                mSongs.setValue(songs);
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
