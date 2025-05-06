package com.example.newvpn.config;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

import java.util.Locale;

public class PreferencesPage extends AppCompatActivity {

    private String TAG = "";

    private ConstraintLayout connectionLayout;
    private ImageButton decreaseFontButton;
    private ImageButton increaseFontButton;
    private ConstraintLayout permissionsLayout;
    private ConstraintLayout inviteFriendLayout;
    private ConstraintLayout selectLanguageLayout;
    private TextView currentLanguageTextView;
    private ConstraintLayout notificationsLayout;
    private ImageView notificationsIcon;
    private boolean notificationsEnabled = true;

    private float fontScale = 1.0f;
    private static final float FONT_SCALE_STEP = 0.1f;
    private static final String PREFS_NAME = "VPNPrefs";
    private static final String LANGUAGE_KEY = "language";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Cargar el idioma guardado antes de configurar la vista
        loadSavedLanguage();
        
        setContentView(R.layout.activity_config_preferences);

        try {
            HeaderHelper.injectHeader(this, R.id.header_container, getString(R.string.config_title));
            MenuHelper.injectMenu(this, R.id.fl_mainpage_menu_container);
            TAG = getString(R.string.preferencespage_title);

            initializeViews();
            setupListeners();
            updateLanguageDisplay();
        } catch (Exception e) {
            Log.e(TAG, "Error en onCreate: " + e.getMessage());
        }
    }

    private void initializeViews() {
        try {
            connectionLayout = findViewById(R.id.cl_connection);
            decreaseFontButton = findViewById(R.id.btn_font_decrease);
            increaseFontButton = findViewById(R.id.btn_font_increase);
            permissionsLayout = findViewById(R.id.cl_permissions);
            inviteFriendLayout = findViewById(R.id.cl_invite_friend);
            selectLanguageLayout = findViewById(R.id.cl_select_language);
            currentLanguageTextView = findViewById(R.id.tv_current_language);
            notificationsLayout = findViewById(R.id.cl_notifications);
            notificationsIcon = findViewById(R.id.iv_config_pref_notifications);
        } catch (Exception e) {
            Log.e(TAG, "Error en initializeViews: " + e.getMessage());
        }
    }

    private void setupListeners() {
        try {
            connectionLayout.setOnClickListener(v -> {
                Intent intent = new Intent(PreferencesPage.this, ConnexionPage.class);
                startActivity(intent);
            });

            decreaseFontButton.setOnClickListener(v -> changeFontSize(false));
            increaseFontButton.setOnClickListener(v -> changeFontSize(true));

            permissionsLayout.setOnClickListener(v -> {
                Intent intent = new Intent(PreferencesPage.this, PermissionsPage.class);
                startActivity(intent);
            });

            inviteFriendLayout.setOnClickListener(v -> {
                copyToClipboard();
                Toast.makeText(this, getString(R.string.clipboard_url_copied), Toast.LENGTH_SHORT).show();
            });

            // Listener para Seleccionar idioma
            selectLanguageLayout.setOnClickListener(v -> showLanguageSelectionDialog());

            // Listener para Notificaciones
            notificationsLayout.setOnClickListener(v -> {
                notificationsEnabled = !notificationsEnabled;
                notificationsIcon.setImageResource(notificationsEnabled ? R.drawable.util_notifications_active_24px : R.drawable.util_notifications_off_24px);
                Toast.makeText(this, notificationsEnabled ? getString(R.string.notifications_on) : getString(R.string.notifications_off), Toast.LENGTH_SHORT).show();
            });
        } catch (Exception e) {
            Log.e(TAG, "Error en setupListeners: " + e.getMessage());
        }
    }

    private void changeFontSize(boolean increase) {
        try {
            if (increase) {
                if (fontScale < 1.0f) {
                    fontScale += FONT_SCALE_STEP;
                    Toast.makeText(this, getString(R.string.font_increase), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.font_max_reached), Toast.LENGTH_SHORT).show();
                }
            } else {
                if (fontScale > 0.5f) {
                    fontScale -= FONT_SCALE_STEP;
                    Toast.makeText(this, getString(R.string.font_decrease), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.font_min_reached), Toast.LENGTH_SHORT).show();
                }
            }
            getResources().getConfiguration().fontScale = fontScale;
            recreate();
        } catch (Exception e) {
            Log.e(TAG, "Error en changeFontSize: " + e.getMessage());
        }
    }

    private void copyToClipboard() {
        try {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(getString(R.string.share_url), getString(R.string.share_url_string));
            clipboard.setPrimaryClip(clip);
        } catch (Exception e) {
            Log.e(TAG, "Error en copyToClipboard: " + e.getMessage());
        }
    }
    
    private void showLanguageSelectionDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.util_preferences_dialog_language_selection, null);
            
            RadioGroup radioGroup = dialogView.findViewById(R.id.rg_languages);
            RadioButton spanishRadioButton = dialogView.findViewById(R.id.rb_spanish);
            RadioButton englishRadioButton = dialogView.findViewById(R.id.rb_english);
            
            // Marcar el idioma actual
            String currentLanguage = getCurrentLocale().getLanguage();
            if (currentLanguage.equals("en")) {
                englishRadioButton.setChecked(true);
            } else {
                spanishRadioButton.setChecked(true);
            }
            
            builder.setView(dialogView)
                    .setTitle(R.string.language_select_title)
                    .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        
                        if (selectedId == R.id.rb_spanish) {
                            changeLanguage("es");
                        } else if (selectedId == R.id.rb_english) {
                            changeLanguage("en");
                        }
                    })
                    .setNegativeButton(R.string.language_cancel, (dialog, id) -> {
                        dialog.dismiss();
                    });
                    
            builder.create().show();
        } catch (Exception e) {
            Log.e(TAG, "Error en showLanguageSelectionDialog: " + e.getMessage());
            Toast.makeText(this, R.string.language_not_available, Toast.LENGTH_SHORT).show();
        }
    }
    
    private void changeLanguage(String languageCode) {
        try {
            Resources resources = getResources();
            Configuration configuration = resources.getConfiguration();
            Locale newLocale = new Locale(languageCode);
            
            configuration.setLocale(newLocale);
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            
            // Guardar la preferencia del idioma
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(LANGUAGE_KEY, languageCode);
            editor.apply();
            
            // Mostrar mensaje de cambio de idioma
            String languageName = languageCode.equals("es") ? 
                    getString(R.string.language_spanish) : getString(R.string.language_english);
            
            Toast.makeText(this, 
                    String.format(getString(R.string.language_changed), languageName), 
                    Toast.LENGTH_SHORT).show();
            
            // Actualizar la interfaz de usuario
            updateLanguageDisplay();
            
            // Recrear la actividad para aplicar los cambios
            recreate();
            
        } catch (Exception e) {
            Log.e(TAG, "Error en changeLanguage: " + e.getMessage());
            Toast.makeText(this, R.string.language_not_available, Toast.LENGTH_SHORT).show();
        }
    }
    
    private void loadSavedLanguage() {
        try {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String languageCode = settings.getString(LANGUAGE_KEY, "es"); // Español por defecto
            
            Resources resources = getResources();
            Configuration configuration = resources.getConfiguration();
            Locale locale = new Locale(languageCode);
            
            configuration.setLocale(locale);
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        } catch (Exception e) {
            Log.e(TAG, "Error en loadSavedLanguage: " + e.getMessage());
        }
    }
    
    private Locale getCurrentLocale() {
        Configuration config = getResources().getConfiguration();
        return config.getLocales().get(0);
    }
    
    private void updateLanguageDisplay() {
        try {
            String currentLanguage = getCurrentLocale().getLanguage();
            if (currentLanguage.equals("en")) {
                currentLanguageTextView.setText(R.string.english);
            } else {
                currentLanguageTextView.setText(R.string.spanish);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error en updateLanguageDisplay: " + e.getMessage());
        }
    }
}
