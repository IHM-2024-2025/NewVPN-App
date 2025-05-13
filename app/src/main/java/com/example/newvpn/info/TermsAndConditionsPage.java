package com.example.newvpn.info;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;

/**
 * Actividad que muestra los términos y condiciones de la aplicación.
 */
public class TermsAndConditionsPage extends AppCompatActivity {
    private static final String TAG = "TermsAndConditions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
            setContentView(R.layout.activity_info_termsandconditions);
            
            // Configurar el encabezado
            setupHeader();
            
            // Cargar el contenido
            setupContent();
            
            // Configurar el botón para volver
            setupBackButton();
        } catch (Exception e) {
            Log.e(TAG, "Error al inicializar la actividad", e);
            Toast.makeText(this, "Error al cargar la página. Por favor, inténtelo de nuevo.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    
    /**
     * Configura el encabezado de la página utilizando HeaderHelper
     */
    private void setupHeader() {
        try {
            HeaderHelper.injectHeader(this, R.id.headerContainer, getString(R.string.register_terms_and_conditions));
        } catch (Exception e) {
            // No terminamos la actividad ya que podemos seguir sin el header
            Toast.makeText(this, "Error al cargar el encabezado", Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * Carga el texto de los términos y condiciones desde los recursos
     */
    private void setupContent() {
        TextView contentView = findViewById(R.id.tvTermsContent);
        if (contentView != null) {
            try {
                String termsContent = getString(R.string.terms_content);
                contentView.setText(termsContent);
            } catch (Exception e) {
                Log.e(TAG, "Error al cargar el texto de términos y condiciones", e);
            }
        } else {
            Log.e(TAG, "No se encontró el TextView para el contenido");
        }
    }
    
    /**
     * Configura el botón para volver a la pantalla anterior
     */
    private void setupBackButton() {
        Button backButton = findViewById(R.id.btnBack);
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                try {
                    Log.d(TAG, "Botón de retorno pulsado");
                    finish();
                } catch (Exception e) {
                    Log.e(TAG, "Error al procesar clic en botón de retorno", e);
                    finish();
                }
            });
        } else {
            Log.e(TAG, "No se encontró el botón de retorno");
        }
    }
    
    @Override
    public void onBackPressed() {
        finish();
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d(TAG, "Botón de navegación pulsado");
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
