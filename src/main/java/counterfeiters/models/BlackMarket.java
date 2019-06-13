package counterfeiters.models;

import com.google.cloud.firestore.annotation.Exclude;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;

public class BlackMarket {
    private ArrayList<Card> marketList;

    public ArrayList<Card> cardRow = new ArrayList<>();

    public BlackMarket() {
        this.marketList = new ArrayList<>();
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
        for (int i = 0; i < 7; i++) {
            if ((cardRow.size() > 0) && cardRow.get(i).equals(BlancCard.class)) {
                cardRow.remove(i);
            }
        }
        while (cardRow.size() < 7) {
            addToRow();
        }
    }

    public Card givePlayerCard(Card cardtype) {
        for (Card n : marketList) {
            if (n.getClass() == cardtype.getClass()) {
                Card card = n;
                marketList.remove(n);
                return card;
            }
        }
        return null;
    }


    public Card getCard(int position) {
        return cardRow.get(position);
    }

    public void removeCard(int position) {
        cardRow.remove(position);
    }

    public void makeCardPurchased(int position) {
        cardRow.set(position, new BlancCard());
    }
}
