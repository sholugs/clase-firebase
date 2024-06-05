package com.example.firebase.manager;

import com.example.firebase.dao.FirebaseUserDAO;
import com.example.firebase.dao.listeners.OnUserReceivedListener;
import com.example.firebase.dao.listeners.OnUsersReceivedListener;
import com.example.firebase.dto.UserDTO;

import java.util.concurrent.ExecutorService;

public class UserManager {
    private FirebaseUserDAO firebaseUserDAO;
    private ExecutorService executorService;

    public UserManager(ExecutorService executorService) {
        this.firebaseUserDAO = new FirebaseUserDAO();
        this.executorService = executorService;
    }

    public void createUser(UserDTO user) {
        executorService.execute(() -> firebaseUserDAO.createUser(user));
    }

    public void getUser(String userId, OnUserReceivedListener listener) {
        executorService.execute(() -> firebaseUserDAO.getUser(userId, listener));
    }

    public void getAllUsers(OnUsersReceivedListener listener) {
        executorService.execute(() -> firebaseUserDAO.getAllUsers(listener));
    }
}
