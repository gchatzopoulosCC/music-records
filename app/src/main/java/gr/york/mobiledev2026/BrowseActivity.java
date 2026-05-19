package gr.york.mobiledev2026;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gr.york.mobiledev2026.databinding.BrowseBinding;
import gr.york.mobiledev2026.recycler.ArtistsListAdapter;
import gr.york.mobiledev2026.ui.artist.ArtistViewModel;

public class BrowseActivity extends AppCompatActivity {

    private BrowseBinding binding;
    private ArtistViewModel artistsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BrowseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel
        artistsViewModel = new ViewModelProvider(this).get(ArtistViewModel.class);

        // UI Setup
        setupNavigation();
        setupRecyclerView();
        setupSearchBar();
    }

    private void setupNavigation() {
        binding.bottomNavigationView.setSelectedItemId(R.id.home);
        binding.bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.home) {
                return true;
            } else if (id == R.id.tracks) {
                startActivity(new Intent(this, TracksActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.collections) {
                startActivity(new Intent(this, CollectionsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }

    private void setupSearchBar() {
        int secondaryColor = com.google.android.material.color.MaterialColors.getColor(binding.searchView, com.google.android.material.R.attr.colorSecondary);

        ImageView searchIcon = binding.searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        ImageView closeIcon = binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn);

        if (searchIcon != null) searchIcon.setColorFilter(secondaryColor);
        if (closeIcon != null) closeIcon.setColorFilter(secondaryColor);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                artistsViewModel.setSearchQuery(newText);
                return true;
            }
        });
    }

    private void setupRecyclerView() {
        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));

        // Moved spacing calculation here to prevent the crash
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

        ArtistsListAdapter adapter = new ArtistsListAdapter(new ArrayList<>(), artist -> {
            Intent intent = new Intent(BrowseActivity.this, ArtistPageActivity.class);
            intent.putExtra("ARTIST_NAME", artist.getName());
            startActivity(intent);
        });
        binding.recycleView.setAdapter(adapter);

        // Observe the search results
        artistsViewModel.getSearchResults().observe(this, artists -> {
            if (artists != null) {
                adapter.updateData(artists);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
