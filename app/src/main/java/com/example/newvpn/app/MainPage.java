package com.example.newvpn.app;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

public class MainPage extends AppCompatActivity {

    private boolean isVpnOn = false;
    private View innerCircle;
    private View onText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_mainpage);

        // Inyectar el logo en su contenedor específico
        HeaderHelper.injectHeaderLogo(this, R.id.fl_mainpage_logo_container);

        // Inyectar el menú en su contenedor
        MenuHelper.injectMenu(this, R.id.fl_mainpage_menu_container);
        innerCircle = findViewById(R.id.innerCircle);
        onText = findViewById(R.id.boton_on);
        FrameLayout vpnButton = findViewById(R.id.vpnButton);

        vpnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVpnOn = !isVpnOn;
                if (isVpnOn) {
                    innerCircle.setBackgroundResource(R.drawable.circle_green);
                } else {
                    innerCircle.setBackgroundResource(R.drawable.circle_dark);
                }
            }
        });
    }
}
