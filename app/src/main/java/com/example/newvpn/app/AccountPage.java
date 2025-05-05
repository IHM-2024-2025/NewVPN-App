package com.example.newvpn.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.newvpn.R;
import com.example.newvpn.info.UserDataPage;
import com.example.newvpn.monetize.PayPage;
import com.example.newvpn.session.LoginPage;
import com.example.newvpn.utils.ButtonsNavigation;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

public class AccountPage extends AppCompatActivity {

    private Button btnSubscribe;
    private TextView txtNombreUsuario, txtCorreo;
    private Button btnCerrarSesion, btnCancelarSuscripcion, btnSoporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_account);

        // Inyectar el header
        HeaderHelper.injectHeader(this, R.id.fl_account_header_container, "Mi Cuenta");

        // Inyectar el menú
        MenuHelper.injectMenu(this, R.id.fl_account_menu_container);

        // Inicializar vistas
        initializeViews();

        // Configurar listeners
        setupListeners();

        // Cargar datos del usuario
        loadUserData();
    }

    private void initializeViews() {
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtCorreo = findViewById(R.id.txtCorreo);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnCancelarSuscripcion = findViewById(R.id.btnCancelarSuscripcion);
        btnSubscribe = findViewById(R.id.subscription);
        btnSoporte = findViewById(R.id.btnSoporte);
    }

    private void setupListeners() {
        // Botón de suscripción
        btnSubscribe.setOnClickListener(v -> {
            ButtonsNavigation.navigateTo(this, PayPage.class);
        });

        // Botón de cerrar sesión
        btnCerrarSesion.setOnClickListener(v -> {

            Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
            // Navegamos a la pantalla de login
            Intent intent = new Intent(AccountPage.this, LoginPage.class);
            // Limpiamos la pila de actividades para que el usuario no pueda volver atrás
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // Botón de cancelar suscripción
        btnCancelarSuscripcion.setOnClickListener(v -> {
            // Lógica para cancelar suscripción
            Toast.makeText(this, "Solicitud de cancelación enviada", Toast.LENGTH_SHORT).show();
        });

        // Icono de editar
        ImageView editIcon = findViewById(R.id.imgEditar);
        editIcon.setOnClickListener(v -> {
            Intent intent = new Intent(AccountPage.this, UserDataPage.class);
            startActivity(intent);
        });

        // Botón de soporte
        btnSoporte.setOnClickListener(v -> {
            // Navegar a la página de soporte o mostrar diálogo
            Toast.makeText(this, "Conectando con soporte...", Toast.LENGTH_SHORT).show();
            // Aquí puedes añadir la navegación a la página de soporte cuando esté disponible
        });
    }

    private void loadUserData() {
        // Simulación de datos - En un caso real, estos datos vendrían de una base de datos o API
        txtNombreUsuario.setText("Rodrigo Díaz de Vivar");
        txtCorreo.setText("cid@alu.ubu.es");
    }
}
