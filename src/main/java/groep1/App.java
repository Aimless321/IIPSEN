package groep1;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;


public class App
{
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        InputStream serviceAccount =
                App.class.getResourceAsStream("/firebase-creds.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://iipsen-groep1.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

        Firestore db = FirestoreClient.getFirestore();
    }
}
