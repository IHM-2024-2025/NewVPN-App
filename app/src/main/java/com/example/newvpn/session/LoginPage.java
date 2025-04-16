package com.example.newvpn.session;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.newvpn.R;
import com.example.newvpn.app.MainPage;
import com.example.newvpn.utils.ButtonsNavigation;

public class LoginPage extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_login);
        
        // Encontrar el botón de inicio de sesión
        Button loginButton = findViewById(R.id.bt_session_login_login);
        
        // Agregar listener para el evento de clic
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a MainPage al hacer clic en el botón
                ButtonsNavigation.navigateTo(LoginPage.this, MainPage.class);
            }
        });
    }
}
