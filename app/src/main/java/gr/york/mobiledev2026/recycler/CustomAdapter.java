package gr.york.mobiledev2026.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.york.mobiledev2026.R;
import gr.york.mobiledev2026.databinding.HolderItemBinding;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private List<String> dataList;

    public CustomAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.holder_item, parent, false);
        HolderItemBinding binding = HolderItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CustomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
