package counterfeiters.events;

import counterfeiters.models.LotteryTicket;

import javax.swing.text.html.ImageView;
import java.util.ArrayList;

public class EventHandler {
    private static EventHandler instance;

    public static EventHandler getInstance() {
        if(instance == null) {
            instance = new EventHandler();
        }

        return instance;
    }

    //Private constructor to block creating an instance the normal way
    private EventHandler() {}

    private ArrayList<EventListener> listeners = new ArrayList<>();

    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }

    public void startRound() {
        for(EventListener listener : listeners) {
            listener.onRoundStart();
        }
    }

    public void endRound() {
        for(EventListener listener : listeners) {
            listener.onRoundEnd();
        }
    }

    public void endGame() {
        for(EventListener listener : listeners) {
            listener.onGameEnd();
        }
    }
}
