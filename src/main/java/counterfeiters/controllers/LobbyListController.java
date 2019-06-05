package counterfeiters.controllers;

import counterfeiters.models.FirebaseModel;
import counterfeiters.models.Game;
import counterfeiters.views.Observer;

public class LobbyListController {

    private ApplicationController app;
    public FirebaseModel lobbyList;

    public LobbyListController(ApplicationController applicationController) {
        this.app = applicationController;
        lobbyList = new FirebaseModel();
    }

    public void registerObserver(Observer observer) {
        lobbyList.registerObserver(observer);
    }

    public void updateData(Game updateGame) {
        lobbyList.updateData();
    }


}
