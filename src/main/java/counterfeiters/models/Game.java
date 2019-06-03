package counterfeiters.models;

import java.util.ArrayList;
import java.util.Date;

public class Game {
    private int gameId;
    private int numPlayers;
    private int round;
    private Date startTime;

    ArrayList<Player> players = new ArrayList<Player>();
}
