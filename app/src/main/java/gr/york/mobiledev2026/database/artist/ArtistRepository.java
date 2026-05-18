package gr.york.mobiledev2026.database.artist;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.bumptech.glide.Glide;

import gr.york.mobiledev2026.database.Db;

public class ArtistRepository {
    private static final String TAG = ArtistRepository.class.getSimpleName();
    private final ArtistDao artistDao;
    private final ExecutorService executorService;
    private final Application application;

    public ArtistRepository(Application application) {
        this.application = application;
        Db db = Db.getDatabase(application);
        artistDao = db.artistDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    private static void requireNonEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }

    private void runAsync(String operation, Runnable task) {
        executorService.execute(() -> {
            try {
                task.run();
            } catch (Exception e) {
                Log.e(TAG, operation + " failed", e);
            }
        });
    }

    public LiveData<List<ArtistEntity>> getAllArtists() {
        return artistDao.readAll();
    }

    public LiveData<List<ArtistEntity>> getArtistsUpTo(int lim) {
        if (lim <= 0) {
            throw new IllegalArgumentException("Limit must be greater than 0");
        }
        return artistDao.readUpTo(lim);
    }

    public LiveData<List<ArtistEntity>> searchArtistsByName(String query) {
        requireNonEmpty(query, "Search Query");
        return artistDao.searchByName(query);
    }

    public LiveData<ArtistEntity> findArtistByName(String name) {
        requireNonEmpty(name, "Artist Name");
        return artistDao.findByName(name);
    }

    public String getImagePathByName(String name) {
        requireNonEmpty(name, "Artist Name");
        return artistDao.getImagePathByName(name);
    }

    public LiveData<String> getImagePathLiveDataByName(String name) {
        requireNonEmpty(name, "Artist Name");
        return artistDao.getImagePathLiveDataByName(name);
    }

    public LiveData<Bitmap> loadImageByName(String name) {
        requireNonEmpty(name, "Artist Name");

        MutableLiveData<Bitmap> image = new MutableLiveData<>();
        executorService.execute(() -> {
            String path = artistDao.getImagePathByName(name);
            if (path != null) {
                try {
                    Bitmap bitmap = Glide.with(application)
                            .asBitmap()
                            .load(path)
                            .submit()
                            .get();
                    image.postValue(bitmap);
                } catch (Exception e) {
                    Log.e(TAG, "Failed to load image", e);
                    image.postValue(null);
                }
            } else {
                image.postValue(null);
            }
        });

        return image;
    }

    public String saveImage(Context context, String name, Bitmap image) {
        requireNonEmpty(name, "Artist Name");
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }

        String slug = name.toLowerCase().replace(" ", "_");
        String filename = slug + "_" + System.currentTimeMillis() + ".jpg";
        File file = new File(context.getFilesDir(), filename);

        try (FileOutputStream stream = new FileOutputStream(file)) {
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        } catch (IOException e) {
            Log.e(TAG, "Failed to save image for " + name, e);
            return null;
        }

        String path = file.getAbsolutePath();
        executorService.execute(() -> {
            ArtistEntity artist = artistDao.findByNameSync(name);
            if (artist != null) {
                artist.setImagePath(path);
                artistDao.update(artist);
            }
        });

        return path;
    }

    public void insert(ArtistEntity artist) {
        runAsync("insert " + artist.getName(), () -> artistDao.insert(artist));
    }

    public void update(ArtistEntity artist) {
        runAsync("update " + artist.getName(), () -> artistDao.update(artist));
    }

    public void save(ArtistEntity artist) {
        runAsync("save " + artist.getName(), () -> artistDao.save(artist));
    }

    public void delete(ArtistEntity artist) {
        runAsync("delete " + artist.getName(), () -> artistDao.delete(artist));
    }
}
