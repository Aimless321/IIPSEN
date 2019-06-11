package counterfeiters.models;

import com.google.cloud.firestore.annotation.Exclude;

import java.util.ArrayList;
import java.util.Collections;

public class BlackMarket {
    private ArrayList<Card> marketList = new ArrayList<>();

    public ArrayList<Card> cardRow = new ArrayList<>();

    public BlackMarket() {}

    public void addToMarket(Card card) {
        marketList.add(card);
    }

    /**
     * This method shuffles the BlackMarket arraylist.
     * @author: LeanderLoomans
     */
    public void shuffleMarket() {
        Collections.shuffle(marketList);
    }


    /**
     * This method takes the first 7 cards from marketList and places them in cardRow.
     * The cards are removed from marketList.
     * @autor: LeanderLoomans
     */
    public void prepareView() {
        for (int i = 0; i < 7; i++) {
            cardRow.add(marketList.get(0));
            marketList.remove(0);
        }
    }

    public Card getCard(int position) {
        return cardRow.get(position);
    }
}
