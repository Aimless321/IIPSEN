package counterfeiters.models;

import counterfeiters.events.EventListener;

import java.util.ArrayList;
import java.util.Collections;

public class BlackMarket implements EventListener {
    private ArrayList<Card> marketList = new ArrayList<>();;

    public ArrayList<Card> cardRow = new ArrayList<>();

    public void updateData(BlackMarket updateBlackMarket) {
        cardRow = updateBlackMarket.cardRow;
        marketList = updateBlackMarket.getMarketList();
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
        //Remove all blanccards
        cardRow.removeIf(card -> (card.getName().equals("")));

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

    public ArrayList<Card> getMarketList() {
        return marketList;
    }

    public void setMarketList(ArrayList<Card> marketList) {
        this.marketList = marketList;
    }

    @Override
    public void onRoundEnd() {
        //Remove first 2 cards
        cardRow.remove(0);
        cardRow.remove(0);

        refill();
    }

    @Override
    public void onRoundStart() {

    }

    @Override
    public void onGameEnd() {

    }
}
