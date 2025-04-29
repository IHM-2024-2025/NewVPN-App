package com.example.newvpn.config;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

public class PermissionsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_permissions);
        HeaderHelper.injectHeader(this, R.id.header_container, getString(R.string.permissions_title));
        MenuHelper.injectMenu(this, R.id.fl_mainpage_menu_container);
    }
}