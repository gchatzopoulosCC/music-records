package gr.york.mobiledev2026.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import gr.york.mobiledev2026.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private MyViewModel viewModel;

    public SecondFragment() {
        // Required empty public constructor
    }

    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString("title", param1);
        args.putString("desc", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);


        String title = getArguments().getString("title", "empty");

        binding.secondTxtTitle.setText(title);

        binding.secondBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.decrementCounter();
                //binding.secondTxtTitle.setText(title + " " + viewModel.getCounter());
            }
        });

        binding.secondBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.incrementCounter();
//                binding.secondTxtTitle.setText(title + " " + viewModel.getCounter());
            }
        });
    }
}
