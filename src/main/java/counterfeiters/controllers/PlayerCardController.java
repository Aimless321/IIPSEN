package counterfeiters.controllers;

import counterfeiters.models.Card;
import counterfeiters.views.Observer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

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

    public FlowPane makeCardView(FlowPane playerCardView) {
        System.out.println("hier goed 1");
        ArrayList<Card> cardRow = app.gameController.game.getPlayers().get(player).getCards();
        for (int i = 0; i < cardRow.size(); i++) {
            System.out.println("Adding card: " + cardRow.get(i).getName());

            ImageView imageview = new ImageView(cardRow.get(i).getImage());
            imageview.setFitWidth(150);
            imageview.setPreserveRatio(true);
            System.out.println("tot hier ook goed 2");
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

