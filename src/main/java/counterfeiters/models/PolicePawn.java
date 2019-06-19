package counterfeiters.models;

import com.google.cloud.firestore.annotation.Exclude;

import java.util.HashMap;

public class PolicePawn{

    private int pawnPosition = 1;

    HashMap<Integer, Integer> xcoordinates= new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> ycoordinates= new HashMap<Integer, Integer>();

    public PolicePawn() {
        fillXCoordinates();
        fillYCoordinates();
    }

    public PolicePawn(int players) {
        if (players == 3) {
            this.pawnPosition = 4;
        }

        fillXCoordinates();
        fillYCoordinates();
    }

    public void updateData(PolicePawn updatePolicePawn) {
        this.pawnPosition = updatePolicePawn.pawnPosition;
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

    public int qualityCheck() {
        if (pawnPosition >= 11 && pawnPosition < 13) {
            return 1;
        }
        else if (pawnPosition >= 13 && pawnPosition <17) {
            return 2;
        }
        else if (pawnPosition>=17) {
            return 3;
        }
        else {
            return 0;
        }
    }

    public boolean godfatherCheck() {
        if (pawnPosition == 10 || pawnPosition == 16) {
            return true;
        }
        return false;
    }

    public boolean planeCheck() {
        if (pawnPosition == 15) {
            return true;
        }
        return false;
    }

    public boolean endCheck(){
        if (pawnPosition == 19){
            return true;
        }
        return false;
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
