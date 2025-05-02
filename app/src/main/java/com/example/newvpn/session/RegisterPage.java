package com.example.newvpn.session;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.newvpn.R;
import com.example.newvpn.app.MainPage;
import com.example.newvpn.utils.HeaderHelper;

public class RegisterPage extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_register);

        // Inyectar el header
        HeaderHelper.injectHeader(this, R.id.fl_register_header_container, "New VPN");
        
        // Configurar el evento para ir a la pantalla de inicio de sesión
        TextView loginTextView = findViewById(R.id.tv_session_register_goto_login);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la pantalla de inicio de sesión
                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(intent);
                finish(); // Cerrar la actividad actual
            }
        });
        
        // Configurar el botón de registro para redirigir a la página principal
        Button registerButton = findViewById(R.id.bt_session_register_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la pantalla principal
                Intent intent = new Intent(RegisterPage.this, MainPage.class);
                startActivity(intent);
                finish(); // Cerrar la actividad actual
            }
        });
    }
}
