package gr.york.mobiledev2026.ui.collection;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import gr.york.mobiledev2026.ui.artist.BrowseArtistsActivity;
import gr.york.mobiledev2026.R;
import gr.york.mobiledev2026.ui.track.TracksActivity;
import gr.york.mobiledev2026.databinding.CollectionsBinding;
import gr.york.mobiledev2026.recycler.CollectionListAdapter;

public class CollectionsActivity extends AppCompatActivity {


    private CollectionsBinding binding;

    private CollectionViewModel collectionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = CollectionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        collectionViewModel = new ViewModelProvider(this).get(CollectionViewModel.class);

        binding.bottomNavigationView.setSelectedItemId(R.id.collections);

        binding.bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.home) {
                startActivity(new Intent(this, BrowseArtistsActivity.class));
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

        CollectionListAdapter adapter = new CollectionListAdapter(new ArrayList<>(), (item) -> {
            collectionViewModel.delete(item);
        });

        binding.recycleView.setAdapter(adapter);

        collectionViewModel.getAllArtists().observe(this, list -> {
            if (list != null) {
                // Just update the list names/paths in the adapter
                adapter.updateData(list);
            }
        });
    }


}
