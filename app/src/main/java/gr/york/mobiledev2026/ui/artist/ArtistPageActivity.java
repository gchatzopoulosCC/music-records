package gr.york.mobiledev2026.ui.artist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import gr.york.mobiledev2026.R;
import gr.york.mobiledev2026.data.local.CollectionEntity;
import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.data.model.Track;
import gr.york.mobiledev2026.data.service.ArtistService;
import gr.york.mobiledev2026.databinding.ActivityArtistPageBinding;
import gr.york.mobiledev2026.recycler.SimilarArtistsListAdapter;
import gr.york.mobiledev2026.recycler.TrackListAdapter;
import gr.york.mobiledev2026.ui.collection.CollectionViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ArtistPageActivity extends AppCompatActivity {
    private final CompositeDisposable disposables = new CompositeDisposable();

    Artist artist;
    boolean isSaved = false;

    ArtistViewModel artistViewModel;
    CollectionViewModel collectionViewModel;

    private ActivityArtistPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArtistPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        artistViewModel = new ViewModelProvider(this).get(ArtistViewModel.class);
        collectionViewModel = new ViewModelProvider(this).get(CollectionViewModel.class);

        Intent intent = getIntent();
        String artistName = intent.getStringExtra("ARTIST_NAME");
        if (artistName != null && !artistName.isEmpty()) {
            disposables.add(artistViewModel.getArtistByName(artistName)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe( response -> {
                                    artist = response.getData();
                                    binding.headerTitle.setText(artist.getName());
                                    binding.headerDescription.setText(getString(R.string.listeners_format, artist.getStats().getListeners()));

                                    setExpandButton();
                                    Glide.with(binding.profileImage.getContext())
                                        .load(artist.getImageUrl())
                                        .placeholder(R.mipmap.ic_launcher) // Show this while loading
                                        .into(binding.profileImage);

                                    collectionViewModel.findArtistByName(artist.getName()).observe(this, entity -> {
                                        isSaved = entity != null;
                                        changeButtonColor(isSaved);
                                    });

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
                                    binding.profileDescription.setText(artist.getBiography().getContent());

                //                  TODO
                                    TrackListAdapter trackAdapter = new TrackListAdapter(artist.getTopTracks());
                                    binding.tracksView.setLayoutManager(new LinearLayoutManager(this));
                                    binding.tracksView.setAdapter(trackAdapter);

                                    SimilarArtistsListAdapter similarAdapter = new SimilarArtistsListAdapter(artist.getSimilarArtists(), artist -> {
                                        Intent goToSimilarArtist = new Intent(ArtistPageActivity.this, ArtistPageActivity.class);
                                        goToSimilarArtist.putExtra("ARTIST_NAME", artist.getName());
                                        startActivity(goToSimilarArtist);
                                    });
                                    binding.similarArtistsView.setLayoutManager(new LinearLayoutManager(this));
                                    binding.similarArtistsView.setAdapter(similarAdapter);
                }));
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
        isSaved = !isSaved;
        if (isSaved) {
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
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {}
                });
        } else {
//            TODO
//            collectionViewModel.delete();
        }
        changeButtonColor(isSaved);
    }

    private void changeButtonColor(boolean isSaved) {
        int colorPrimaryContainer = com.google.android.material.R.attr.colorPrimaryContainer;
        int colorOnPrimaryContainer = com.google.android.material.R.attr.colorOnPrimaryContainer;

        if (isSaved) {
            binding.saveButton.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorOnPrimaryContainer));

            binding.saveButton.setIconTint(android.content.res.ColorStateList.valueOf(colorPrimaryContainer));
            binding.saveButton.setIconResource(R.drawable.round_bookmark_24);

            binding.saveButton.setTextColor(colorPrimaryContainer);
            binding.saveButton.setText(R.string.saved);
        } else {
            binding.saveButton.setBackgroundTintList(android.content.res.ColorStateList.valueOf(colorPrimaryContainer));
            binding.saveButton.setIconTint(android.content.res.ColorStateList.valueOf(colorOnPrimaryContainer));
            binding.saveButton.setTextColor(colorOnPrimaryContainer);

            binding.saveButton.setText(R.string.save);
            binding.saveButton.setIconResource(R.drawable.round_bookmark_border_24);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
