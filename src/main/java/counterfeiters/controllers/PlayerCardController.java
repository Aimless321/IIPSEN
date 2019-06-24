package counterfeiters.controllers;

import counterfeiters.models.Card;
import counterfeiters.views.Observer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;

public class PlayerCardController {
    private ApplicationController app;

    private int player;

    public PlayerCardController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void registerObserver(Observer observer) {
        app.accountController.registerObserver(observer);
    }

    /**
     * This method adds all cards that the player owns to the Flowpane.
     * For every card, an imageview is created.
     * Next, the image of the card is added to the imageview.
     * The imageview is added to the Flowpane and when all cards are inserted the Flowpane is returned.
     * @author: Leander Loomans
     * @param playerCardView
     * @return
     */
    public FlowPane makeCardView(FlowPane playerCardView) {
        ArrayList<Card> cardRow = app.gameController.game.getPlayers().get(player).getCards();
        for (int i = 0; i < cardRow.size(); i++) {
            ImageView imageview = new ImageView(cardRow.get(i).getImage());
            imageview.setFitWidth(150);
            imageview.setPreserveRatio(true);
            playerCardView.getChildren().add(imageview);
        }
        return playerCardView;
    }

    public void setPlayerID(int player) {
        this.player = player;
    }

    public boolean checkPlayer(int player) {
        return player < app.gameController.game.getPlayers().size();
    }
}

