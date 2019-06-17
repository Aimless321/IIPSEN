package counterfeiters.models;

public class FirstPlayerPawn {
    private Player firstPlayer;

    public FirstPlayerPawn() {

    }

    public void updateData(FirstPlayerPawn firstPlayerPawn) {
        this.firstPlayer = firstPlayerPawn.getFirstPlayer();
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }
}
