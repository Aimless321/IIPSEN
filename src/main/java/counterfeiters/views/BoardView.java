package counterfeiters.views;

import counterfeiters.controllers.BoardController;
import counterfeiters.controllers.PopUpLaunderMoneyController;
import counterfeiters.managers.SoundManager;
import counterfeiters.events.EventHandler;
import counterfeiters.models.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BoardView implements Observer {

    public HBox blackMarketView;
    public ImageView policePawn;
    public Pane pane;
    public ImageView muteButton;
    public Text qualityOneMoney;
    public Text qualityTwoMoney;
    public Text qualityThreeMoney;
    public Text totalRealMoney;
    public Text totalBankMoney;
    public Text crocplayer;
    public Text deerplayer;
    public Text herronplayer;
    public Text hippoplayer;
    public Pane hippopane;

    private Stage stage;
    private BoardController boardcontroller;

    //Need an empty constructor for FXML
    public BoardView() {

    }

    public BoardView(Stage primaryStage, Object BoardController) {
        this.stage = primaryStage;
        this.boardcontroller = (BoardController) BoardController;
        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/game.fxml", stage, boardcontroller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/game.png"));

        pane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.M) {
                SoundManager.toggleMute();
            } else if (event.getCode() == KeyCode.F11) {
                stage.setFullScreen(!stage.isFullScreen());
            }
        });


        //Show it on the screen
        stage.getScene().setRoot(pane);
    }

    @FXML
    public void blackMarket(MouseEvent mouseEvent) {

        if(!boardcontroller.board.checkYourTurn()) {
            return;
        }

        Button btn = (Button) mouseEvent.getSource();

        if(boardcontroller.checkActionField(btn.getId())) {
            if(!boardcontroller.makePurchase(btn.getStyleClass().get(1))) {
                return;
            }

            placeHenchman(btn);
        }
    }

    @FXML
    public void actionFieldLaunder(MouseEvent mouseEvent) {
        if(!boardcontroller.board.checkYourTurn()) {
            return;
        }

        Button btn = (Button) mouseEvent.getSource();

        // Based on the given type the right method will be called.
        boardcontroller.app.popUpLaunderMoneyController.launderMoney(PopUpLaunderMoneyController.LaunderType.SUPERMARKET, btn);
    }

    @FXML
    public void actionFieldHealer(MouseEvent mouseEvent) {
        if(!boardcontroller.board.checkYourTurn()) {
            return;
        }

        Button btn = (Button) mouseEvent.getSource();

        boardcontroller.app.popUpLaunderMoneyController.launderMoney(PopUpLaunderMoneyController.LaunderType.HEALER, btn);
    }

    @FXML
    public void actionFieldFraud(MouseEvent mouseEvent) {

        if(!boardcontroller.board.checkYourTurn()) {
            return;
        }

        Button btn = (Button) mouseEvent.getSource();

        boardcontroller.updateMoneyOnPosition(Integer.parseInt(btn.getId()));

        if (btn.getStyleClass().contains("glasses")) {
            boardcontroller.makeFirstPlayer();
        }

        placeHenchman(btn);
    }

    @FXML
    public void actionFieldFly(MouseEvent mouseEvent) {

        if(!boardcontroller.board.checkYourTurn()) {
            return;
        }

        if(boardcontroller.board.policePawn.planeCheck()) {
            return;
        }

        Button btn = (Button) mouseEvent.getSource();

        if(btn.getStyleClass().contains("ticket")) {
            if(!boardcontroller.checkCard(new PlaneTicket())) {
                return;
            }

            boardcontroller.openBahamas(btn);
        } else if (boardcontroller.checkActionField(btn.getId())) {
            boardcontroller.openBahamas(btn);
        }
    }

    @FXML
    public void actionFieldPrint(MouseEvent mouseEvent) {

        if(!boardcontroller.board.checkYourTurn()) {
            return;
        }

        Button btn = (Button) mouseEvent.getSource();

        boardcontroller.printMoney();

        if(btn.getStyleClass().contains("police")) {
            boardcontroller.advancePolice();
        }

        placeHenchman(btn);
    }


    @FXML
    public void pressRules(MouseEvent mouseEvent) {
        boardcontroller.rulesPressed();
    }

    @FXML
    public void pressMute(MouseEvent mouseEvent) {
        SoundManager.toggleMute();

        if (SoundManager.muteSound) {
            muteButton.setOpacity(1);
        }
        else {
            muteButton.setOpacity(0.5);
        }
    }

    @FXML
    public void pressCards(MouseEvent mouseEvent) {
        ImageView img = (ImageView) mouseEvent.getSource();
        int player = Integer.parseInt(img.getStyleClass().get(1));

        if (!boardcontroller.app.playerCardController.checkPlayer(player)) {
            return;
        }

        boardcontroller.app.playerCardController.setPlayerID(player);
        boardcontroller.app.loadView(PlayerCardView.class, boardcontroller.app.playerCardController);
    }

    public void placeHenchman(Button btn) {
        boardcontroller.henchmanPlaced(btn);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {
        this.boardcontroller = (BoardController) controller;
    }

    @Override
    public void update(Observable observable) {

        Board board = (Board) observable;

        updateBlackMarket(board);
        updatePolicePawn(board);

        resetHenchman();
        addHenchman(board);

        updateMoney(board);

        setCurrentPlayerShadow(board);
        setSunglasses(board);
    }

    public void updateBlackMarket(Board board) {
        ArrayList<Card> cardRow = board.blackmarket.cardRow;

        for (int i = 0; i < cardRow.size(); i++) {
            ((ImageView) blackMarketView.getChildren().get(i)).setImage(board.blackmarket.getCard(i).getImage());
        }

        for (int j = blackMarketView.getChildren().size()-1; j >= cardRow.size(); j--) {
            System.out.println("Removing card " + j);
            blackMarketView.getChildren().remove(j);
        }

    }

    public void updatePolicePawn(Board board) {
        policePawn.setX(board.policePawn.getXCoordinate());
        policePawn.setY(board.policePawn.getYCoordinate());

        Bounds bounds = policePawn.screenToLocal(policePawn.getLayoutBounds());


        try {
            policePawn.setX(bounds.getMinX());

            //Pawn is too low, so set it a big higher
            policePawn.setY(bounds.getMinY());
        } catch (NullPointerException e) {}

        if (board.policePawn.endCheck()) {
            board.game.localPlayer.updateMoneyReduce(MoneyType.REAL,board.game.localPlayer.getRealMoney().getTotalMoney()/2);
            EventHandler.getInstance().endRound();
            EventHandler.getInstance().endGame();
            boardcontroller.endGame();
        }
    }

    /**
     * Removes all of the henchmen from the board and enbaled all of the buttons again.
     */
    public void resetHenchman() {
        Set<Node> nodes = pane.lookupAll(".henchman");

        for(Node node : nodes) {
            pane.getChildren().remove(node);
        }

        String[] henchmanBoxes = {"#henchman-croc", "#henchman-deer", "#henchman-herron", "#henchman-hippo"};
        for (int i = 0; i < henchmanBoxes.length; i++) {
            VBox box = (VBox) pane.lookup(henchmanBoxes[i]);

            ObservableList<Node> children = box.getChildren();
            while(children.size() < 4) {
                ImageView oldImage = (ImageView)children.get(0);
                ImageView newHenchman = new ImageView(oldImage.getImage());
                newHenchman.setFitWidth(36);
                newHenchman.setFitHeight(36);
                children.add(0, newHenchman);
            }
        }

        //Enable all of the action field buttons again (21 buttons)
        for(int i = 0; i <= 21; i++) {
            Button btn = (Button) pane.lookup(".actionfield-" + i);

            if(btn != null) {
                btn.setDisable(false);
            }
        }
    }

    /**
     * Adds all of the henchmen that have been placed to the board, and disables the buttons.
     * @param board
     */
    public void addHenchman(Board board) {
        for (Henchman henchman : board.getHenchmen()) {
            VBox henchmanbox = (VBox) pane.lookup("#henchman-" + henchman.getOwner());

            ImageView old = (ImageView) henchmanbox.getChildren().remove(0);

            ImageView henchmanImage = new ImageView(old.getImage());
            henchmanImage.setLayoutX(henchman.x);
            henchmanImage.setLayoutY(henchman.y);
            henchmanImage.setFitHeight(36);
            henchmanImage.setFitWidth(36);
            henchmanImage.getStyleClass().add("henchman");

            pane.getChildren().add(henchmanImage);

            //Disable the button that it's placed on
            Button btn = (Button) pane.lookup("." + henchman.getBtnId());

            if(btn != null) {
                btn.setDisable(true);
            }
        }
    }

    public void playersAndCards() {
        //FOR LOOP
        HashMap<String, String> map = boardcontroller.board.getPlayersAndCards();
        for (Map.Entry playerValues : map.entrySet()) {
            System.out.println("Key: " + playerValues.getKey() + " & Value: " + playerValues.getValue());
            switch (playerValues.getKey().toString()) {
                case "1":
                    crocplayer.setText(playerValues.getValue().toString());
                case "2":
                    deerplayer.setText(playerValues.getValue().toString());
                case "3":
                    herronplayer.setText(playerValues.getValue().toString());
                case "4":
                    hippoplayer.setText(playerValues.getValue().toString());
            }
        }
        //If there are only 3 players hide the fourth player.
        if (map.size() ==3){
            hippopane.setVisible(false);
        }
    }

    /**
     * This method sets the money textfields in the boardview
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 11-06-2019
     * */
    public void updateMoney(Board board) {
        this.qualityOneMoney.setText(String.valueOf(board.game.localPlayer.getFakeMoney().getQualityOne()));
        this.qualityTwoMoney.setText(String.valueOf(board.game.localPlayer.getFakeMoney().getQualityTwo()));
        this.qualityThreeMoney.setText(String.valueOf(board.game.localPlayer.getFakeMoney().getQualityThree()));
        this.totalRealMoney.setText(String.valueOf(board.game.localPlayer.getRealMoney().getTotalMoney()));
        this.totalBankMoney.setText(String.valueOf(board.game.localPlayer.getBahamasBank().getTotalBankMoney()));
    }

    /**
     * Adds a shadow to the players who's turn it is.
     * @param board the board model
     */
    public void setCurrentPlayerShadow(Board board) {
        String[] profilePictures = {"#profile-croc", "#profile-deer", "#profile-herron", "#profile-hippo"};
        for(int i = 0; i < profilePictures.length; i++) {
            ImageView profilePicture = (ImageView) pane.lookup(profilePictures[i]);

            //Remove effect
            profilePicture.setEffect(null);
        }

        DropShadow shadow = new DropShadow(25.0, Color.web("#00adee"));
        shadow.setSpread(0.5);

        Player currentPlayer = board.getCurrentPlayer();
        ImageView profilePicture = (ImageView) pane.lookup("#profile-" + currentPlayer.getCharacterName());
        profilePicture.setEffect(shadow);
    }

    /**
     * Adds glasses to the player's profile picture who starts a round.
     * Resets the other images first, so we dont get doubles.
     * @param board the board model
     */
    public void setSunglasses(Board board) {
        for (Player player : board.game.getPlayers()) {
            ImageView profilePicture = (ImageView) pane.lookup("#profile-" + player.getCharacterName());
            profilePicture.setImage(player.getCharacterImagePath());
        }

        Player firstPlayer = board.firstPlayerPawn.getFirstPlayer();
        ImageView profilePicture = (ImageView) pane.lookup("#profile-" + firstPlayer.getCharacterName());
        profilePicture.setImage(firstPlayer.getCharacterGlassesImagePath());
    }

    @Override
    public void start() {
        for (int i = 0; i < 7; i++) {
            ImageView imageview = new ImageView();
            imageview.setFitWidth(111);
            imageview.setPreserveRatio(true);
            blackMarketView.getChildren().add(imageview);
        }

        boardcontroller.registerObserver(this);
        boardcontroller.prepareView();
        playersAndCards();

        boardcontroller.registerListeners();

    }
}
