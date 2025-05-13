package com.example.newvpn.session;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newvpn.R;
import com.example.newvpn.utils.ButtonsNavigation;
import com.example.newvpn.utils.HeaderHelper;

public class RecoverPasswordPage extends AppCompatActivity {

    private EditText emailEditText;
    private CheckBox acceptEmailsCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_recoverpassword);

        // Inyectar el header
        HeaderHelper.injectHeader(this, R.id.fl_recover_header_container, getString(R.string.new_password));

        // Inicializar componentes
        emailEditText = findViewById(R.id.pt_session_recover_email);
        acceptEmailsCheckBox = findViewById(R.id.cb_session_recover_accept_emails);
        Button recoverButton = findViewById(R.id.bt_session_recover_submit);
        TextView backToLoginTextView = findViewById(R.id.tv_session_recover_back_to_login);

        // Configurar el botón de recuperar contraseña
        recoverButton.setOnClickListener(v -> {
            if (validateForm()) {
                // Mostrar Toast de éxito
                Toast.makeText(RecoverPasswordPage.this, 
                        R.string.recover_password_message,
                        Toast.LENGTH_LONG).show();
                
                // Navegar a la página de login después de 1 segundos
                new Handler().postDelayed(() -> {
                    ButtonsNavigation.navigateTo(RecoverPasswordPage.this, LoginPage.class);
                    finish();
                }, 1000);
            }
        });

        // Configurar el enlace para volver al login
        backToLoginTextView.setOnClickListener(v -> {
            ButtonsNavigation.navigateTo(RecoverPasswordPage.this, LoginPage.class);
            finish();
        });
    }

    private boolean validateForm() {
        String email = emailEditText.getText().toString().trim();
        
        // Validar que el email no esté vacío
        if (email.isEmpty()) {
            emailEditText.setError(getString(R.string.error_no_email));
            return false;
        }
        
        // Validar el formato del email usando un patrón básico
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError(getString(R.string.error_invalid_email));
            return false;
        }
        
        // Validar que el checkbox esté marcado
        if (!acceptEmailsCheckBox.isChecked()) {
            Toast.makeText(this, R.string.error_acept_emails,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }
}
