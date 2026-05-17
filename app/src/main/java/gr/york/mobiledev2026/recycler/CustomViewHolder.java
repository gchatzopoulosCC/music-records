package gr.york.mobiledev2026.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import gr.york.mobiledev2026.databinding.HolderItemBinding;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    private HolderItemBinding binding;

    public CustomViewHolder(@NonNull HolderItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(String data) {
        binding.holderTxtTitle.setText(data);
    }
}
