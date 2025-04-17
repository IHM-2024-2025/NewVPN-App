package com.example.newvpn.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_mainpage);

        // Inyectar el logo en su contenedor específico
        HeaderHelper.injectHeaderLogo(this, R.id.fl_mainpage_logo_container);

        // Inyectar el menú en su contenedor
        MenuHelper.injectMenu(this, R.id.fl_mainpage_menu_container);
    }
}
