package com.example.newvpn.info;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;

public class TermsAndConditionsPage extends AppCompatActivity {
    private static final String TAG = "TermsAndConditions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_info_termsandconditions);
            
            // Configurar el encabezado de forma segura
            setupHeader();
            
            // Verificar y configurar el contenido
            setupContent();
            
            // Configurar el botón para volver
            setupBackButton();
        } catch (Exception e) {
            Log.e(TAG, "Error al inicializar la actividad: " + e.getMessage());
            Toast.makeText(this, "Error al cargar la página. Por favor, inténtelo de nuevo.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    
    private void setupHeader() {
        try {
            // Inyectar el header personalizado usando HeaderHelper
            HeaderHelper.injectHeader(this, R.id.headerContainer, getString(R.string.terms_title));
            Log.d(TAG, "Header inyectado correctamente");
        } catch (Exception e) {
            Log.e(TAG, "Error al inyectar el header: " + e.getMessage(), e);
            Toast.makeText(this, "Error al cargar el encabezado", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void setupContent() {
        try {
            TextView contentView = findViewById(R.id.tvTermsContent);
            if (contentView != null) {
                // Asegurarse de que el contenido se carga correctamente
                getResources().getString(R.string.terms_content);
                contentView.setText(R.string.terms_content);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al configurar el contenido: " + e.getMessage());
        }
    }
    
    private void setupBackButton() {
        try {
            Button backButton = findViewById(R.id.btnBack);
            if (backButton != null) {
                backButton.setOnClickListener(v -> onBackPressed());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al configurar el botón de retorno: " + e.getMessage());
        }
    }
    
    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
        } catch (Exception e) {
            Log.e(TAG, "Error al ejecutar onBackPressed: " + e.getMessage());
            finish();
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
