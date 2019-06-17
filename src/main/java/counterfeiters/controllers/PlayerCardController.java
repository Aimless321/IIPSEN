package counterfeiters.controllers;

import counterfeiters.models.Card;
import counterfeiters.views.Observer;
import javafx.scene.image.ImageView;
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

    public GridPane makeCardView(GridPane playerCardView) {
        ArrayList<Card> cardRow = app.gameController.game.getPlayers().get(player).getCards();
        for (int i = 0; i < cardRow.size(); i++) {
            ImageView imageview = new ImageView();
            imageview.setFitWidth(111);
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

