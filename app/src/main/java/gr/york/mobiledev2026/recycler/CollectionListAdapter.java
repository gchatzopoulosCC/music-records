package gr.york.mobiledev2026.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.york.mobiledev2026.R;
import gr.york.mobiledev2026.database.artist.ArtistEntity;
import gr.york.mobiledev2026.database.collection.CollectionItem;
import gr.york.mobiledev2026.ui.artist.ArtistViewModel;



public class CollectionListAdapter extends RecyclerView.Adapter<CollectionListAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ArtistEntity item);
    }
    private List<ArtistEntity> dataList;
    private ArtistViewModel viewModel;

    private static OnItemClickListener listener;
    public CollectionListAdapter(List<ArtistEntity> dataList, OnItemClickListener l) {
        this.dataList = dataList;
        this.listener = l;
    }

    // Called when RecyclerView needs a new ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_item, parent, false);
        return new MyViewHolder(view);
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

    public void updateData(List<ArtistEntity> newData) {
        this.dataList = newData;
        notifyDataSetChanged();
    }
    // ViewHolder class (usually inner class)
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        ImageButton button;
        MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);
            image = itemView.findViewById(R.id.imageView);
            button = itemView.findViewById(R.id.removeButton);
        }
        void bind(ArtistEntity item) {
            title.setText(item.getName());
//            TODO: image for artists
//            image.setImageBitmap(item.getImagePath());
            button.setOnClickListener(v -> {
                List<ArtistEntity> newDataList = dataList;
                listener.onItemClick(item);
                newDataList.remove(item);
                updateData(newDataList);
            });
        }
    }
}
