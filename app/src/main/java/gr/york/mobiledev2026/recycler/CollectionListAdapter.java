package gr.york.mobiledev2026.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import gr.york.mobiledev2026.R;
import gr.york.mobiledev2026.data.local.CollectionEntity;
import gr.york.mobiledev2026.ui.collection.CollectionViewModel;


public class CollectionListAdapter extends RecyclerView.Adapter<CollectionListAdapter.MyViewHolder> {
    private List<CollectionEntity> dataList;
    private CollectionViewModel viewModel;
    private final OnItemClickListener<CollectionEntity> listener;
    public CollectionListAdapter(List<CollectionEntity> dataList, OnItemClickListener<CollectionEntity> listener) {
        this.dataList = dataList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CollectionEntity item = dataList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void updateData(List<CollectionEntity> newData) {
        this.dataList = newData;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        ImageButton removeButton;
        MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);
            image = itemView.findViewById(R.id.imageView);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
        void bind(CollectionEntity item) {
            title.setText(item.getName());
            Glide.with(itemView.getContext())
                .load(item.getImagePath())
                .placeholder(R.mipmap.ic_launcher)
                .into(image);

            removeButton.setOnClickListener(v -> {
                listener.onItemClick(item);
            });
        }
    }
}
