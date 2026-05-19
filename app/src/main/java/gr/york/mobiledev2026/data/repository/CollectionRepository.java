package gr.york.mobiledev2026.data.repository;

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

import com.bumptech.glide.Glide;

import gr.york.mobiledev2026.data.local.AppDatabase;
import gr.york.mobiledev2026.data.local.AppExecutors;
import gr.york.mobiledev2026.data.local.CollectionDao;
import gr.york.mobiledev2026.data.local.CollectionEntity;

public class CollectionRepository {
    private static final String TAG = CollectionRepository.class.getSimpleName();
    private final CollectionDao collectionDao;
    private final AppExecutors executors;
    private final Application application;

    public CollectionRepository(Application application) {
        this.application = application;
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        collectionDao = appDatabase.artistDao();
        executors = AppExecutors.getInstance();
    }

    private static void requireNonEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }

    private void runAsync(String operation, Runnable task) {
        executors.diskIO().execute(() -> {
            try {
                task.run();
            } catch (Exception e) {
                Log.e(TAG, operation + " failed", e);
            }
        });
    }

    public LiveData<List<CollectionEntity>> getAllArtists() {
        return collectionDao.readAll();
    }

    public LiveData<List<CollectionEntity>> getArtistsUpTo(int lim) {
        if (lim <= 0) {
            throw new IllegalArgumentException("Limit must be greater than 0");
        }
        return collectionDao.readUpTo(lim);
    }

    public LiveData<List<CollectionEntity>> searchArtistsByName(String query) {
        requireNonEmpty(query, "Search Query");
        return collectionDao.searchByName(query);
    }

    public LiveData<CollectionEntity> findArtistByName(String name) {
        requireNonEmpty(name, "Artist Name");
        return collectionDao.findByName(name);
    }

    public String getImagePathByName(String name) {
        requireNonEmpty(name, "Artist Name");
        return collectionDao.getImagePathByName(name);
    }

    public LiveData<String> getImagePathLiveDataByName(String name) {
        requireNonEmpty(name, "Artist Name");
        return collectionDao.getImagePathLiveDataByName(name);
    }

    public LiveData<Bitmap> loadImageByName(String name) {
        requireNonEmpty(name, "Artist Name");

        MutableLiveData<Bitmap> image = new MutableLiveData<>();
        executors.diskIO().execute(() -> {
            String path = collectionDao.getImagePathByName(name);
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
        executors.diskIO().execute(() -> {
            CollectionEntity artist = collectionDao.findByNameSync(name);
            if (artist != null) {
                artist.setImagePath(path);
                collectionDao.update(artist);
            }
        });

        return path;
    }

    public void insert(CollectionEntity artist) {
        runAsync("insert " + artist.getName(), () -> collectionDao.insert(artist));
    }

    public void update(CollectionEntity artist) {
        runAsync("update " + artist.getName(), () -> collectionDao.update(artist));
    }

    public void save(CollectionEntity artist) {
        runAsync("save " + artist.getName(), () -> collectionDao.save(artist));
    }

    public void delete(CollectionEntity artist) {
        runAsync("delete " + artist.getName(), () -> collectionDao.delete(artist));
    }
}
