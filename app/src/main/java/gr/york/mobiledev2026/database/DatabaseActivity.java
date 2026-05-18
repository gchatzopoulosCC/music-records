package gr.york.mobiledev2026.database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room3.Room;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import gr.york.mobiledev2026.databinding.ActivityDatabaseBinding;

public class DatabaseActivity extends AppCompatActivity {

    private ActivityDatabaseBinding binding;
    private Db database;

    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDatabaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.databaseTxtTitle.setText(Objects.requireNonNull(getIntent().getExtras()).getString("name"));
        binding.databaseTxtNumber.setText(String.valueOf(getIntent().getExtras().getInt("number")));

        binding.databaseBtnClose.setOnClickListener(view -> {

            Intent intent = new Intent();
            intent.putExtra("result", "Data from DatabaseActivity");

            setResult(3000, intent);
            finish();
        });

        database = Room.databaseBuilder(
                getApplicationContext(),
                Db.class,
                "db"
        ).build();

//        executor.execute(() -> {
//                UserEntity user = new UserEntity();
//                user.setName("John");
//                try {
//                    database.userDao().insert(user);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }

//                database.userDao().save(user);

//                UserEntity[] dataList = database.userDao().readAll();

//                Log.d("DatabaseActivity", "Data from database: " + dataList.length);
//
//        });

    }

    @Override
    protected void onStop() {
        database.close();
        database = null;
        super.onStop();
    }
}