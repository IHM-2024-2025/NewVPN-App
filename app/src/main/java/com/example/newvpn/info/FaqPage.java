package com.example.newvpn.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newvpn.R;

public class FaqPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_faq);
        View volverFaq = findViewById(R.id.volver_faq);

        // Habilitar el botón
        volverFaq.setEnabled(true);

        // Acción al hacer clic en el botón
        volverFaq.setOnClickListener(v -> {
            // Ir a la actividad ContactPage
            Intent intent = new Intent(FaqPage.this, ContactPage.class);
            startActivity(intent);
            finish(); // Opcional: cerrar FaqPage para que no se pueda volver con el botón "Atrás"
        });
    }
}
