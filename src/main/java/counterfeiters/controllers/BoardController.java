package counterfeiters.controllers;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.ListenerRegistration;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.*;
import counterfeiters.views.Observer;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardController {
    public ApplicationController app;
    public Board board = new Board();
    private ListenerRegistration listener;
    private boolean lobbyDeleted = false;

    public BoardController(ApplicationController applicationController) {
        this.app = applicationController;

        fillMarket(3);
        board.blackmarket.shuffleMarket();
    }

    public Board createFromSaved(String gameid) {
        FirebaseService fb = FirebaseService.getInstance();
        DocumentSnapshot documentSnapshot = fb.get("games", gameid);

        Board savedBoard = documentSnapshot.toObject(Board.class);
        this.board = savedBoard;

        return savedBoard;
    }

    public void registerObserver(Observer observer) {
        board.registerObserver(observer);
    }

    public void registerListeners() {
        FirebaseService fb = FirebaseService.getInstance();

        //Listen for changes in the lobby
        listener = fb.listen("games", board.game.getGameId(),
                (documentSnapshot, e) -> {
                    if (e != null) {
                        System.err.println("Listen failed: " + e);
                        return;
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        System.out.println("Updating board");

                        Board updateBoard = documentSnapshot.toObject(Board.class);
                        board.updateData(updateBoard);
                    }
                });
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

    /**
     * By giving an Id, right character and amount. You can add or reduce money and the money will be updated in the indicated part.
     * Calling the correct type of money is based on the given id.
     * Adding or reducing money depends on de given character.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 09-06-2019
     * */
    public void updateMoneyOnPosition(int qId, String character, int amount){
        Player player = app.gameController.game.localPlayer;
        app.gameController.updateMoney(qId, character, amount);
    }

    /**
     * By giving the right moneyQuality and quantity, the method for the check can be called up.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 11-06-2019
     * */
    public boolean checkActionField(int moneyId, String id){
        int money = Integer.parseInt(id);

        if (board.checkMoney(moneyId, money)) {
            //TODO: Fout
            app.gameController.game.notifyAllObservers();
            return true;
        }
        return false;
    }

    public void henchmanPlaced(Button btn) {
        if(!lobbyDeleted) {
            app.gameController.game.delete();
        }

        Bounds bounds = btn.localToScene(btn.getBoundsInLocal());

        //Calculate middle position of the button
        double posX = bounds.getMinX() + btn.getWidth() / 3;
        double posY = bounds.getMinY() + btn.getHeight() / 5;

        Player player = app.gameController.game.localPlayer;
        board.placeHenchman(posX, posY, player.getCharacterName());

        board.checkEndRound();

        board.updateFirebase();
        board.notifyAllObservers();
    }

    public void prepareView() {
        board.prepareFirstPlayer();
        board.prepareBlackMarket();
        board.setPlayersAndCards();
        //app.gameController.game.notifyAllObservers(); //Voert alle updates uit in de game.
    }

    public void advancePolice() {
        board.advancePolice();
    }

    public void printMoney() {

        int[] print = board.printMoney();
        board.game.localPlayer.updateMoneyPlus(print[0], print[1]);
    }

    public void makePurchase(String cardNumber ) {
        board.makePurchase(Integer.parseInt(cardNumber));
    }

    public boolean checkCard(Card card) {
        return board.game.localPlayer.hasCard(card);
    }

    public void makeFirstPlayer() {
        board.makeFirstPlayer();
    }

    public void transerMoney(int qualityId, int qualityOne, int qualityTwo, int qualityThree) {
        board.transferMoney(qualityId, qualityOne, qualityTwo, qualityThree);
    }

    public Card givePlayerCard(Card card) {
        return board.blackmarket.givePlayerCard(card);
    }
}
