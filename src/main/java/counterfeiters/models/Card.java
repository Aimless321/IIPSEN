package counterfeiters.models;


import com.google.cloud.firestore.annotation.Exclude;
import javafx.scene.image.Image;

public class Card {
    private String name;
    private String img;

    public Card() {

    }

    public Card(String name, String img) {
        this.name = name;
        this.img = img;
    }

    @Exclude
    public Image getImage() {
        Image image = new Image(getClass().getResourceAsStream(img));
        return image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
