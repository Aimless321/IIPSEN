package counterfeiters.controllers;

import counterfeiters.models.*;
import counterfeiters.views.Observer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class BlackMarketController {
    private ApplicationController app;
    public BlackMarket blackmarket;

    public BlackMarketController(ApplicationController applicationController) {
        this.app = applicationController;
        blackmarket = new BlackMarket();
        fillMarket(3);
        blackmarket.shuffleMarket();
    }

    public void registerObserver(Observer observer) {
        blackmarket.registerObserver(observer);
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
                blackmarket.addToMarket(n);
            }
        }
    }

    public void prepareView() {
        blackmarket.prepareView();
    }
}
