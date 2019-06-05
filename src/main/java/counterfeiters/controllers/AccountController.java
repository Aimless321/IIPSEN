package counterfeiters.controllers;

import counterfeiters.models.Account;
import counterfeiters.views.Observer;

public class AccountController {

    private ApplicationController app;
    private Account account;

    public AccountController(ApplicationController applicationController) {
        this.app = applicationController;
        account = new Account();
    }

    public boolean checkCredentials(String username, String password, String passwordCheck){
//        account.checkCredentials(username, password, passwordCheck);
        if (account.checkCredentials(username, password, passwordCheck)){
            return true;
        }
        else {return false;}
    }

    public boolean loginPressed(String username, String password)
    {
        if(account.checkCredentials(username, password))
        {
            return true;
        }
        else{return false;}

    }

    public void registerObserver(Observer observer) {
        account.registerObserver(observer);
    }
}
