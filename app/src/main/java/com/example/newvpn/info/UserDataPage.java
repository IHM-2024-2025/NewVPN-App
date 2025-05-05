package com.example.newvpn.info;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

public class UserDataPage extends AppCompatActivity {

    private EditText etNombre, etApellidos, etTelefono, etPais, etEmail, etPassword;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_userdata);

        // Inyectar el header y menú en la página de user data
        HeaderHelper.injectHeader(this, R.id.fl_info_userdata_header_container, getString(R.string.user_data_header_title));
        MenuHelper.injectMenu(this, R.id.fl_info_userdata_menu_container);

        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etTelefono = findViewById(R.id.etTelefono);
        etPais = findViewById(R.id.etPais);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnGuardar = findViewById(R.id.btnGuardarCambios);

        btnGuardar.setOnClickListener(v -> {
            Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
