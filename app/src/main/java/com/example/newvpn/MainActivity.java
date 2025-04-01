package com.example.newvpn;

import android.os.Bundle;
import android.view.View;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.newvpn.session.Login;
import com.example.newvpn.utils.ButtonsNavigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);
        
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        redirectToLoginAfterDelay();
    }
    
    /**
     * Redirects to the login screen after a delay.
     */
    private void redirectToLoginAfterDelay() {
        // Delay in milliseconds
        int delay = 3000;
        new Handler(Looper.getMainLooper()).postDelayed(
            () -> ButtonsNavigation.navigateTo(MainActivity.this, Login.class),
            delay
        );
    }
}

