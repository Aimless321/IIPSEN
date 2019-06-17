package counterfeiters.models;


import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.Observer;

import java.nio.charset.Charset;
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

    /**
     * This method compares the password of the input from the user and firebase
     *
     * @author Robin van den Berg
     */

    public boolean checkCredentials(String username, String password) {
        FirebaseService fb = FirebaseService.getInstance();

        //Hash the password before checking it
        HashFunction hashFunction = Hashing.sha512();
        HashCode passwordHash = hashFunction.hashString(password, Charset.defaultCharset());

        try
        {
            String r = fb.get("users", username).getString("password");

            if (r.equals(passwordHash.toString()))
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

        //Hash the password before saving it
        HashFunction hashFunction = Hashing.sha512();
        HashCode hashCode = hashFunction.hashString(password, Charset.defaultCharset());

        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("password", hashCode.toString());

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
