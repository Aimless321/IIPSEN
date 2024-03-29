package counterfeiters.models;

import counterfeiters.events.EventListener;

import java.util.ArrayList;
import java.util.Collections;

public class BlackMarket implements EventListener {
    private ArrayList<Card> marketList = new ArrayList<>();

    public ArrayList<Card> cardRow = new ArrayList<>();

    public void updateData(BlackMarket updateBlackMarket) {
        cardRow = updateBlackMarket.cardRow;
        marketList = updateBlackMarket.getMarketList();
    }

    /**
     * This method takes the first card from marketList and adds it to cardRow.
     * The card will be removed from marketList.
     * @author: LeanderLoomans
     * @param card
     */
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

        while ((cardRow.size() < 7) && (marketList.size() > 0)) {
            addToRow();
        }
    }

    /**
     * This method gives the correct card to the player.
     * It loops through the marketList until it finds an object that matches cardtype.
     * That card is removed from the marketList and returned.
     * @param cardtype
     * @return
     * @author: LeanderLoomans
     */
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

    //a blank card to ensure that there is an empty space in the market
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
        //Remove first 2 existing cards
        if (cardRow.size()>1) {
            cardRow.remove(0);
            cardRow.remove(0);
        }
        else if (cardRow.size()>0) {
            cardRow.remove(0);
        }


        refill();
    }

    @Override
    public void onRoundStart() {

    }

    @Override
    public void onGameEnd() {

    }
}
