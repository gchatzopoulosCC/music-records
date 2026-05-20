package gr.york.mobiledev2026.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.york.mobiledev2026.R;
import gr.york.mobiledev2026.data.model.Track;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.TrackViewHolder> {

    private final List<Track> tracks;

    public TrackListAdapter(List<Track> tracks) {
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
        Track track = tracks.get(position);
        holder.bind(track);
    }

    @Override
    public int getItemCount() {
        return tracks != null ? tracks.size() : 0;
    }

    public static class TrackViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView listeners;
        TextView duration;

        TrackViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.trackItem);
            listeners = itemView.findViewById(R.id.listeners);
            duration = itemView.findViewById(R.id.duration);
        }

        void bind(Track item) {
            name.setText(item.getName());
            listeners.setText(itemView.getContext().getString(R.string.listeners_format, item.getStats().getListeners()));
            int secondsDuration = item.getDuration() / 1000;
            int minutes = secondsDuration / 60;
            int seconds = secondsDuration % 60;
            duration.setText(itemView.getContext().getString(R.string.duration_format, minutes, seconds));
        }
    }
}
