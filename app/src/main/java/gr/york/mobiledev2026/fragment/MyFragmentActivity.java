package gr.york.mobiledev2026.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import gr.york.mobiledev2026.databinding.ActivityMyFragmentBinding;

public class MyFragmentActivity extends AppCompatActivity {

    private ActivityMyFragmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.add(BlankFragment.newInstance(), "my_fragment");
//        transaction.add(binding.fragmentContainer.getId(),BlankFragment.newInstance(), "my_fragment");
        transaction.replace(binding.fragmentContainerUp.getId(), BlankFragment.newInstance(), "my_fragment");
        transaction.replace(binding.fragmentContainerDown.getId(), SecondFragment.newInstance("Activity",""), "SecondFragment");
//        transaction.remove(BlankFragment.newInstance());
        transaction.commit();
    }
}