package gr.york.mobiledev2026.database;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArtistRepository {
    private final ArtistDao artistDao;
    private final ExecutorService executorService;

    public ArtistRepository(Application application) {
        Db db = Db.getDatabase(application);
        artistDao = db.artistDao();
        executorService = Executors.newSingleThreadExecutor();
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
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Search query cannot be null or empty");
        }
        return artistDao.searchByName(query);
    }

    public LiveData<ArtistEntity> findArtistByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return artistDao.findByName(name);
    }

    public String getImagePathByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return artistDao.getImagePathByName(name);
    }

    public LiveData<Bitmap> loadImageByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        MutableLiveData<Bitmap> image = new MutableLiveData<>();
        executorService.execute(() -> {
            String path = artistDao.getImagePathByName(name);
            if (path != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                image.postValue(bitmap);
            } else {
                image.postValue(null);
            }
        });

        return image;
    }

    public String saveImage(Context context, String name, Bitmap image) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }

        String slug = name.toLowerCase().replace(" ", "_");
        String filename = slug + "_" + System.currentTimeMillis() + ".jpg";
        File file = new File(context.getFilesDir(), filename);

        try (FileOutputStream stream = new FileOutputStream(file)) {
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        } catch (IOException e) {
            Log.e("ArtistRepository", "Failed to save image for " + name, e);
            return null;
        }

        String path = file.getAbsolutePath();
        executorService.execute(() -> {
            ArtistEntity artist = artistDao.findByName(name).getValue();
            if (artist != null) {
                artist.setImagePath(path);
                artistDao.update(artist);
            }
        });

        return path;
    }

    public void insert(ArtistEntity artist) {
        executorService.execute(() -> artistDao.insert(artist));
    }

    public void update(ArtistEntity artist) {
        executorService.execute(() -> artistDao.update(artist));
    }

    public void save(ArtistEntity artist) {
        executorService.execute(() -> artistDao.save(artist));
    }

    public void delete(ArtistEntity artist) {
        executorService.execute(() -> artistDao.delete(artist));
    }
}
