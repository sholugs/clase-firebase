package com.example.firebase.dao;

import static android.content.ContentValues.TAG;
import static android.util.Log.*;

import android.util.Log;

import com.example.firebase.dao.listeners.OnUserReceivedListener;
import com.example.firebase.dao.listeners.OnUsersReceivedListener;
import com.example.firebase.dto.UserDTO;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FirebaseUserDAO implements UserDAO {
    private FirebaseFirestore db;

    public FirebaseUserDAO() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void createUser(UserDTO user) {
        db.collection("users")
                .document(user.getId())
                .set(user)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User successfully created with ID: " + user.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error creating user", e));
    }

    @Override
    public void updateUser(UserDTO user) {
        db.collection("users")
                .document(user.getId())
                .set(user)
                .addOnSuccessListener(aVoid -> d(TAG, "DocumentSnapshot successfully updated!"))
                .addOnFailureListener(e -> w(TAG, "Error updating document"));
    }

    @Override
    public void deleteUser(UserDTO user) {
        db.collection("users")
                .document(user.getId())
                .delete()
                .addOnSuccessListener(aVoid -> d(TAG, "DocumentSnapshot successfully deleted!"))
                .addOnFailureListener(e -> w(TAG, "Error deleting document", e));
    }

    @Override
    public void getUser(String userId, OnUserReceivedListener listener) {
        db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    UserDTO user = documentSnapshot.toObject(UserDTO.class);
                    Log.d(TAG, "DocumentSnapshot data: " + user);
                    listener.onUserReceived(user);
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error getting document", e);
                    listener.onError(e);
                });
    }
    @Override
    public void getAllUsers(OnUsersReceivedListener listener) {
        db.collection("users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<UserDTO> users = queryDocumentSnapshots.toObjects(UserDTO.class);
                    d(TAG, "DocumentSnapshot data: " + users);
                    listener.onUsersReceived(users);
                })
                .addOnFailureListener(e -> {
                    w(TAG, "Error getting documents", e);
                    listener.onError(e);
                });
    }
}