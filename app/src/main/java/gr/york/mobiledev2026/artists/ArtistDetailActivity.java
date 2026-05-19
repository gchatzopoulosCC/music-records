/*
package gr.york.mobiledev2026.artists;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import gr.york.mobiledev2026.R;
import gr.york.mobiledev2026.TrackAdapter;
import gr.york.mobiledev2026.database.ArtistEntity;
import gr.york.mobiledev2026.database.MyDatabase;
import gr.york.mobiledev2026.databinding.ActivityArtistDetailBinding;
import gr.york.mobiledev2026.recycler.CustomAdapter;
import android.os.Bundle;

@Override
public class ArtistDetailActivity {
    private ActivityArtistDetailBinding binding;
    private MyDatabase database;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private boolean isSaved = false;

    // Placeholder data
    private final String artistName = "The Beatles";
    private final String artistListeners = "12,000,000 listeners";
    private final String artistBio = "The Beatles were an English rock band formed in Liverpool in 1960...";
    private final String artistUrl = "https://www.last.fm/music/The+Beatles";




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArtistDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "my_database")
                .fallbackToDestructiveMigration()
                .build();

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        setupButtons();
        setupRecyclerViews();
        bindArtistData(artistName, artistListeners, artistBio);
        checkIfSaved();
        setupToolbarTitle();
    }
}
*/
