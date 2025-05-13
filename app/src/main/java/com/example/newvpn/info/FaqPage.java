package com.example.newvpn.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

public class FaqPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_faq);

        // Inyectar el logo en su contenedor específico
        HeaderHelper.injectHeaderLogo(this, R.id.faq_logo_container);

        // Inyectar el menú en su contenedor
        MenuHelper.injectMenu(this, R.id.faq_menu_container);
    }
}
