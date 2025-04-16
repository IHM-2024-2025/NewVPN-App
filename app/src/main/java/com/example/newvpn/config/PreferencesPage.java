package com.example.newvpn.config;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

public class PreferencesPage extends AppCompatActivity {

    private static final String TAG = "PreferencesPage";

    private ConstraintLayout connectionLayout;
    private ImageButton decreaseFontButton;
    private ImageButton increaseFontButton;
    private ConstraintLayout permissionsLayout;
    private ConstraintLayout inviteFriendLayout;
    private ConstraintLayout selectLanguageLayout;
    private ConstraintLayout notificationsLayout;
    private ImageView notificationsIcon;
    private boolean notificationsEnabled = true;

    private float fontScale = 1.0f;
    private static final float FONT_SCALE_STEP = 0.1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_preferences);

        try {
            HeaderHelper.injectHeader(this, R.id.header_container, getString(R.string.config_title));
            MenuHelper.injectMenu(this, R.id.fl_mainpage_menu_container);

            initializeViews();
            setupListeners();
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
            selectLanguageLayout.setOnClickListener(v -> Toast.makeText(this, getString(R.string.not_implemented), Toast.LENGTH_SHORT).show());

            // Listener para Notificaciones
            notificationsLayout.setOnClickListener(v -> {
                notificationsEnabled = !notificationsEnabled;
                notificationsIcon.setImageResource(notificationsEnabled ? R.drawable.notifications_active_24px : R.drawable.notifications_off_24px);
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
            ClipData clip = ClipData.newPlainText("Enlace de invitación", "https://joseleelportfolio.vercel.app");
            clipboard.setPrimaryClip(clip);
        } catch (Exception e) {
            Log.e(TAG, "Error en copyToClipboard: " + e.getMessage());
        }
    }
}