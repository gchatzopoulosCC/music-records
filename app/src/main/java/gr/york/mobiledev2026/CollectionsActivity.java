package gr.york.mobiledev2026;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import gr.york.mobiledev2026.database.artist.ArtistEntity;
import gr.york.mobiledev2026.database.collection.CollectionItem;
import gr.york.mobiledev2026.databinding.CollectionsBinding;
import gr.york.mobiledev2026.databinding.TracksBinding;
import gr.york.mobiledev2026.recycler.CollectionListAdapter;
import gr.york.mobiledev2026.ui.artist.ArtistViewModel;

public class CollectionsActivity extends AppCompatActivity {

    private CollectionsBinding binding;

    private ArtistViewModel artistsViewModel;

    private List<ArtistEntity> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CollectionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        artistsViewModel = new ViewModelProvider(this).get(ArtistViewModel.class);
        artistsViewModel.getAllArtists().observe(this, i -> {
            items = i;
        });

        binding.bottomNavigationView.setSelectedItemId(R.id.collections);

        binding.bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.home) {
                startActivity(new Intent(this, BrowseActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.tracks) {
                startActivity(new Intent(this, TracksActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.collections) {
                return true;
            }
            return false;
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.recycleView.setAdapter(new CollectionListAdapter(items, (item)->{
            artistsViewModel.delete(item.getName());
        }));
    }


}
