package demo.adfhtml.view;

public class IndexEntry {
    public IndexEntry() {
        super();
    }
    
    private String name;
    private String description;
    private String navigation;
    private String image;

    public void setName(String name) {
        this.name = name;
    }

    public IndexEntry(String name, String description, String navigation, String image) {
        super();
        this.name = name;
        this.description = description;
        this.navigation = navigation;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setNavigation(String navigation) {
        this.navigation = navigation;
    }

    public String getNavigation() {
        return navigation;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

}
