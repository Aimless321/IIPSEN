package counterfeiters.models;

public class Lawyer extends Card{
    private String quality;

    public Lawyer(String quality) {
            super("lawyer", quality + "-lawyer.png");
            this.quality = quality;
    }
}
