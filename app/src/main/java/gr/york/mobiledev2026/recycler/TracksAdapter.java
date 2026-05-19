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
import gr.york.mobiledev2026.database.track.Track;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TrackViewHolder> {

    private List<Track> tracks;

    public TracksAdapter(List<Track> tracks) {
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the simpler track list item layout
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.track_card, parent, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Track track = tracks.get(position);
        holder.trackNameTextView.setText(track.getName());
        holder.trackImg.setImageBitmap(track.getImage());
        holder.artistNameView.setText(track.getArtist());
    }

    @Override
    public int getItemCount() {
        return tracks != null ? tracks.size() : 0;
    }

    static class TrackViewHolder extends RecyclerView.ViewHolder {
        TextView trackNameTextView;
        TextView artistNameView;
        ImageView trackImg;


        TrackViewHolder(View itemView) {
            super(itemView);
            // Matches the ID in tracks_list_item.xml
            trackNameTextView = itemView.findViewById(R.id.track_text);
            artistNameView = itemView.findViewById(R.id.artist_text);
            trackImg = itemView.findViewById(R.id.track_img);
        }
    }
}
