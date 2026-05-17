package gr.york.mobiledev2026.recycler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import gr.york.mobiledev2026.databinding.ActivityRecyclerBinding;

public class RecyclerActivity extends AppCompatActivity {

    private ActivityRecyclerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecyclerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.recyclerView.setAdapter(new CustomAdapter(generateData()));
        binding.recyclerView.setAdapter(new CustomAdapterV2(generateData()));
    }

    private List<String> generateData() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dataList.add("Item " + (i + 1));
        }
        return dataList;
    }
}