package counterfeiters.firebase;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

    public List<QueryDocumentSnapshot> query(String collection, String key, String value) {
        Query query = db.collection(collection).whereEqualTo(key, value);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        try {
            return querySnapshot.get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error executing query on: " + collection);
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
