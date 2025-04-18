package com.example.newvpn.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Toast;

import com.example.newvpn.app.AccountPage;
import com.example.newvpn.app.CountriesPage;
import com.example.newvpn.app.MainPage;
import com.example.newvpn.R;
import com.example.newvpn.config.PreferencesPage;

public class MenuHelper {
    
    /**
     * Inyecta el menú de navegación en una actividad.
     *
     * @param activity    La actividad donde se inyectará el menú
     * @param containerId El ID del contenedor donde se colocará el menú
     */
    public static void injectMenu(Activity activity, int containerId) {
        FrameLayout container = activity.findViewById(containerId);
        if (container == null) {
            return;
        }
        
        // Inflar el layout del menú
        View menuView = LayoutInflater.from(activity).inflate(
                R.layout.util_app_menu, container, false);
        
        // Configurar listeners para los botones del menú
        setupMenuListeners(activity, menuView);
        
        // Aplicar un margen inferior si es necesario
        MarginLayoutParams params = new MarginLayoutParams(
                MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT);
        params.bottomMargin = (int)(16 * activity.getResources().getDisplayMetrics().density); // 16dp
        menuView.setLayoutParams(params);
        
        // Añadir el menú al contenedor
        container.removeAllViews();
        container.addView(menuView);

    }
    
    /**
     * Configura los listeners para los botones del menú.
     * 
     * @param activity La actividad actual
     * @param menuView La vista del menú
     */
    private static void setupMenuListeners(Activity activity, View menuView) {
        // Botón Home
        ImageView homeButton = menuView.findViewById(R.id.iv_util_menu_house);
        homeButton.setOnClickListener(v -> {
            if (activity instanceof MainPage) {
                Toast.makeText(activity, "Ya estás en la página principal", Toast.LENGTH_SHORT).show();
            } else {
                ButtonsNavigation.navigateTo(activity, MainPage.class);
            }
        });
        
        // Botón Países
        ImageView globeButton = menuView.findViewById(R.id.iv_util_menu_globe);
        globeButton.setOnClickListener(v -> {
            if (activity instanceof CountriesPage) {
                Toast.makeText(activity, "Ya estás en la página de países", Toast.LENGTH_SHORT).show();
            } else {
                ButtonsNavigation.navigateTo(activity, CountriesPage.class);
            }
        });
        
        // Botón Configuración
        ImageView settingsButton = menuView.findViewById(R.id.iv_util_menu_settings);
        settingsButton.setOnClickListener(v -> {
            if (activity instanceof PreferencesPage) {
                Toast.makeText(activity, "Ya estás en la página de configuración", Toast.LENGTH_SHORT).show();
            } else {
                ButtonsNavigation.navigateTo(activity, PreferencesPage.class);
            }
        });
        
        // Botón Perfil
        ImageView profileButton = menuView.findViewById(R.id.iv_util_menu_profile);
        profileButton.setOnClickListener(v -> {
            if (activity instanceof AccountPage) {
                Toast.makeText(activity, "Ya estás en la página de perfil", Toast.LENGTH_SHORT).show();
            } else {
                ButtonsNavigation.navigateTo(activity, AccountPage.class);
            }
        });
    }
}
