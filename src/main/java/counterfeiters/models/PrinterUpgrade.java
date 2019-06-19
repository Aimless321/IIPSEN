package counterfeiters.models;

import com.google.cloud.firestore.annotation.Exclude;

public class PrinterUpgrade extends Card {
    public enum UpgradeType {PAPER, PAINT, HOLOGRAM}
    private UpgradeType type;

    public PrinterUpgrade(UpgradeType type) {
        super("upgrade", "");

        if (type == UpgradeType.PAPER) {
            super.setImg("/cards/paper.png");
        }
        else if (type == UpgradeType.PAINT) {
            super.setImg("/cards/paint.png");
        }
        else {
            super.setImg("/cards/hologram.png");
        }

        this.type = type;
    }

    public PrinterUpgrade() {}

    @Exclude
    public UpgradeType getType() {
        return type;
    }
}
