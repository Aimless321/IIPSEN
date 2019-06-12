package counterfeiters.models;


import javafx.scene.image.Image;

public abstract class Card {
    private String name;
    private String img;

    public Card(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public Image getImg() {
        Image image = new Image(getClass().getResourceAsStream(img));
        return image;
    }

    public String getName() {
        return name;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
