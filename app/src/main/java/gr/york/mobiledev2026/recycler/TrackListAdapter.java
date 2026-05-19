package gr.york.mobiledev2026.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.york.mobiledev2026.R;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.TrackViewHolder> {

    private List<String> tracks;

    public TrackListAdapter(List<String> tracks) {
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the simpler track list item layout
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.tracks_list_item, parent, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        String trackName = tracks.get(position);
        holder.trackNameTextView.setText(trackName);
    }

    @Override
    public int getItemCount() {
        return tracks != null ? tracks.size() : 0;
    }

    // Call this to refresh the tracks (e.g., when the artist changes)
    public void updateData(List<String> newTracks) {
        this.tracks = newTracks;
        notifyDataSetChanged();
    }

    static class TrackViewHolder extends RecyclerView.ViewHolder {
        TextView trackNameTextView;

        TrackViewHolder(View itemView) {
            super(itemView);
            // Matches the ID in tracks_list_item.xml
            trackNameTextView = itemView.findViewById(R.id.trackItem);
        }
    }
}
