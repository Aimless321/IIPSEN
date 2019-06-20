package counterfeiters.models;

import counterfeiters.events.EventHandler;
import counterfeiters.events.EventListener;

public class FirstPlayerPawn implements EventListener {
    private Player firstPlayer;
    private Player nextFirstPlayer;

    public FirstPlayerPawn() {
        EventHandler.getInstance().registerListener(this);
    }

    public void updateData(FirstPlayerPawn firstPlayerPawn) {
        this.firstPlayer = firstPlayerPawn.getFirstPlayer();
    }

    @Override
    public void onRoundEnd() {
        this.firstPlayer = nextFirstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getNextFirstPlayer() {
        return nextFirstPlayer;
    }

    public void setNextFirstPlayer(Player nextFirstPlayer) {
        this.nextFirstPlayer = nextFirstPlayer;
    }

    @Override
    public void onRoundStart() {

    }

    @Override
    public void onGameEnd() {

    }
}
