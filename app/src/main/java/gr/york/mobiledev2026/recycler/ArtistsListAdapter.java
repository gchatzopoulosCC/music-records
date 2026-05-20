package gr.york.mobiledev2026.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import gr.york.mobiledev2026.R;
import gr.york.mobiledev2026.data.model.Artist;

public class ArtistsListAdapter extends RecyclerView.Adapter<ArtistsListAdapter.MyViewHolder> {
    private List<Artist> dataList;
    private OnItemClickListener<Artist> listener;
    public ArtistsListAdapter(List<Artist> dataList) {
        this.dataList = dataList;
    }
    public ArtistsListAdapter(List<Artist> dataList, OnItemClickListener<Artist> listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Artist item = dataList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void updateData(List<Artist> newData) {
        this.dataList = newData;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.artist_text);
            image = itemView.findViewById(R.id.artist_img);
        }
        void bind(Artist item) {
            title.setText(item.getName());
            Glide.with(itemView.getContext())
                .load(item.getImageUrl()) // This works for BOTH URLs and Local Paths
                .placeholder(R.mipmap.ic_launcher)
                .into(image);

            if (listener != null) {
                itemView.setOnClickListener(v -> {
                    listener.onItemClick(item);
                });
            }
        }
    }
}
