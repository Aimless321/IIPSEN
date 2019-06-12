package counterfeiters.models;

import com.google.cloud.firestore.annotation.Exclude;

import java.util.ArrayList;
import java.util.Collections;

public class BlackMarket {
    private ArrayList<Card> marketList;

    @Exclude
    public ArrayList<Card> cardRow = new ArrayList<>();

    public BlackMarket() {
        this.marketList = new ArrayList<Card>();
    }

    public void addToMarket(Card card) {
        marketList.add(card);
    }

    public void addToRow() {
        cardRow.add(marketList.get(0));
        marketList.remove(0);
    }

    /**
     * This method shuffles the BlackMarket arraylist.
     * @author: LeanderLoomans
     */
    public void shuffleMarket() {
        Collections.shuffle(marketList);
    }


    /**
     * This method takes cards from marketList and places them in cardRow.
     * It continues until cardRow contains 7 cards.
     * The cards are removed from marketList.
     * @autor: LeanderLoomans
     */
    public void refill() {
        while (cardRow.size() < 7) {
            addToRow();
        }
    }


    public Card getCard(int position) {
        return cardRow.get(position);
    }
}
