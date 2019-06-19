package counterfeiters.models;

public class Lawyer extends Card{
    private String quality;
    private static boolean ingame = false;

    public Lawyer() {
        super("lawyer", "/cards/good-lawyer.png");
        if (!ingame) {
            quality = "good";
            ingame = true;
        }
        else {
            quality = "bad";
            super.setImg("/cards/bad-lawyer.png");
        }
    }

    public String getQuality() {
        return quality;
    }
}
