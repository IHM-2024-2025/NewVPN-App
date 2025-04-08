package com.example.newvpn.config;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.newvpn.R;
import com.example.newvpn.utils.ButtonsNavigation;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

public class PreferencesPage extends AppCompatActivity {

    private ConstraintLayout connectionLayout;
    private Switch darkModeSwitch;
    private ImageButton decreaseFontButton;
    private ImageButton increaseFontButton;
    private ConstraintLayout permissionsLayout;
    private ConstraintLayout inviteFriendLayout;
    private float fontScale = 1.0f;
    private static final float FONT_SCALE_STEP = 0.1f;
    private View headerView;
    private View menuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_preferences);
        
        // Inyectar el header y establecer el título
        headerView = HeaderHelper.injectHeader(this, R.id.header_container, getString(R.string.config_title));
        
        // Inyectar el menú de navegación
        menuView = MenuHelper.injectMenu(this, R.id.menu_container);
        
        initializeViews();
        setupListeners();
    }
    
    private void initializeViews() {
        connectionLayout = findViewById(R.id.cl_connection);
        darkModeSwitch = findViewById(R.id.switch_config_pref_darkmode);
        decreaseFontButton = findViewById(R.id.btn_font_decrease);
        increaseFontButton = findViewById(R.id.btn_font_increase);
        permissionsLayout = findViewById(R.id.cl_permissions);
        inviteFriendLayout = findViewById(R.id.cl_invite_friend);
        
        // Inicializar el switch con el modo actual
        darkModeSwitch.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
    }
    
    private void setupListeners() {
        // Conexión - navega a la página de conexión
        connectionLayout.setOnClickListener(v -> {
            try {
                ButtonsNavigation.navigateTo(this, Class.forName("com.example.newvpn.ConnexionPage"));
            } catch (ClassNotFoundException e) {
                Toast.makeText(this, "Página no disponible", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Modo oscuro - cambia el tema de la app
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(this, "Modo oscuro activado", Toast.LENGTH_SHORT).show();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Toast.makeText(this, "Modo oscuro desactivado", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Tamaño de letra - aumenta/disminuye el tamaño de fuente
        decreaseFontButton.setOnClickListener(v -> changeFontSize(false));
        increaseFontButton.setOnClickListener(v -> changeFontSize(true));
        
        // Permisos - navega a la página de permisos
        permissionsLayout.setOnClickListener(v -> {
            try {
                ButtonsNavigation.navigateTo(this, Class.forName("com.example.newvpn.PermissionsPage"));
            } catch (ClassNotFoundException e) {
                Toast.makeText(this, "Página no disponible", Toast.LENGTH_SHORT).show();
            }
        });
        
        // Invitar a un amigo - copia URL al portapapeles
        inviteFriendLayout.setOnClickListener(v -> {
            copyToClipboard();
            Toast.makeText(this, "URL copiada al portapapeles", Toast.LENGTH_SHORT).show();
        });
    }
    
    private void changeFontSize(boolean increase) {
        if (increase) {
            fontScale += FONT_SCALE_STEP;
            Toast.makeText(this, "Tamaño de letra aumentado", Toast.LENGTH_SHORT).show();
        } else {
            if (fontScale > 0.5f) { // Evitar tamaños demasiado pequeños
                fontScale -= FONT_SCALE_STEP;
                Toast.makeText(this, "Tamaño de letra reducido", Toast.LENGTH_SHORT).show();
            }
        }
        
        // Aplicar el nuevo tamaño de fuente
        getResources().getConfiguration().fontScale = fontScale;
        recreate(); // Recrear la actividad para reflejar los cambios
    }
    
    private void copyToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Enlace de invitación", "https://joseleelportfolio.vercel.app");
        clipboard.setPrimaryClip(clip);
    }
}
