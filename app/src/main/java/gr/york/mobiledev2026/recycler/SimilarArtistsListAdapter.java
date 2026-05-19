package gr.york.mobiledev2026.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.york.mobiledev2026.R;
import gr.york.mobiledev2026.database.artist.ArtistEntity;

public class SimilarArtistsListAdapter extends RecyclerView.Adapter<SimilarArtistsListAdapter.SimilarViewHolder> {

    private List<ArtistEntity> artists;

    public SimilarArtistsListAdapter(List<ArtistEntity> artists) {
        this.artists = artists;
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
        ArtistEntity artist = artists.get(position);
        holder.nameTextView.setText(artist.getName());

        // TODO: Load image if you have it
        // holder.imageView.setImageBitmap(...)
    }

    @Override
    public int getItemCount() {
        return artists != null ? artists.size() : 0;
    }

    public void updateData(List<ArtistEntity> newData) {
        this.artists = newData;
        notifyDataSetChanged();
    }

    static class SimilarViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        SimilarViewHolder(View itemView) {
            super(itemView);
            // Matches IDs in bounded_artist_card.xml
            nameTextView = itemView.findViewById(R.id.track_text);
            imageView = itemView.findViewById(R.id.track_img);
        }
    }
}
