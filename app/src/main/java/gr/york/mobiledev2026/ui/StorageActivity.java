package gr.york.mobiledev2026.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import gr.york.mobiledev2026.databinding.ActivityStorageBinding;

public class StorageActivity extends AppCompatActivity {

    private ActivityStorageBinding binding;

    private int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStorageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
//        SharedPreferences packagePreferences = getPreferences( MODE_PRIVATE);
//        preferences.edit().putInt("number", number).commit();
//        preferences.edit().putInt("number", number).apply();

        binding.storageBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number--;
                preferences.edit().putInt("number", number).apply();
                binding.storageTxtTitle.setText(String.valueOf(number));
            }
        });

        binding.storageBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number++;

                preferences.edit().putInt("number", number).apply();
                binding.storageTxtTitle.setText(String.valueOf(number));
            }
        });

        number = preferences.getInt("number", 0);
        binding.storageTxtTitle.setText(String.valueOf(number));
    }
}