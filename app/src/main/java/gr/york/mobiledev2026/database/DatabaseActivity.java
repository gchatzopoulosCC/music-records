package gr.york.mobiledev2026.database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import gr.york.mobiledev2026.databinding.ActivityDatabaseBinding;

public class DatabaseActivity extends AppCompatActivity {

    private ActivityDatabaseBinding binding;
    private MyDatabase database;

    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDatabaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.databaseTxtTitle.setText(getIntent().getExtras().getString("name"));
        binding.databaseTxtNumber.setText(String.valueOf(getIntent().getExtras().getInt("number")));

        binding.databaseBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra("result", "Data from DatabaseActivity");

                setResult(3000, intent);
                finish();
            }
        });

        database = Room.databaseBuilder(
                getApplicationContext(),
                MyDatabase.class,
                "my_database"
        ).build();

        executor.execute(new Runnable() {

            @Override
            public void run() {
                UserEntity user = new UserEntity();
                user.setName("John");
//                try {
//                    database.userDao().insert(user);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }

                database.userDao().save(user);

                UserEntity[] dataList = database.userDao().readAll();

                Log.d("DatabaseActivity", "Data from database: " + dataList.length);

            }
        });

    }

    @Override
    protected void onStop() {
        database.close();
        database = null;
        super.onStop();
    }
}