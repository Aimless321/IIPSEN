package counterfeiters.controllers;

import counterfeiters.models.Account;

public class AccountController {

    private ApplicationController app;
    private Account account;

    public AccountController(ApplicationController applicationController) {
        this.app = applicationController;

    }

    public void addUser(String username, String password, String passwordCheck){
        account = new Account(username,password,passwordCheck);
    }

    public void login(String username, String password){}
}
