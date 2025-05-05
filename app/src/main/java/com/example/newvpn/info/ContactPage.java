package com.example.newvpn.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newvpn.R;

public class ContactPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_contact);

        View tInfoSendMessage = findViewById(R.id.t_info_send_message);
        View irFaq = findViewById(R.id.ir_faq);
        View btInfoChat = findViewById(R.id.bt_info_chat);

        // Habilitar todos los botones
        tInfoSendMessage.setEnabled(true);
        irFaq.setEnabled(true);
        btInfoChat.setEnabled(true);

        // Botón 1: Ir a LiveChatPage
        tInfoSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactPage.this, LiveChatPage.class);
                startActivity(intent);
            }
        });

        // Botón 2: Ir a FaqPage
        irFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactPage.this, FaqPage.class);
                startActivity(intent);
            }
        });

        // Botón 3: Mostrar alerta de mensaje enviado
        btInfoChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ContactPage.this)
                        .setTitle("Mensaje enviado")
                        .setMessage("Tu mensaje ha sido enviado con éxito.")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }
}
