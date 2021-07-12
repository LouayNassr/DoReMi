package com.example.doremi.Fragments.GalleryFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.doremi.Constants;
import com.example.doremi.Models.Song;
import com.example.doremi.Utils.VolleySingleton;
import com.example.doremi.ViewModels.GalleryViewModel;
import com.example.doremi.databinding.FragmentGalleryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private static final String LOG = GalleryFragment.class.getSimpleName();
    public List<Song> songs;
    SongsAdapter adapter;
    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);

        songs = new ArrayList<>();
        extractSongs();

        return binding.getRoot();
    }

    private void extractSongs() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Constants.songsListApi, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
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
                binding.songsListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new SongsAdapter(getContext(), songs);
                binding.songsListRecyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG, "error happened");
            }
        });
        VolleySingleton.getInstance(this.getContext()).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}