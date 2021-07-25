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
import com.example.doremi.Models.Playlist;
import com.example.doremi.Utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsRepository {
    private static final String LOG = PlaylistsRepository.class.getSimpleName();
    private MutableLiveData<List<Playlist>> mPlaylists = new MutableLiveData<>();
    Context ctx;

    public PlaylistsRepository(@NonNull Context context) {
        ctx = context;
    }
    public LiveData<List<Playlist>> getPlaylists() {
        fetchPlaylists();
        return mPlaylists;
    }

    private void fetchPlaylists() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Constants.playlistsApi, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Playlist> playlists = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Playlist playlist = new Playlist();
                        playlist.setName(object.getString("name"));
                        playlist.setSongsCount(object.getString("songs_count"));
                        playlist.setCoverImageUrl(object.getString("cover_image"));
                        playlist.setUrl(object.getString("url"));
                        playlists.add(playlist);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mPlaylists.setValue(playlists);
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
