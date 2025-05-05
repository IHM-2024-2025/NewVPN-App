package com.example.newvpn.info;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

public class UserDataPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_userdata);

        // Inyectar el header y menú en la página de user data
        HeaderHelper.injectHeader(this, R.id.fl_info_userdata_header_container, getString(R.string.user_data_header_title));
        MenuHelper.injectMenu(this, R.id.fl_info_userdata_menu_container);
        Button btnGuardar = findViewById(R.id.btnGuardarCambios);

        btnGuardar.setOnClickListener(v -> {
            Toast.makeText(this, R.string.changes_saved, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
