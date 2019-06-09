package counterfeiters.models;

import com.google.cloud.firestore.annotation.Exclude;

import java.util.HashMap;

public class PolicePawn{

    private int pawnPosition;

    HashMap<Integer, Integer> xcoordinates= new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> ycoordinates= new HashMap<Integer, Integer>();

    public PolicePawn() {

    }

    public PolicePawn(int players) {
        if (players == 3) {
            this.pawnPosition = 4;
        }
        else {
            this.pawnPosition = 1;
        }
        fillXCoordinates();
        fillYCoordinates();
    }

    private void fillXCoordinates() {
        int[] values = {918, 918, 875, 900, 917, 876, 833, 794, 807, 853, 907, 962, 1018, 1074, 1129, 1186, 1241, 1297, 1353};
        for (int i = 0; i < 19; i++) {
            xcoordinates.put(i + 1, values[i]);
        }
    }

    private void fillYCoordinates() {
        int[] values = {361, 415, 450, 498, 549, 586, 622, 661, 714, 746, 756, 762, 769, 772, 782, 787, 794, 800, 804};
        for (int i = 0; i < 19; i++) {
            ycoordinates.put(i + 1, values[i]);
        }
    }

    @Exclude
    public int getXCoordinate() {
        return xcoordinates.get(pawnPosition);
    }

    @Exclude
    public int getYCoordinate() {
        return ycoordinates.get(pawnPosition);
    }

    public void advance() {
        pawnPosition++;
    }

    public int getPawnPosition() {
        return pawnPosition;
    }

    public void setPawnPosition(int pawnPosition) {
        this.pawnPosition = pawnPosition;
    }
}
