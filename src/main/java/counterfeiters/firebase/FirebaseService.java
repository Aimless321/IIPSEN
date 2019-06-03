package counterfeiters.firebase;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * The interaction between all of the classes and the Firestore db.
 * Uses the Singleton design pattern.
 * Get a instance by using <b>FirebaseService.getInstance();</b>
 * @author Wesley Bijleveld
 */
public class FirebaseService {
    public static FirebaseService instance = null;

    private Firestore db;

    private FirebaseService() {
        InputStream serviceAccount =
                getClass().getResourceAsStream("/firebase-creds.json");

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://iipsen-groep1.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);

            db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            System.err.println("Cannot read the firebase credentials json");
        }
    }

    public void listen(String collection, String document, EventListener<DocumentSnapshot> eventListener) {
        CollectionReference colRef = db.collection(collection);
        DocumentReference docRef = colRef.document(document);

        docRef.addSnapshotListener(eventListener);
    }

    public void set(String collection, String document, Map<String, Object> data) {
        DocumentReference docRef = db.collection(collection).document(document);

        ApiFuture<WriteResult> future = docRef.set(data);

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void setClass(String collection, String document, Object data) {
        DocumentReference docRef = db.collection(collection).document(document);

        ApiFuture<WriteResult> future = docRef.set(data);

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public DocumentReference insert(String collection) {
        return db.collection(collection).document();
    }

    public DocumentSnapshot get(String collection, String document) {
        DocumentReference docRef = db.collection(collection).document(document);

        ApiFuture<DocumentSnapshot> future = docRef.get();

        try {
            DocumentSnapshot documentData = future.get();

            if(documentData != null && documentData.exists()) {
                return documentData;
            } else {
                System.err.println("Cannot find document: " + document + " in collection: " + collection);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void delete(String collection, String document) {
        DocumentReference docRef = db.collection(collection).document(document);

        ApiFuture<WriteResult> future = docRef.delete();

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error deleting " + collection + "/" + document);
        }
    }

    public static FirebaseService getInstance() {
        if(instance == null) {
            instance = new FirebaseService();
        }

        return instance;
    }
}
