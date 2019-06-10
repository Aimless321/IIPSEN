package counterfeiters.views;

import counterfeiters.controllers.BoardController;
import counterfeiters.models.Board;
import counterfeiters.models.Henchman;
import counterfeiters.models.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Set;

public class BoardView implements Observer {

    public HBox blackMarketView;
    public ImageView policePawn;
    public Pane pane;

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
        Scene scene = new Scene(root, ViewUtilities.screenWidth, ViewUtilities.screenHeight);
        stage.setScene(scene);
    }

    @FXML
    public void blackMarket(MouseEvent mouseEvent) {
        System.out.println("Black market pressed");
        if(!boardcontroller.board.game.checkYourTurn()) {
            return;
        }

        Button btn = (Button) mouseEvent.getSource();
        placeHenchman(btn);
        boardcontroller.board.game.nextTurn();
    }

    @FXML
    public void actionFieldLaunder(MouseEvent mouseEvent) {
        System.out.println("Launder button pressed");
        if(!boardcontroller.board.game.checkYourTurn()) {
            return;
        }

        Button btn = (Button) mouseEvent.getSource();
        if(btn.getStyleClass().get(0) == "police" ) {
            boardcontroller.advancePolice();
        }

        placeHenchman(btn);
        boardcontroller.board.game.nextTurn();
    }

    @FXML
    public void actionFieldFraud(MouseEvent mouseEvent) {
        System.out.println("Fraud button pressed");
        if(!boardcontroller.board.game.checkYourTurn()) {
            return;
        }

        Button btn = (Button) mouseEvent.getSource();
        if(btn.getStyleClass().get(0) == "police" ) {
            boardcontroller.advancePolice();
        }

        placeHenchman(btn);
        boardcontroller.board.game.nextTurn();
    }

    @FXML
    public void actionFieldFly(MouseEvent mouseEvent) {
        System.out.println("Fly button pressed");
        if(!boardcontroller.board.game.checkYourTurn()) {
            return;
        }

        Button btn = (Button) mouseEvent.getSource();
        placeHenchman(btn);
        boardcontroller.board.game.nextTurn();
    }

    @FXML
    public void actionFieldHealer(MouseEvent mouseEvent) {
        System.out.println("Healer button pressed");
        if(!boardcontroller.board.game.checkYourTurn()) {
            return;
        }

        Button btn = (Button) mouseEvent.getSource();
        placeHenchman(btn);
        boardcontroller.board.game.nextTurn();
    }

    @FXML
    public void actionFieldPrint(MouseEvent mouseEvent) {
        System.out.println("Print button pressed");
        if(!boardcontroller.board.game.checkYourTurn()) {
            return;
        }

        Button btn = (Button) mouseEvent.getSource();
        if(btn.getStyleClass().get(0) == "police" ) {
            boardcontroller.advancePolice();
        }

        placeHenchman(btn);
        boardcontroller.board.game.nextTurn();
    }

    @FXML
    public void pressRules(MouseEvent mouseEvent) {
        System.out.println("Rules button pressed");

        Button btn = (Button) mouseEvent.getSource();
        placeHenchman(btn);
    }

    @FXML
    public void pressCards(MouseEvent mouseEvent) {
        System.out.println("Cards button pressed");

        Button btn = (Button) mouseEvent.getSource();
        placeHenchman(btn);
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
        Board board = (Board)observable;

        for (int i = 0; i < 7; i++) {
            ImageView imageview = new ImageView(board.blackmarket.getCard(i).getImg());
            imageview.setFitWidth(111);
            imageview.setPreserveRatio(true);
            blackMarketView.getChildren().add(imageview);
        }

        policePawn.setX(board.policePawn.getXCoordinate());
        policePawn.setY(board.policePawn.getYCoordinate());

        resetHenchman();

        for(Henchman henchman : board.getHenchmen()) {
            VBox henchmanbox  = (VBox) pane.lookup("#henchman-" + henchman.getOwner());

            ImageView old = (ImageView) henchmanbox.getChildren().remove(0);

            ImageView henchmanImage = new ImageView(old.getImage());
            henchmanImage.setLayoutX(henchman.x);
            henchmanImage.setLayoutY(henchman.y);
            henchmanImage.setFitHeight(36);
            henchmanImage.setFitWidth(36);
            henchmanImage.getStyleClass().add("henchman");

            pane.getChildren().add(henchmanImage);
        }
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
    }

    @Override
    public void start() {
        boardcontroller.registerObserver(this);
        boardcontroller.prepareView();

        boardcontroller.registerListeners();
    }
}
