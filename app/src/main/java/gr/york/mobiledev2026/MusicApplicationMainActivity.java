package gr.york.mobiledev2026;

import static androidx.core.graphics.drawable.DrawableKt.toBitmap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//import gr.york.mobiledev2026.artists.ArtistDetailActivity;
import gr.york.mobiledev2026.database.ArtistEntity;
import gr.york.mobiledev2026.databinding.ActivityMusicMainBinding;
import gr.york.mobiledev2026.recycler.ArtistsListAdapter;
//import gr.york.mobiledev2026.recycler.CustomAdapter;
//import gr.york.mobiledev2026.sample.ArtistDetailActivity;

public class MusicApplicationMainActivity extends AppCompatActivity {

    private static final String TAG = "MusicMainActivity";
    private ActivityMusicMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter logic would go here
                return true;
            }
        });

        setupRecyclerView();
        setupSearchBar();
    }

    private void setupSearchBar() {
        int secondaryColor = com.google.android.material.color.MaterialColors.getColor(binding.searchView, com.google.android.material.R.attr.colorSecondary);

        ImageView searchIcon = binding.searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        ImageView closeIcon = binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn);

        if (searchIcon != null) searchIcon.setColorFilter(secondaryColor);
        if (closeIcon != null) closeIcon.setColorFilter(secondaryColor);
    }

    private void setupRecyclerView() {
        List<ArtistEntity> data = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            data.add(new ArtistEntity("Name "+i, drawableToBitmap(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.round_bookmark_border_24)))));
        }

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

        binding.recycleView.setAdapter(new ArtistsListAdapter(data));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult called with requestCode: " + requestCode + ", resultCode: " + resultCode);
    }










    public Bitmap drawableToBitmap(Drawable drawable) {
        // Take intrinsic dimensions, default to 1 if layout bounds aren't set yet
        int width = drawable.getIntrinsicWidth() > 0 ? drawable.getIntrinsicWidth() : 1;
        int height = drawable.getIntrinsicHeight() > 0 ? drawable.getIntrinsicHeight() : 1;

        // Create a mutable bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Bind canvas to the bitmap and draw the drawable onto it
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}