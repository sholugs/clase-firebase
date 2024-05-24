package com.example.firebase.dao;

import com.example.firebase.dao.listeners.OnUserReceivedListener;
import com.example.firebase.dao.listeners.OnUsersReceivedListener;
import com.example.firebase.dto.UserDTO;

public interface UserDAO {
    void createUser(UserDTO user);
    void updateUser(UserDTO user);
    void deleteUser(UserDTO user);
    void getUser(String userId, OnUserReceivedListener listener);
    void getAllUsers(OnUsersReceivedListener listener);
}