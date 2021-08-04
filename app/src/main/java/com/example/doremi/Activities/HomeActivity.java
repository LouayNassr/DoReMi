package com.example.doremi.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.doremi.R;
import com.example.doremi.databinding.ActivityHomeBinding;

import org.jetbrains.annotations.NotNull;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_songs, R.id.nav_playlists, R.id.nav_home)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_activity_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_search:
                switch (navController.getCurrentDestination().getId()) {
                    case R.id.nav_home:
                        navController.navigate(R.id.action_nav_home_to_nav_search);
                        break;
                    case R.id.nav_playlists:
                        navController.navigate(R.id.action_nav_playlists_to_nav_search);
                        break;
                    case R.id.nav_songs:
                        navController.navigate(R.id.action_nav_songs_to_nav_search);
                        break;
                }
        }
        return super.onOptionsItemSelected(item);
    }
}