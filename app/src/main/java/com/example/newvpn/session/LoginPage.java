package com.example.newvpn.session;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.newvpn.R;
import com.example.newvpn.app.MainPage;
import com.example.newvpn.utils.ButtonsNavigation;
import com.example.newvpn.utils.HeaderHelper;

public class LoginPage extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_login);

        // Inyectar el header
        HeaderHelper.injectHeader(this, R.id.fl_login_header_container, "New VPN");

        // Encontrar el botón de inicio de sesión
        Button loginButton = findViewById(R.id.bt_session_login_login);
        
        // Agregar listener para el evento de clic
        loginButton.setOnClickListener(v -> {
            // Navegar a MainPage al hacer clic en el botón
            ButtonsNavigation.navigateTo(LoginPage.this, MainPage.class);
        });

        // Encontrar el TextView de registro
        TextView registerTextView = findViewById(R.id.tv_session_login_goto_register);

        // Añadir listener para el evento de clic
        registerTextView.setOnClickListener(v -> {
            // Navegar a RegisterPage al hacer clic en el TextView
            ButtonsNavigation.navigateTo(LoginPage.this, RegisterPage.class);
        });

        // Añadir listener para el texto "He olvidado mi contraseña"
        TextView forgotPasswordTextView = findViewById(R.id.tv_session_login_forgotpassword);
        forgotPasswordTextView.setOnClickListener(v -> {
            // Mostrar un Toast indicando que la función no está implementada
            Toast.makeText(LoginPage.this, getString(R.string.not_implemented), Toast.LENGTH_SHORT).show();
        });
    }
}
