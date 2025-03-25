package com.example.newvpn;

import android.os.Bundle;
import android.view.View;
import android.os.Handler;
import android.os.Looper;

import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.newvpn.app.MainPage;
import com.example.newvpn.session.Login;
import com.example.newvpn.session.Register;
import com.example.newvpn.utils.ButtonsNavigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Set content view first
        setContentView(R.layout.activity_main);
        
        // Enable edge-to-edge after setting content view
        EdgeToEdge.enable(this);
        
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
        
        // Initialize buttons with try-catch to help debug any issues
        try {
            Button btnLogin = findViewById(R.id.btnLogin);
            Button btnRegister = findViewById(R.id.btnRegister);
            Button btnMainPage = findViewById(R.id.btnMainPage);
            
            // Set click listeners
            if (btnLogin != null) {
                btnLogin.setOnClickListener(view -> ButtonsNavigation.navigateTo(MainActivity.this, Login.class));
            }
            
            if (btnRegister != null) {
                btnRegister.setOnClickListener(view -> ButtonsNavigation.navigateTo(MainActivity.this, Register.class));
            }
            
            if (btnMainPage != null) {
                btnMainPage.setOnClickListener(view -> ButtonsNavigation.navigateTo(MainActivity.this, MainPage.class));
            }
        } catch (Exception e) {
            // Show toast message
            Toast.makeText(MainActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();

            // Wait 2 seconds and then exit
            // Exit the app
            new Handler(Looper.getMainLooper()).postDelayed(this::finish, 2000); // 2 seconds
        }
    }
}
