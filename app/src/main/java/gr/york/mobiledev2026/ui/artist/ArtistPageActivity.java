package gr.york.mobiledev2026.ui.artist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import gr.york.mobiledev2026.R;
import gr.york.mobiledev2026.data.local.CollectionEntity;
import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.databinding.ActivityArtistPageBinding;
import gr.york.mobiledev2026.recycler.SimilarArtistsListAdapter;
import gr.york.mobiledev2026.recycler.TrackListAdapter;
import gr.york.mobiledev2026.ui.collection.CollectionViewModel;

public class ArtistPageActivity extends AppCompatActivity {

    private Artist artist;
    private boolean isSaved = false;
    private CollectionEntity currentCollectionEntity;

    private ArtistViewModel artistViewModel;
    private CollectionViewModel collectionViewModel;
    private ActivityArtistPageBinding binding;
    private TrackListAdapter trackAdapter;
    private SimilarArtistsListAdapter similarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArtistPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        artistViewModel = new ViewModelProvider(this).get(ArtistViewModel.class);
        collectionViewModel = new ViewModelProvider(this).get(CollectionViewModel.class);

        setupStaticClickListeners();
        setupRecyclerViews();

        String artistName = getIntent().getStringExtra("ARTIST_NAME");
        if (artistName != null && !artistName.isEmpty()) {
            observeArtistData(artistName);
        }
    }

    private void setupStaticClickListeners() {
        binding.backButton.setOnClickListener(v -> finish());
        binding.shareButton.setOnClickListener(v -> share());
        binding.saveButton.setOnClickListener(v -> toggleSaved());
    }

    private void setupRecyclerViews() {
        trackAdapter = new TrackListAdapter(new java.util.ArrayList<>());
        binding.tracksView.setLayoutManager(new LinearLayoutManager(this));
        binding.tracksView.setNestedScrollingEnabled(true);
        binding.tracksView.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });
        binding.tracksView.setClipToOutline(true);
        binding.tracksView.setAdapter(trackAdapter);

        similarAdapter = new SimilarArtistsListAdapter(new java.util.ArrayList<>(), item -> {
            Intent goToSimilarArtist = new Intent(ArtistPageActivity.this, ArtistPageActivity.class);
            goToSimilarArtist.putExtra("ARTIST_NAME", item.getName());
            startActivity(goToSimilarArtist);
        });
        binding.similarArtistsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        final int gap = (int) (8 * getResources().getDisplayMetrics().density);
        binding.similarArtistsView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) > 0) {
                    outRect.left = gap;
                }
            }
        });

        binding.similarArtistsView.setAdapter(similarAdapter);
    }

    private void observeArtistData(String artistName) {
        artistViewModel.getArtistByName(artistName).observe(this, artistData -> {
            if (artistData == null) return;

            this.artist = artistData;
            updateArtistUI();

            observeSavedStatus(artistData.getName());
            observeTracks(artistData.getName());
        });
    }

    private void updateArtistUI() {
        binding.headerTitle.setText(artist.getName());

        if (artist.getStats() != null) {
            binding.headerDescription.setText(getString(R.string.listeners_format, artist.getStats().getListeners()));
        } else {
            binding.headerDescription.setText("");
        }

        if (artist.getBiography() != null) {
            binding.profileDescription.setText(artist.getBiography().getContent());
            setExpandButton();
        }

        Glide.with(this)
            .load(artist.getImageUrl())
            .placeholder(R.mipmap.ic_launcher)
            .into(binding.profileImage);

        if (artist.getSimilarArtists() != null) {
            similarAdapter.updateData(artist.getSimilarArtists());
        }
    }

    private void observeTracks(String artistName) {
        artistViewModel.getArtistTopTracks(artistName).observe(this, tracks -> {
            if (tracks != null) {
                trackAdapter.updateData(tracks);
            }
        });
    }

    private void observeSavedStatus(String artistName) {
        collectionViewModel.findArtistByName(artistName).observe(this, entity -> {
            this.currentCollectionEntity = entity;
            this.isSaved = entity != null;
            changeButtonColor(isSaved);
        });
    }

    private void toggleSaved() {
        if (artist == null) return;

        if (!isSaved) {
            Glide.with(this)
                .asBitmap()
                .load(artist.getImageUrl())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        CollectionEntity newCollectionEntity = new CollectionEntity(artist.getName());
                        String imagePath = collectionViewModel.saveImage(artist.getName(), resource);
                        newCollectionEntity.setImagePath(imagePath);
                        collectionViewModel.save(newCollectionEntity);
                        Toast.makeText(ArtistPageActivity.this, R.string.saved, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        collectionViewModel.save(new CollectionEntity(artist.getName()));
                        Toast.makeText(ArtistPageActivity.this, R.string.saved, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {}
                });
        } else {
            if (currentCollectionEntity != null) {
                collectionViewModel.delete(currentCollectionEntity);
                Toast.makeText(this, "Removed from collection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void changeButtonColor(boolean isSaved) {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(com.google.android.material.R.attr.colorPrimaryContainer, typedValue, true);
        int colorPrimaryContainer = typedValue.data;

        getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnPrimaryContainer, typedValue, true);
        int colorOnPrimaryContainer = typedValue.data;

        if (isSaved) {
            binding.saveButton.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorOnPrimaryContainer));
            binding.saveButton.setIconTint(android.content.res.ColorStateList.valueOf(colorPrimaryContainer));
            binding.saveButton.setIconResource(R.drawable.round_bookmark_24);
            binding.saveButton.setTextColor(colorPrimaryContainer);
            binding.saveButton.setText(R.string.saved);
        } else {
            binding.saveButton.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorPrimaryContainer));
            binding.saveButton.setIconTint(android.content.res.ColorStateList.valueOf(colorOnPrimaryContainer));
            binding.saveButton.setIconResource(R.drawable.round_bookmark_border_24);
            binding.saveButton.setTextColor(colorOnPrimaryContainer);
            binding.saveButton.setText(R.string.save);
        }
    }

    private void setExpandButton() {
        TextView textView = binding.profileDescription;
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                android.text.Layout layout = textView.getLayout();
                if (layout != null) {
                    int lines = layout.getLineCount();
                    boolean isMoreText = lines > 0 && layout.getEllipsisCount(lines - 1) > 0;
                    if (isMoreText) {
                        binding.expandButton.setVisibility(View.VISIBLE);
                        binding.expandButton.setOnClickListener(v -> {
                            if (binding.profileDescription.getMaxLines() == 3) {
                                binding.profileDescription.setMaxLines(999);
                            } else {
                                binding.profileDescription.setMaxLines(3);
                            }
                        });
                    } else {
                        binding.expandButton.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void share() {
        if (artist == null) return;
        String shareText = "Check out this artist: " + artist.getName() +
            "\nListen on Last.fm: https://www.last.fm/music/" + artist.getName().replace(" ", "+");

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Share artist via:");
        startActivity(shareIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
