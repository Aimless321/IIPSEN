package counterfeiters.models;

public class PrinterUpgrade extends Card {
    public enum UpgradeType {PAPER, PAINT, HOLOGRAM}

    public PrinterUpgrade(UpgradeType type) {
        super("upgrade", "");

        if (type == UpgradeType.PAPER) {
            super.setImg("cards/paper.png");
        }
        else if (type == UpgradeType.PAINT) {
            super.setImg("cards/paint.png");
        }
        else {
            super.setImg("cards/hologram.png");
        }
    }
}
