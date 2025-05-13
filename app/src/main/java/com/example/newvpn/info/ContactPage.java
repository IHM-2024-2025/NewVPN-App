package com.example.newvpn.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

public class ContactPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_contact);
        // Inyectar el header
        HeaderHelper.injectHeader(this, R.id.fl_suppport_header_container,getString(R.string.support));

        // Inyectar el menú
        MenuHelper.injectMenu(this, R.id.fl_support_menu_container);

        View tInfoSendMessage = findViewById(R.id.bt_info_send_message);
        View irFaq = findViewById(R.id.ir_faq);
        View btInfoChat = findViewById(R.id.bt_info_chat);

        // Habilitar todos los botones
        tInfoSendMessage.setEnabled(true);
        irFaq.setEnabled(true);
        btInfoChat.setEnabled(true);

        // Botón 1: Ir a LiveChatPage
        tInfoSendMessage.setOnClickListener(v -> {
            Intent intent = new Intent(ContactPage.this, LiveChatPage.class);
            startActivity(intent);
        });

        // Botón 2: Ir a FaqPage
        irFaq.setOnClickListener(v -> {
            Intent intent = new Intent(ContactPage.this, FaqPage.class);
            startActivity(intent);
        });

        // Botón 3: Mostrar alerta de mensaje enviado
        btInfoChat.setOnClickListener(v -> new AlertDialog.Builder(ContactPage.this)
                .setTitle(R.string.message_send)
                .setMessage(R.string.message_send_sucess)
                .setPositiveButton(R.string.ok, null)
                .show());
    }
}
