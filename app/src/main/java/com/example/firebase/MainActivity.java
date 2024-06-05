package com.example.firebase;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firebase.dao.listeners.OnUserReceivedListener;
import com.example.firebase.dao.listeners.OnUsersReceivedListener;
import com.example.firebase.dto.UserDTO;
import com.example.firebase.manager.ExecutorManager;
import com.example.firebase.manager.UserManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private UserManager userManager;
    private ExecutorService executorService;

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

        ExecutorManager executorManager = new ExecutorManager();
        userManager = new UserManager(executorManager.getExecutorService());

        UserDTO newUser = createUser();
        userManager.createUser(newUser);
        userManager.getUser(newUser.getId(), new OnUserReceivedListener() {
            @Override
            public void onUserReceived(UserDTO user) {
                MainActivity.this.onUserReceived(user);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        userManager.getAllUsers(new OnUsersReceivedListener() {
            @Override
            public void onUsersReceived(List<UserDTO> users) {
                MainActivity.this.onUsersReceived(users);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        executorService = Executors.newSingleThreadExecutor();

        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(v -> {
            executorService.execute(() -> {
                long fib = calculateFibonacci(45);
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Fibonacci result: " + fib, Toast.LENGTH_LONG).show();
                });
            });
        });

        Button testButton = findViewById(R.id.testButton);
        TextView testTextView = findViewById(R.id.testTextView);

        testButton.setOnClickListener(v -> {
            testTextView.setText("Button clicked" + System.currentTimeMillis());
        });
    }

    private UserDTO createUser() {
        UserDTO newUser = new UserDTO();
        newUser.setId("userExecutorId");
        newUser.setFirstName("soyExceutor");
        newUser.setBirthYear(2024);
        newUser.setLastName("userLastName");
        return newUser;
    }

    private void onUserReceived(UserDTO user) {
        runOnUiThread(() -> {
            TextView userIdTextView = findViewById(R.id.userId);
            TextView userFirstNameTextView = findViewById(R.id.userFirstName);
            TextView userLastNameTextView = findViewById(R.id.userLastName);
            TextView userBirthYearTextView = findViewById(R.id.userBirthYear);

            userIdTextView.setText("User ID: " + user.getId());
            userFirstNameTextView.setText("User First Name: " + user.getFirstName());
            userLastNameTextView.setText("User Last Name: " + user.getLastName());
            userBirthYearTextView.setText("User Birth Year: " + user.getBirthYear());
        });

        Log.d(TAG, "User received: " + user);
    }

    private void onUsersReceived(List<UserDTO> users) {
        Log.d(TAG, "Users received: " + users);
    }

    //ejecutar fibonacci de forma recursiva intencionalmente para hacer que tarde una banda
    private long calculateFibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return calculateFibonacci(n - 1) + calculateFibonacci(n - 2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
