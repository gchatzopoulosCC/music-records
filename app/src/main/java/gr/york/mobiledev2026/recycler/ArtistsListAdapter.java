package gr.york.mobiledev2026.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.york.mobiledev2026.R;
import gr.york.mobiledev2026.database.ArtistEntity;

public class ArtistsListAdapter extends RecyclerView.Adapter<ArtistsListAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick();
    }
    private List<ArtistEntity> dataList;
    private OnItemClickListener listener;
    public ArtistsListAdapter(List<ArtistEntity> dataList) {
        this.dataList = dataList;
    }
    public ArtistsListAdapter(List<ArtistEntity> dataList, OnItemClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    // Called when RecyclerView needs a new ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_card, parent, false);
        return new MyViewHolder(view, listener);
    }
    // Called to display data at specified position
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ArtistEntity item = dataList.get(position);
        holder.bind(item);
    }
    // Total number of items
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    // ViewHolder class (usually inner class)
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        OnItemClickListener listener;
        MyViewHolder(View itemView, OnItemClickListener l) {
            super(itemView);
            title = itemView.findViewById(R.id.artist_text);
            image = itemView.findViewById(R.id.artist_img);
            this.listener = l;

            if (listener != null) {
                itemView.setOnClickListener(v -> {
                    listener.onItemClick();
                });
            }
        }
        void bind(ArtistEntity item) {
            title.setText(item.getTitle());
            image.setImageBitmap(item.getImage());
        }
    }
}
