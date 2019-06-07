package counterfeiters.models;

import counterfeiters.views.Observer;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Collections;

public class BlackMarket {
    private ArrayList<Card> marketList;
    public Card[] cardRow = new Card[7];

    public BlackMarket() {
        this.marketList = new ArrayList<Card>();
    }

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
            cardRow[i] = marketList.get(0);
            marketList.remove(0);
        }
    }

    public Card getCard(int position) {
        return cardRow[position];
    }
}
