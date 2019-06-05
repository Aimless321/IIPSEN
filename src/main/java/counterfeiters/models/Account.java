package counterfeiters.models;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.Observer;
import counterfeiters.views.RegisterView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Deze model maakt het mogelijk om de gegevens van een account te tonen.
 *
 * @author Ali Rezaa Ghariebiyan
 * @author Robin van den Berg
 * version 05-06-2019
 */

public class Account implements Observable{
    private String username;
    private String password;
    private String textField;
    private Account account;
    private RegisterView registerView;
    private ArrayList<Observer> observers = new ArrayList<>();

    public void login()
    {



    }

    public static void register(){}

    public boolean checkCredentials(String username, String password)
    {
            FirebaseService fb = FirebaseService.getInstance();

            String p = password;
            String r = fb.get("users", username).getString("password");

            if (r.equals(p)) {
                return true;
            }
            else {return false;}
    }


    public static void addUser(String username, String password){}
    public static boolean checkInput(String username, String password){return true;}

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public Account() {}
    public Account(String username, String password, String passwordCheck)
    {
        if(checkPassword(username,password, passwordCheck))
        {
            this.username = username;
            this.password = password;
        }
    }


    public Account(String username, String password)
    {

        this.username = username;
        this.password = password;

    }


    @Override
    public void notifyAllObservers() {
        System.out.println(observers);
        for(Observer observer : observers) {
            System.out.println(observer);
            System.out.println(observers.toString());
            observer.update(this);
            System.out.println("I've found a obsersver");
        }
        System.out.println("notifyAll:: wrong pass");
    }

    public boolean checkPassword(String username, String password, String passwordCheck) {
        if (password.equals(passwordCheck)) {
            verifyUser(username, password);
            return true;
        }
        else {
            //TODO: Password komt niet overeen
            notifyAllObservers();
            return false;
        }
    }


    public void verifyUser(String username, String password) {
        FirebaseService fb = FirebaseService.getInstance();

        List<QueryDocumentSnapshot> documents = fb.query("users", "username", username);

        if (documents.size() == 0){
            Map<String, Object> data = new HashMap<>();
            data.put("username", username);
            data.put("password", password);

            fb.set("users", username, data);
        }
        //TODO: username is al in gebruik.
//        else{
//
//        }
    }

}
