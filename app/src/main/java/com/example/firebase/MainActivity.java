package com.example.firebase;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firebase.dao.FirebaseUserDAO;
import com.example.firebase.dao.listeners.OnUserReceivedListener;
import com.example.firebase.dao.listeners.OnUsersReceivedListener;
import com.example.firebase.dto.UserDTO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseUserDAO firebaseUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseUserDAO = new FirebaseUserDAO();

        // Crear una instancia de UserDTO con los detalles del usuario
        UserDTO newUser = new UserDTO();
        newUser.setId("userId");
        newUser.setFirstName("userFirstName");
        newUser.setBirthYear(2000);
        newUser.setLastName("userLastName");

        // Llamar al método createUser() en firebaseUserDAO
        firebaseUserDAO.createUser(newUser);

        // Obtener y visualizar los detalles del usuario recién creado
        firebaseUserDAO.getUser(newUser.getId(), new OnUserReceivedListener() {
            @Override
            public void onUserReceived(UserDTO user) {
                TextView userIdTextView = findViewById(R.id.userId);
                TextView userFirstNameTextView = findViewById(R.id.userFirstName);
                TextView userLastNameTextView = findViewById(R.id.userLastName);
                TextView userBirthYearTextView = findViewById(R.id.userBirthYear);

                userIdTextView.setText("User ID: " + user.getId());
                userFirstNameTextView.setText("User First Name: " + user.getFirstName());
                userLastNameTextView.setText("User Last Name: " + user.getLastName());
                userBirthYearTextView.setText("User Birth Year: " + user.getBirthYear());

                Log.d(TAG, "User received: " + user);
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Error getting user", e);
            }
        });

        // Get all users
        firebaseUserDAO.getAllUsers(new OnUsersReceivedListener() {
            @Override
            public void onUsersReceived(List<UserDTO> users) {
                Log.d(TAG, "Users received: " + users);
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Error getting users", e);
            }
        });
    }
}