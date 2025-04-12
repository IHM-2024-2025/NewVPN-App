package com.example.newvpn.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

public class AccountPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_account);
        
        // Inyectar el header
        HeaderHelper.injectHeader(this, R.id.fl_account_header_container, "Mi Cuenta");
        
        // Inyectar el menú
        MenuHelper.injectMenu(this, R.id.fl_account_menu_container);
    }
}
