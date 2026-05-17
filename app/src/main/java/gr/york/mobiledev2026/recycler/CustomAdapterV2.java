package gr.york.mobiledev2026.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.york.mobiledev2026.databinding.HolderItemBinding;
import gr.york.mobiledev2026.databinding.HolderItemV2Binding;

public class CustomAdapterV2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> dataList;

    public CustomAdapterV2(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            HolderItemV2Binding binding = HolderItemV2Binding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new CustomViewHolderV2(binding);
        } else {
            HolderItemBinding binding = HolderItemBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new CustomViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CustomViewHolder) {
            CustomViewHolder customHolder = (CustomViewHolder) holder;
            customHolder.bind(dataList.get(position));
        } else if (holder instanceof CustomViewHolderV2) {
            CustomViewHolderV2 customHolder = (CustomViewHolderV2) holder;
            customHolder.bind(dataList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return 1;
        } else {
            return super.getItemViewType(position);
        }
    }
}
