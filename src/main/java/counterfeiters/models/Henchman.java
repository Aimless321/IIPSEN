package counterfeiters.models;

/**
 * Model that stores all of the data that is needed to show a henchman on the screen.
 */
public class Henchman {
    private String owner;
    /**
     * Btn where the henchman is placed on
     */
    private String btnId;
    public double x;
    public double y;

    public Henchman() {

    }

    public Henchman(double x, double y, String owner, String btnId) {
        this.x = x;
        this.y = y;
        this.owner = owner;
        this.btnId = btnId;
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

    public String getBtnId() {
        return btnId;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }
}
