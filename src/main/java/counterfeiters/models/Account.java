package counterfeiters.models;


import com.google.cloud.firestore.QueryDocumentSnapshot;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Deze model maakt het mogelijk om de gegevens van een account te tonen.
 *
 * @author Ali Rezaa Ghariebiyan, Robin van den Berg
 * @version 05-06-2019
 */

public class Account implements Observable{
    private ArrayList<Observer> observers = new ArrayList<>();
    private String textField;
    private String username;

    public boolean checkCredentials(String username, String password) {
        FirebaseService fb = FirebaseService.getInstance();

        String p = password;

        try
        {
            String r = fb.get("users", username).getString("password");

            if (r.equals(p))
            {
                this.username = username;

                return true;
            }
            else
                {
                    textField = "Password incorrect";
                    notifyAllObservers();
                    return false;
                }

        }
        catch (Exception e)
        {
            textField = "Username not found";
            notifyAllObservers();

        }

        return false;
    }
    
    /**
     * Checks input, checks password, checks username. If it's wrong te view will be updated with a notification.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 05-06-2019
     * @return verifyUser
     * */
    public boolean checkCredentials(String username, String password, String passwordCheck){
            if (username.isEmpty() || password.isEmpty() || passwordCheck.isEmpty()){
                textField = "Incorrect Entry!";
                notifyAllObservers();
                return false;
            }

           if (!password.equals(passwordCheck)) {
               textField = "Password does not match!";
               notifyAllObservers();
               return false;
           }

           if(verifyUser(username, password)) {
               textField = "";
               notifyAllObservers();
               return true;
           }
           else{
               System.out.println("naam bestaat");
               textField = "Username already exist!";
               notifyAllObservers();
               return false;
           }


    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        for(Observer observer : observers) {
            observer.update(this);
        }
    }

    /**
     * Checks whether the username doesn't exist and then calls the 'addUser' method else updates view.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 05-06-2019
     * @return verification
     * */
    public boolean verifyUser(String username, String password) {
        FirebaseService fb = FirebaseService.getInstance();

        List<QueryDocumentSnapshot> documents = fb.query("users", "username", username);

        if (documents.size() == 0){
            addUser(username, password);
            return true;
        }
        else{
            notifyAllObservers();
            return false;
        }
    }


    /**
     *  Saves user in FireStore.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 05-06-2019
     * */
    public void addUser(String username, String password){
        FirebaseService fb = FirebaseService.getInstance();

        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);

        fb.set("users", username, data);
        textField = "";
    }

    public String getTextField() {
        return textField; //Voor het wijzigen van het tekstveld voor foutmeldingen.
    }

    public String getUsername() {
        return username;
    }
}
