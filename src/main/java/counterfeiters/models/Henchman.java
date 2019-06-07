package counterfeiters.models;

import com.google.cloud.firestore.annotation.Exclude;
import javafx.scene.image.Image;

public class Henchman {
    private String owner;
    public double x;
    public double y;

    public Henchman() {

    }

    public Henchman(double x, double y, String owner) {
        this.x = x;
        this.y = y;
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
