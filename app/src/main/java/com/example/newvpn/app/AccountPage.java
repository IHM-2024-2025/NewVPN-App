package com.example.newvpn.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.newvpn.R;
import com.example.newvpn.info.UserDataPage;
import com.example.newvpn.monetize.PayPage;
import com.example.newvpn.utils.ButtonsNavigation;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

public class AccountPage extends AppCompatActivity {

    private Button btnSubscribe;
    private TextView txtNombreUsuario, txtCorreo;
    private Button btnCerrarSesion, btnCancelarSuscripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_account);
        
        // Inyectar el header
        HeaderHelper.injectHeader(this, R.id.fl_account_header_container, "Mi Cuenta");
        
        // Inyectar el menú
        MenuHelper.injectMenu(this, R.id.fl_account_menu_container);
        
        // Inicializar y configurar el botón de suscripción
        btnSubscribe = findViewById(R.id.btnPremium);
        btnSubscribe.setOnClickListener(v -> {
            ButtonsNavigation.navigateTo(this, PayPage.class);
        });

        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtCorreo = findViewById(R.id.txtCorreo);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnCancelarSuscripcion = findViewById(R.id.btnCancelarSuscripcion);

        // Simulación de datos
        txtNombreUsuario.setText("Rodrigo Díaz de Vivar");
        txtCorreo.setText("cid@alu.ubu.es");
        //txtPlan.setText("Plan normal");

        btnCerrarSesion.setOnClickListener(v -> {
            // Acción de cierre de sesión
            finish(); // Por ahora simplemente cerramos la actividad
        });

        btnCancelarSuscripcion.setOnClickListener(v -> {
            // Lógica para cancelar suscripción
        });
        ImageView editIcon = findViewById(R.id.imgEditar);
        editIcon.setOnClickListener(v -> {
            Intent intent = new Intent(AccountPage.this, UserDataPage.class);
            startActivity(intent);
        });


    }
}
