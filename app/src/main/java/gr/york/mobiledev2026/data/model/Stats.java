package gr.york.mobiledev2026.data.model;

public class Stats {
    private int listeners;
    private int playcount;

    public Stats(int listeners, int playcount) {
        this.listeners = listeners;
        this.playcount = playcount;
    }

    public int getListeners() {
        return listeners;
    }

    public void setListeners(int listeners) {
        this.listeners = listeners;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }
}
