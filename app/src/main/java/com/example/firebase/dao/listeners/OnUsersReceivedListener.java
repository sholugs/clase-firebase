package com.example.firebase.dao.listeners;

import com.example.firebase.dto.UserDTO;

import java.util.List;

public interface OnUsersReceivedListener {
    void onUsersReceived(List<UserDTO> users);
    void onError(Exception e);
}
