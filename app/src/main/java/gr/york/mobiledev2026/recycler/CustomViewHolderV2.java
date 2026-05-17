package gr.york.mobiledev2026.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import gr.york.mobiledev2026.databinding.HolderItemBinding;
import gr.york.mobiledev2026.databinding.HolderItemV2Binding;

public class CustomViewHolderV2 extends RecyclerView.ViewHolder {

    private HolderItemV2Binding binding;

    public CustomViewHolderV2(@NonNull HolderItemV2Binding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(String data) {
        binding.holderTxtTitle.setText(data);
    }
}
