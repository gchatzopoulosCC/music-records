package gr.york.mobiledev2026.recycler;

import gr.york.mobiledev2026.data.model.Artist;

public interface OnItemClickListener<T> {
    void onItemClick(T artist);
}
