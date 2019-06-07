package counterfeiters.controllers;

import com.google.cloud.firestore.ListenerRegistration;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.*;
import counterfeiters.models.Board;
import counterfeiters.views.Observer;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;

import java.util.HashMap;

public class BoardController {
    public ApplicationController app;
    public Board board = new Board();
    private ListenerRegistration listener;

    public BoardController(ApplicationController applicationController) {
        this.app = applicationController;

        fillMarket(3);
        board.blackmarket.shuffleMarket();
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

    public void henchmanPlaced(Button btn) {
        Bounds bounds = btn.localToScene(btn.getBoundsInLocal());

        //Calculate middle position of the button
        double posX = bounds.getMinX() + btn.getWidth()/3;
        double posY = bounds.getMinY() + btn.getHeight()/5;

        board.placeHenchman(posX, posY, app.gameController.game.localPlayer.getCharacterName());
    }

    public void prepareView() {
        board.prepareBlackMarket();
    }

    public void advancePolice() {
        board.advancePolice();
    }


}
