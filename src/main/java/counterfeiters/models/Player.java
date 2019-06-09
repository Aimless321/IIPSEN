package counterfeiters.models;

import com.google.cloud.firestore.annotation.Exclude;
import counterfeiters.firebase.FirebaseService;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Player {
    private String userName;
    private int playerId = 1;
    private int score;

    FakeMoney fakeMoney;
    RealMoney realMoney;
    BahamasBank bahamasBank;

    private ArrayList<Card> cards = new ArrayList<>();

    public Player() {

    }

    public Player(String userName) {
        this.userName = userName;
        this.fakeMoney = new FakeMoney();
        this.realMoney = new RealMoney();
        this.bahamasBank = new BahamasBank();
        realMoney.setTotalMoney(200);
        bahamasBank.setTotalBankMoney(0);

    }

    public void leaveLobby(Game game) {
        FirebaseService fb = FirebaseService.getInstance();

        //If the lobby will be empty after this or if this player is the host, remove the lobby from firebase
        //Else remove this player from the lobby and update firebase
        if(game.getNumPlayers() == 1 || game.getPlayers().get(0) == this) {
            game.delete();
        } else {
            game.removePlayer(this);
        }
    }

    public String getUserName() {
        return userName;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getScore() {
        return score;
    }

    @Exclude
    public String getCharacterName() {
        String charaterName;

        switch (playerId) {
            case 1:
                charaterName = "croc";
                break;
            case 2:
                charaterName = "deer";
                break;
            case 3:
                charaterName = "herron";
                break;
            case 4:
                charaterName = "hippo";
                break;
            default:
                charaterName = "croc";
                System.err.println("Invalid player number passed to Player.getCharacterName()");
                break;
        }

        return charaterName;
    }

    @Exclude
    public Image getCharacterImagePath() {
        String imagePath = "/players/" + getCharacterName() + ".jpg";
        return new Image(getClass().getResourceAsStream(imagePath));
    }


    public void updateMoney(int qualityOneMoney, int qualityTwoMoney, int qualityThreeMoney, int totalRealMoney, int totalBankMoney, int qualityId){
        switch(qualityId) {
            case 1:
                fakeMoney.setQualityOne(fakeMoney.getQualityOne() + qualityOneMoney);
                break;
            case 2:
                fakeMoney.setQualityTwo(fakeMoney.getQualityTwo() + qualityTwoMoney);
                break;
            case 3:
                fakeMoney.setQualityThree(fakeMoney.getQualityThree() + qualityThreeMoney);
                break;
            case 4:
                realMoney.setTotalMoney(realMoney.getTotalMoney() + totalRealMoney);
                break;
            case 5:
                bahamasBank.setTotalBankMoney(bahamasBank.getTotalBankMoney() + totalBankMoney);
                break;
            default:
                break;
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public FakeMoney getFakeMoney() {
        return fakeMoney;
    }

    public RealMoney getRealMoney(){
        return realMoney;
    }

    public BahamasBank getBahamasBank(){return bahamasBank;}


    //    public boolean hasPlaneTicket(){
//        return ;
//    }
}
