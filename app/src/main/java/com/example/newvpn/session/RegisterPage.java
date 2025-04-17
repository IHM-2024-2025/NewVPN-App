package com.example.newvpn.session;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;

public class RegisterPage extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_register);

        // Inyectar el header
        HeaderHelper.injectHeader(this, R.id.fl_register_header_container, "New VPN");
    }

}
