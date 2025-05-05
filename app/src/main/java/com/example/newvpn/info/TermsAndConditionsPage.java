package com.example.newvpn.info;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newvpn.R;

public class TermsAndConditionsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_termsandconditions);
        
        // Configurar el título del encabezado
        TextView headerTitle = findViewById(R.id.tv_util_header_title);
        if (headerTitle != null) {
        }
        
        // Configurar el botón para volver
        Button backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
