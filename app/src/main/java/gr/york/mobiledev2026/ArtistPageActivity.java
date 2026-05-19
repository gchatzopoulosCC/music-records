package gr.york.mobiledev2026;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import gr.york.mobiledev2026.database.artist.ArtistEntity;
import gr.york.mobiledev2026.databinding.ActivityArtistPageBinding;
import gr.york.mobiledev2026.recycler.SimilarArtistsListAdapter;
import gr.york.mobiledev2026.recycler.TrackListAdapter;
import gr.york.mobiledev2026.ui.artist.ArtistViewModel;

public class ArtistPageActivity extends AppCompatActivity {

    ArtistEntity artist;
    private ArtistViewModel artistsViewModel;

    private ActivityArtistPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArtistPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        artistsViewModel = new ViewModelProvider(this).get(ArtistViewModel.class);

        Intent intent = getIntent();

        String artistName = intent.getStringExtra("ARTIST_NAME");

        if (artistName != null) {
            artistsViewModel.findArtistByName(artistName).observe(this, artistEntity -> {
                    if (artistEntity != null) {
                        // This is where you actually get the value!
                        this.artist = artistEntity;

                        // Update your UI with the artist data
                        binding.headerTitle.setText(artist.getName());

                        binding.headerDescription.setText(artist.getListeners() + " listeners");

                        binding.profileImage.setImageBitmap(artist.getBitmap());

                        boolean isSaved = artist.isSaved();

                        changeButtonColor(isSaved);

                        binding.shareButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                share();
                            }
                        });

                        binding.backButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });

                        binding.saveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toggleSaved();
                            }
                        });

                        binding.profileDescription.setText(artist.getDescription());

                        setExpandButton();

                        // 1. Create dummy data or get it from your artist object

// 2. Setup the RecyclerView (the one inside your ScrollView)
                        TrackListAdapter trackAdapter = new TrackListAdapter(artistsViewModel.getArtistTopTracks(artist.getName()));
                        binding.tracksView.setLayoutManager(new LinearLayoutManager(this));
                        binding.tracksView.setAdapter(trackAdapter);

                        // 1. Setup the LayoutManager as Horizontal
                        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                        binding.similarArtistsView.setLayoutManager(layoutManager);

// 2. Set the Adapter
                        SimilarArtistsListAdapter similarAdapter = new SimilarArtistsListAdapter(artistsViewModel.getSimilarArtistsList());
                        binding.similarArtistsView.setAdapter(similarAdapter);

                    }});
            }


    }

    private void setExpandButton() {
        TextView textView = binding.profileDescription;
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove listener instantly to avoid continuous loops
                textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                android.text.Layout layout = textView.getLayout();
                if (layout != null) {
                    int lines = layout.getLineCount();
                    if (lines > 3) {
                        binding.expandButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (binding.profileDescription.getMaxLines() == 3) {
                                    binding.profileDescription.setMaxLines(999);
                                } else {
                                    binding.profileDescription.setMaxLines(3);
                                }
                            }
                        });
                    } else {
                        binding.expandButton.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void toggleSaved() {
        artist.isSaved = !artist.isSaved;
        artistsViewModel.setSaved(artist.isSaved);
        changeButtonColor(artist.isSaved);
    }

    private void changeButtonColor(boolean isSaved) {
        // 1. Resolve the colors from your theme attributes
        int colorPrimaryContainer = com.google.android.material.color.MaterialColors
            .getColor(this, com.google.android.material.R.attr.colorPrimaryContainer, android.graphics.Color.BLACK);

        int colorOnPrimaryContainer = com.google.android.material.color.MaterialColors
            .getColor(this, com.google.android.material.R.attr.colorOnPrimaryContainer, android.graphics.Color.WHITE);

// 2. Apply the colors based on the isSaved state
        if (isSaved) {
            // Background -> colorOnPrimaryContainer
            binding.saveButton.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorOnPrimaryContainer));

            // Icon -> colorPrimaryContainer
            binding.saveButton.setIconTint(android.content.res.ColorStateList.valueOf(colorPrimaryContainer));

            // Text -> colorPrimaryContainer
            binding.saveButton.setTextColor(colorPrimaryContainer);

            // Optional: Change text to "Saved"
            binding.saveButton.setText(R.string.saved);
            binding.saveButton.setIconResource(R.drawable.round_bookmark_24); // Solid icon
        } else {
            // Reset to default colors (Inverse)
            binding.saveButton.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorPrimaryContainer));
            binding.saveButton.setIconTint(android.content.res.ColorStateList.valueOf(colorOnPrimaryContainer));
            binding.saveButton.setTextColor(colorOnPrimaryContainer);

            binding.saveButton.setText(R.string.save);
            binding.saveButton.setIconResource(R.drawable.round_bookmark_border_24); // Border icon
        }
    }

    private void share(){
        String shareText = "Check out this artist: " + artist.getName() +
            "\nListen on Last.fm: https://www.last.fm/music/" + artist.getName().replace(" ", "+");

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, "Share artist via:");
        startActivity(shareIntent);
    }

}
