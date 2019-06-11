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

    /**
     * Creates a change listener on a document.
     * @param collection the name of the collection.
     * @param document the name of the document.
     * @param eventListener The EventListener that needs to be executed when the event takes place.
     * @return ListenerRegistration, so you can remove the listener when needed.
     */
    public ListenerRegistration listen(String collection, String document, EventListener<DocumentSnapshot> eventListener) {
        CollectionReference colRef = db.collection(collection);
        DocumentReference docRef = colRef.document(document);

        return docRef.addSnapshotListener(eventListener);
    }

    //listen to a whole collection
    public ListenerRegistration listenToCollection(String collection, EventListener<QuerySnapshot> eventListener) {
        CollectionReference colRef = db.collection(collection);

        return colRef.addSnapshotListener(eventListener);
    }

    /**
     * Write data to a document.
     * @param collection name of the collection.
     * @param document name of the document.
     * @param data data to write to the document.
     */
    public void set(String collection, String document, Map<String, Object> data) {
        DocumentReference docRef = db.collection(collection).document(document);

        ApiFuture<WriteResult> future = docRef.set(data);

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write an entire class to Firebase.
     * See: https://firebase.google.com/docs/firestore/query-data/get-data#custom_objects
     * @param collection
     * @param document
     * @param data
     */
    public void setClass(String collection, String document, Object data) {
        DocumentReference docRef = db.collection(collection).document(document);

        ApiFuture<WriteResult> future = docRef.set(data);

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert an empty document to a collection.
     * @param collection name of the collection.
     * @return a reference to the Document
     */
    public DocumentReference insert(String collection) {
        return db.collection(collection).document();
    }

    /**
     * Get a document from a collection.
     * @param collection name of the collection.
     * @param document name of the document.
     * @return Snapshot of the document.
     */
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

    //Melissa
    public List<QueryDocumentSnapshot> getAllDocumentsFromCollection(String collection)  {
        try {
            ApiFuture<QuerySnapshot> collectionData = db.collection(collection).get();

            if(collectionData != null) {
                List<QueryDocumentSnapshot> documentsList = collectionData.get().getDocuments();
                return documentsList;
            } else {
                System.err.println("Cannot find collection: " + collection);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Search for all documents in a collection that have a certain key and value.
     * @param collection name of the collection to search in.
     * @param key key to search for.
     * @param value value of the key.
     * @return List of documents that match the search.
     */
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

    /**
     * Delete a document from a collection.
     * @param collection name of the collection.
     * @param document name of the document.
     */
    public void delete(String collection, String document) {
        DocumentReference docRef = db.collection(collection).document(document);

        ApiFuture<WriteResult> future = docRef.delete();

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error deleting " + collection + "/" + document);
        }
    }

    /**
     * Singleton pattern to get an instance of this class.
     * @return instance of FirebaseService.
     */
    public static FirebaseService getInstance() {
        if(instance == null) {
            instance = new FirebaseService();
        }

        return instance;
    }
}
