package counterfeiters.controllers;

import counterfeiters.models.Account;
import counterfeiters.views.Observer;

/**
 * This class arranges further linking to the 'Account' model.
 *
 * @author Ali Rezaa Ghariebiyan, Robin van den Berg
 * @version 05-06-2019
 * @return checkCredentials
 * */
public class AccountController {

    private ApplicationController app;
    private Account account;

    //Constructor
    public AccountController(ApplicationController applicationController) {
        this.app = applicationController;
        account = new Account();
    }

    /**
     * This class checks the input and is then further picked up in 'Account'
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 05-06-2019
     * @return checkCredentials
     * */
    public boolean checkCredentials(String username, String password, String passwordCheck){
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

    public String getUsername() {
        return account.getUsername();
    }

    public void registerObserver(Observer observer) {
        account.registerObserver(observer);
    }
}
