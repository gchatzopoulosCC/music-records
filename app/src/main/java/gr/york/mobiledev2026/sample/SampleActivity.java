package gr.york.mobiledev2026.sample;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import gr.york.mobiledev2026.databinding.ActivitySampleBinding;

public class SampleActivity extends AppCompatActivity {

    private ActivitySampleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySampleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.d("SampleActivity", "onCreate called");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("SampleActivity", "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("SampleActivity", "onResume called");
    }

    @Override
    protected void onPause() {
        Log.d("SampleActivity", "onPause called");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("SampleActivity", "onStop called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("SampleActivity", "onDestroy called");
        super.onDestroy();
    }
}