package com.example.firebase.dao.listeners;

import com.example.firebase.dto.UserDTO;

import java.util.List;

public interface OnUserReceivedListener {
    void onUserReceived(UserDTO user);
    void onError(Exception e);
}