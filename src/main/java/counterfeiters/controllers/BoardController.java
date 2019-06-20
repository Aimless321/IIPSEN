package counterfeiters.controllers;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.ListenerRegistration;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.*;
import counterfeiters.views.Observer;
import counterfeiters.views.ScoreboardView;
import javafx.collections.transformation.FilteredList;
import counterfeiters.views.RulesView;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;

import java.util.HashMap;

public class BoardController {
    public ApplicationController app;
    public Board board = new Board();
    private ListenerRegistration listener;
    private boolean lobbyDeleted = false;

    public BoardController(ApplicationController applicationController) {
        this.app = applicationController;

        fillMarket(4);
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

        map.put(new Printer(), 6);
        map.put(new PrinterUpgrade(PrinterUpgrade.UpgradeType.HOLOGRAM), 3);
        map.put(new PrinterUpgrade(PrinterUpgrade.UpgradeType.PAPER), 4);
        map.put(new PrinterUpgrade(PrinterUpgrade.UpgradeType.PAINT), 4);
        map.put(new Diner(), 4);
        map.put(new PlaneTicket(), 3);
        map.put(new ScratchCard(), 3);

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
    public void updateMoneyOnPosition(int amount){
        app.gameController.game.updateMoney(amount);
    }

    /**
     * By giving the right moneyQuality and quantity, the method for the check can be called up.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 11-06-2019
     * */
    public boolean checkActionField(String id){
        int money = Integer.parseInt(id);

        return board.checkMoney(MoneyType.REAL, money);
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
        board.placeHenchman(posX, posY, player.getCharacterName(), getActionFieldButtonId(btn));

        board.checkEndRound();

        board.updateFirebase();
        board.notifyAllObservers();
    }

    public String getActionFieldButtonId(Button btn) {
        //Only get the classes that start with 'actionfield-'
        FilteredList<String> classList = btn.getStyleClass().filtered(
                styleClass -> (styleClass.matches("actionfield-.*")));

        return classList.get(0);
    }

    public void prepareView() {
        board.prepareFirstPlayer();
        board.prepareBlackMarket();
        board.setPlayersAndCards();
    }

    public void rulesPressed()
    {

        app.loadView(RulesView.class, app.rulesController);

    }

    public void advancePolice() {
        board.advancePolice();
    }

    public void printMoney() {
        board.game.localPlayer.printMoney();
    }

    public void openBahamas() {
        app.popUpBahamasController.bahamas();
    }

    public boolean makePurchase(String cardNumber ) {
        return board.makePurchase(Integer.parseInt(cardNumber));
    }

    public boolean checkCard(Card card) {
        return board.game.localPlayer.hasCard(card);
    }

    public void makeFirstPlayer() {
        board.makeFirstPlayer();
    }

    public void transferMoneySupermarket(MoneyType qualityId, int qualityOne, int qualityTwo, int qualityThree) {
        board.transferMoneySupermarket(qualityId, qualityOne, qualityTwo, qualityThree);
    }

    public void transferMoneyHealer(MoneyType qualityId, int qualityOne, int qualityTwo, int qualityThree) {
        board.transferMoneyHealer(qualityId, qualityOne, qualityTwo, qualityThree);
    }

    public Card givePlayerCard(Card card) {
        return board.blackmarket.givePlayerCard(card);
    }

    public void endGame(){
        app.loadView(ScoreboardView.class, app.scoreboardController);
    }
}
