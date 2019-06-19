package counterfeiters.views;

import counterfeiters.controllers.BoardController;
import counterfeiters.controllers.PopUpLaunderMoneyController;
import counterfeiters.models.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BoardView implements Observer {

    public HBox blackMarketView;
    public ImageView policePawn;
    public Pane pane;
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

    private String plus = "+";
    private String min = "-";
    private int qualityOne = 1;
    private int qualityTwo = 2;
    private int qualityThree = 3;
    private int realMoney = 4;
    private int bahamasBank = 5;

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

        //Show it on the screen
        stage.getScene().setRoot(pane);
    }

    @FXML
    public void blackMarket(MouseEvent mouseEvent) {

        if(!boardcontroller.board.checkYourTurn()) {
            return;
        }

        Button btn = (Button) mouseEvent.getSource();


        if(boardcontroller.checkActionField(4, btn.getId())) {
            boardcontroller.makePurchase(btn.getStyleClass().get(1));
            placeHenchman(btn);
        }



    }

    @FXML
    public void actionFieldLaunder(MouseEvent mouseEvent) {

        if(!boardcontroller.board.checkYourTurn()) {
            return;
        }

        boardcontroller.app.popUpLaunderMoneyController.launderMoney(PopUpLaunderMoneyController.LaunderType.SUPERMARKET);

        Button btn = (Button) mouseEvent.getSource();

        boardcontroller.advancePolice();

        placeHenchman(btn);
    }

    @FXML
    public void actionFieldHealer(MouseEvent mouseEvent) {

        if(!boardcontroller.board.checkYourTurn()) {
            return;
        }

        boardcontroller.app.popUpLaunderMoneyController.launderMoney(PopUpLaunderMoneyController.LaunderType.HEALER);

        Button btn = (Button) mouseEvent.getSource();

        if(btn.getStyleClass().contains("police")) {
            boardcontroller.advancePolice();
        }

        placeHenchman(btn);
    }

    @FXML
    public void actionFieldFraud(MouseEvent mouseEvent) {

        if(!boardcontroller.board.checkYourTurn()) {
            return;
        }

        Button btn = (Button) mouseEvent.getSource();

        boardcontroller.updateMoneyOnPosition(realMoney, plus, Integer.parseInt(btn.getId()));

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

        //Checking if the policepawn is on "airplane", if so the player can not fly to the bahamas
        if(boardcontroller.board.policePawn.planeCheck()) {
            return;
        }


        boardcontroller.app.popUpBahamasController.bahamas();

        Button btn = (Button) mouseEvent.getSource();

        if(btn.getStyleClass().contains("ticket") && !boardcontroller.checkCard(new PlaneTicket())) {
            return;
        }
        else if(!btn.getStyleClass().contains("ticket")
                && !boardcontroller.checkActionField(4, btn.getId())) {
            return;
        }

        placeHenchman(btn);
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


        Button btn = (Button) mouseEvent.getSource();
        placeHenchman(btn);
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
        boardcontroller.board.game.nextTurn();

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
    }

    public void updateBlackMarket(Board board) {
        for (int i = 0; i < 7; i++) {
            try {
                ((ImageView) blackMarketView.getChildren().get(i)).setImage(board.blackmarket.getCard(i).getImage());
            }catch (NullPointerException e) {
                System.err.println("Cannot load blackmarket card image");
            }
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

    }

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
        for(int i = 0; i < 21; i++) {
            Button btn = (Button) pane.lookup(".actionfield-" + i);

            if(btn != null) {
                btn.setDisable(false);
            }
        }
    }

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

    public void updateMoney(Board board) {
        this.qualityOneMoney.setText(String.valueOf(board.game.localPlayer.getFakeMoney().getQualityOne()));
        this.qualityTwoMoney.setText(String.valueOf(board.game.localPlayer.getFakeMoney().getQualityTwo()));
        this.qualityThreeMoney.setText(String.valueOf(board.game.localPlayer.getFakeMoney().getQualityThree()));
        this.totalRealMoney.setText(String.valueOf(board.game.localPlayer.getRealMoney().getTotalMoney()));
        this.totalBankMoney.setText(String.valueOf(board.game.localPlayer.getBahamasBank().getTotalBankMoney()));
    }

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
