package gr.york.mobiledev2026.database.collection;

public class CollectionItem {
    private String name;
    private String image; // or URL

    public CollectionItem(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() { return name; }
    public String getImage() { return image;}
}
