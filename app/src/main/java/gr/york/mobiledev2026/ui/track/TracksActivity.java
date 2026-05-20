package gr.york.mobiledev2026.ui.track;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gr.york.mobiledev2026.R;
import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.databinding.TracksBinding;
import gr.york.mobiledev2026.recycler.ArtistsListAdapter;
import gr.york.mobiledev2026.recycler.CollectionListAdapter;
import gr.york.mobiledev2026.recycler.TracksAdapter;
import gr.york.mobiledev2026.ui.artist.ArtistPageActivity;
import gr.york.mobiledev2026.ui.artist.BrowseArtistsActivity;
import gr.york.mobiledev2026.ui.collection.CollectionsActivity;

public class TracksActivity extends AppCompatActivity {

    private TracksBinding binding;

    private TrackViewModel trackViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = TracksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        trackViewModel = new ViewModelProvider(this).get(TrackViewModel.class);

        binding.bottomNavigationView.setSelectedItemId(R.id.tracks);

        binding.bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.home) {
                startActivity(new Intent(this, BrowseArtistsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.tracks) {
                return true;
            } else if (id == R.id.collections) {
                startActivity(new Intent(this, CollectionsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });

        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));

        final int spacing = (int) (16 * getResources().getDisplayMetrics().density);

        binding.recycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                int spanCount = 2;
                int column = position % spanCount;

                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;
                outRect.bottom = spacing;
            }
        });

        TracksAdapter adapter = new TracksAdapter(new ArrayList<>(), artist -> {
            Intent intent = new Intent(TracksActivity.this, ArtistPageActivity.class);
            intent.putExtra("ARTIST_NAME", artist.getName());
            startActivity(intent);
        });

        binding.recycleView.setAdapter(adapter);

        trackViewModel.getTracksList().observe(this, tracks -> {
            if (tracks != null) {
                adapter.updateData(tracks);
            }
        });
    }


}
