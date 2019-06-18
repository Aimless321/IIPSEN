package counterfeiters.models;

public class Henchman {
    private String owner;
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
