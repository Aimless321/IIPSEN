package counterfeiters.models;

import com.google.cloud.firestore.annotation.Exclude;
import com.google.type.Money;
import counterfeiters.events.EventListener;
import counterfeiters.firebase.FirebaseService;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player{
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

        //TODO: Remove for real version
        bahamasBank.setTotalBankMoney(250);
        realMoney.setTotalMoney(100);
    }

    public void leaveLobby(Game game) {
        FirebaseService fb = FirebaseService.getInstance();

        //If the lobby will be empty after this or if this player is the host, remove the lobby from firebase
        //Else remove this player from the lobby and update firebase
        if (game.getNumPlayers() == 1 || game.getPlayers().get(0) == this) {
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

    public void printMoney()
    {
        int printerCounter = 0;
        int printQuality = getPrintQuality();

        for (Card card : cards) {
            if(card.getName().equals("printer")) {
                printerCounter++;
            }
        }

        switch (printQuality){
            case 1:
                updateMoneyPlus(MoneyType.FAKE_ONE, 2 * printerCounter);
                break;
            case 2:
                updateMoneyPlus(MoneyType.FAKE_TWO, 2 * printerCounter);
                break;
            case 3:
                updateMoneyPlus(MoneyType.FAKE_THREE, 2 * printerCounter);
                break;
        }
    }

    private int getPrintQuality() {
        Set<PrinterUpgrade.UpgradeType> upgrades = new HashSet<>();

        for (Card card : cards) {
            switch (card.getImg()) {
                case "/cards/paper.png":
                    upgrades.add(PrinterUpgrade.UpgradeType.PAPER);
                    break;
                case "/cards/paint.png":
                    upgrades.add(PrinterUpgrade.UpgradeType.PAINT);
                    break;
                case "/cards/hologram.png":
                    upgrades.add(PrinterUpgrade.UpgradeType.HOLOGRAM);
                    break;
            }
        }

        return upgrades.size();
    }

    /**
     * This switch will call the right method based on the given moneyType and it will add the right amount.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 09-06-2019
     */
    public void updateMoneyPlus(MoneyType type, int amount) {
        switch (type) {
            case FAKE_ONE:
                fakeMoney.setQualityOne(fakeMoney.getQualityOne() + amount);
                break;
            case FAKE_TWO:
                fakeMoney.setQualityTwo(fakeMoney.getQualityTwo() + amount);
                break;
            case FAKE_THREE:
                fakeMoney.setQualityThree(fakeMoney.getQualityThree() + amount);
                break;
            case REAL:
                realMoney.setTotalMoney(realMoney.getTotalMoney() + amount);
                break;
            case BAHAMAS:
                bahamasBank.setTotalBankMoney(bahamasBank.getTotalBankMoney() + amount);
                break;
            default:
                break;
        }
    }

    /**
     * This switch will call the right method based on the given money type and it will reduce the right amount.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 09-06-2019
     */
    public void updateMoneyReduce(MoneyType type, int amount) {
        switch (type) {
            case FAKE_ONE:
                fakeMoney.setQualityOne(fakeMoney.getQualityOne() - amount);
                break;
            case FAKE_TWO:
                fakeMoney.setQualityTwo(fakeMoney.getQualityTwo() - amount);
                break;
            case FAKE_THREE:
                fakeMoney.setQualityThree(fakeMoney.getQualityThree() - amount);
                break;
            case REAL:
                realMoney.setTotalMoney(realMoney.getTotalMoney() - amount);
                break;
            case BAHAMAS:
                bahamasBank.setTotalBankMoney(bahamasBank.getTotalBankMoney() - amount);
                break;
            default:
                break;
        }
    }

    public boolean checkReduceBahamas(int amount) {
        return bahamasBank.getTotalBankMoney() >= amount;
    }

    public boolean checkAddBahamas(int amount) {
        return realMoney.getTotalMoney() >= amount;
    }

    public void addCard(Card card) {
        cards.add(card);
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

    public RealMoney getRealMoney() {
        return realMoney;
    }

    public BahamasBank getBahamasBank() {
        return bahamasBank;
    }

    public boolean hasCard(Card card) {
        for (Card n : cards) {
            if (n.getName().equals(card.getName())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}

