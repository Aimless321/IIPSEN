package counterfeiters.views;

import counterfeiters.controllers.BoardController;
import counterfeiters.models.BlackMarket;
import counterfeiters.models.Board;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static java.lang.Thread.sleep;

public class BoardView implements Observer {

    public HBox blackMarketView;
    public ImageView policePawn;

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
        pane.setBackground(ViewUtilities.getBackground("/background/with-money-and-logo.png"));

        //Show it on the screen
        Scene scene = new Scene(root, ViewUtilities.screenWidth, ViewUtilities.screenHeight);
        stage.setScene(scene);
    }

    @FXML
    public void blackMarket(MouseEvent mouseEvent) {
        System.out.println("Black market pressed");
    }

    @FXML
    public void actionFieldLaunder(MouseEvent mouseEvent) {
        System.out.println("Launder button pressed");
        boardcontroller.advancePolice();
    }

    @FXML
    public void actionFieldFraud(MouseEvent mouseEvent) {

        System.out.println("Fraud button pressed");
        Button btn = (Button) mouseEvent.getSource();
        if(btn.getStyleClass().get(0) == "police" ) {
            boardcontroller.advancePolice();
        }
    }

    @FXML
    public void actionFieldFly(MouseEvent mouseEvent) {
        System.out.println("Fly button pressed");
    }

    @FXML
    public void actionFieldHealer(MouseEvent mouseEvent) {
        System.out.println("Healer button pressed");
    }

    @FXML
    public void actionFieldPrint(MouseEvent mouseEvent) {

        System.out.println("Print button pressed");
        Button btn = (Button) mouseEvent.getSource();
        if(btn.getStyleClass().get(0) == "police" ) {
            boardcontroller.advancePolice();
        }
    }

    @FXML
    public void pressRules(MouseEvent mouseEvent) {
        System.out.println("Rules button pressed");
    }

    @FXML
    public void pressCards(MouseEvent mouseEvent) {
        System.out.println("Cards button pressed");
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
    }

    @Override
    public void start() {
        boardcontroller.registerObserver(this);
        boardcontroller.prepareView();

    }
}
