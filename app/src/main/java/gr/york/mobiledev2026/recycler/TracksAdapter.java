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
import gr.york.mobiledev2026.data.model.Track;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TrackViewHolder> {

    private List<Track> tracks;
    private final OnItemClickListener<Artist> listener;

    public TracksAdapter(List<Track> tracks, OnItemClickListener<Artist> listener) {
        this.tracks = tracks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.track_card, parent, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Track track = tracks.get(position);
        holder.bind(track);
    }

    public void updateData(List<Track> newData) {
        this.tracks = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tracks != null ? tracks.size() : 0;
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder {
        TextView trackName;
        TextView artistName;
        ImageView trackImg;


        TrackViewHolder(View itemView) {
            super(itemView);
            trackName = itemView.findViewById(R.id.track_text);
            artistName = itemView.findViewById(R.id.artist_text);
            trackImg = itemView.findViewById(R.id.track_img);
        }

        void bind(Track item) {
            trackName.setText(item.getName());
            Artist artist = item.getArtist();
            artistName.setText(artist != null ? artist.getName() : "");
            Glide.with(itemView.getContext())
                .load(item.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(trackImg);

            if (listener != null && artist != null) {
                itemView.setOnClickListener(v -> listener.onItemClick(artist));
            }
        }
    }
}
