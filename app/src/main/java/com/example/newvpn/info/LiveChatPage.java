package com.example.newvpn.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newvpn.R;
import com.example.newvpn.utils.MenuHelper;

public class LiveChatPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_livechat);
        View volverChat = findViewById(R.id.volver_chat);

        // Habilita el botón
        volverChat.setEnabled(true);

        // Acción al hacer clic
        volverChat.setOnClickListener(v -> {
            // Ir a la actividad ContactPage
            Intent intent = new Intent(LiveChatPage.this, ContactPage.class);
            startActivity(intent);
            finish();
        });
        
        // Inyectar el menú
        MenuHelper.injectMenu(this, R.id.fl_livechat_menu_container);
    }
}
