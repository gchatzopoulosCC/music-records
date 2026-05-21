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

public class SimilarArtistsListAdapter extends RecyclerView.Adapter<SimilarArtistsListAdapter.SimilarViewHolder> {
    private List<Artist> artists;
    private final OnItemClickListener<Artist> listener;

    public SimilarArtistsListAdapter(List<Artist> artists, OnItemClickListener<Artist> listener) {
        this.artists = artists;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SimilarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.bounded_artist_card, parent, false);
        return new SimilarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarViewHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.bind(artist);
    }

    @Override
    public int getItemCount() {
        return artists != null ? artists.size() : 0;
    }

    public void updateData(List<Artist> newData) {
        this.artists = newData;
        notifyDataSetChanged();
    }

    public class SimilarViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        SimilarViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.track_text);
            image = itemView.findViewById(R.id.track_img);
        }

        void bind(Artist item) {
            name.setText(item.getName());
            Glide.with(itemView.getContext())
                .load(item.getImageUrl())
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
