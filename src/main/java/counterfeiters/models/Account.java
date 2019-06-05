package counterfeiters.models;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.Observer;
import counterfeiters.views.RegisterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Deze model maakt het mogelijk om de gegevens van een account te tonen.
 *
 * @author Ali Rezaa Ghariebiyan
 * version 03-06-2019
 */

public class Account implements Observable{
    private ArrayList<Observer> observers = new ArrayList<>();
    private String textField;

    public static void login(){}
    public static void register(){}

    public boolean checkCredentials(String username, String password, String passwordCheck){
        if (password.equals(passwordCheck)) {
            if(verifyUser(username, password)){
                verifyUser(username, password);
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
        else {
            textField = "Wrong password!";
            notifyAllObservers();
            return false;
        }
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
//        System.out.println(observers);
        for(Observer observer : observers) {
            observer.update(this);
        }
    }

    public boolean verifyUser(String username, String password) {
        FirebaseService fb = FirebaseService.getInstance();

        List<QueryDocumentSnapshot> documents = fb.query("users", "username", username);

        if (documents.size() == 0){
            saveUser(username, password);
            return true;
        }
        else{

            notifyAllObservers();
            return false;
        }
    }

    public void saveUser(String username, String password){
        FirebaseService fb = FirebaseService.getInstance();

        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);

        fb.set("users", username, data);
        textField = "";
    }

    public String getTextField() {
        return textField;
    }
}
