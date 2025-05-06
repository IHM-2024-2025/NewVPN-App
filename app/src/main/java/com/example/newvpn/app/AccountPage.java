package com.example.newvpn.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.newvpn.R;
import com.example.newvpn.info.ContactPage;
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
        HeaderHelper.injectHeader(this, R.id.fl_account_header_container, getString(R.string.my_account));

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
            Toast.makeText(this, R.string.session_closed, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AccountPage.this, LoginPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // Botón de cancelar suscripción
        btnCancelarSuscripcion.setOnClickListener(v -> {
            Toast.makeText(this, R.string.subscription_cancel, Toast.LENGTH_SHORT).show();
        });

        // Icono de editar
        ImageView editIcon = findViewById(R.id.imgEditar);
        editIcon.setOnClickListener(v -> {
            Intent intent = new Intent(AccountPage.this, UserDataPage.class);
            startActivity(intent);
        });

        // Botón de soporte
        btnSoporte.setOnClickListener(v -> {
            Intent intent = new Intent(AccountPage.this, ContactPage.class);
            startActivity(intent);        });
    }

    private void loadUserData() {
        // Simulación de datos - En un caso real, estos datos vendrían de una base de datos o API
        txtNombreUsuario.setText(R.string.user_name);
        txtCorreo.setText(R.string.user_email);
    }
}
