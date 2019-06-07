package counterfeiters.controllers;

import counterfeiters.models.*;
import counterfeiters.models.Board;
import counterfeiters.views.Observer;

import java.util.HashMap;

public class BoardController {
    public ApplicationController app;
    public Board board = new Board();

    public BoardController(ApplicationController applicationController) {
        this.app = applicationController;

        fillMarket(3);
        board.blackmarket.shuffleMarket();
    }

    public void registerObserver(Observer observer) {
        board.registerObserver(observer);
    }

    /**
     * This function fills the BlackMarket arraylist with cards.
     * Of each type of card, the right amount is added.
     * @author LeanderLoomans
     * @param spelers
     */

    private void fillMarket(int spelers) {
        HashMap<Card, Integer> map = new HashMap();

        map.put(new Printer(), 9);
        map.put(new PrinterUpgrade(PrinterUpgrade.UpgradeType.HOLOGRAM), 3);
        map.put(new PrinterUpgrade(PrinterUpgrade.UpgradeType.PAPER), 4);
        map.put(new PrinterUpgrade(PrinterUpgrade.UpgradeType.PAINT), 4);
        map.put(new Diner(), 4);
        map.put(new PlaneTicket(), 3);
        map.put(new ScratchCard(), 3);
        map.put(new Lawyer("good"), 1);

        if (spelers == 4) {
            for (Card n : map.keySet()) {
                map.put(n, map.get(n) + 1);
            }
        }

        for (Card n : map.keySet()) {
            for (int i = 0; i < map.get(n); i++) {
                board.blackmarket.addToMarket(n);
            }
        }
    }

    public void prepareView() {
        board.prepareBlackMarket();
    }

    public void advancePolice() {
        board.advancePolice();
    }


}
