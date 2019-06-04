package counterfeiters.models;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import counterfeiters.controllers.AccountController;
import counterfeiters.firebase.FirebaseService;
import javafx.stage.Stage;

public class Account {
    private String username;
    private String password;
    FirebaseService fb;

    public void getData()
    {

    }



    public void login()
    {
        System.out.println("login");
    }
    public void CheckCredentials(String username, String password)
    {

        this.username = username;
        this.password = password;

    }
    public void checkInput(){}
    public void addUser(){}

}
