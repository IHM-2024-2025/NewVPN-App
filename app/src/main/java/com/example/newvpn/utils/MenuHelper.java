package com.example.newvpn.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.newvpn.app.AccountPage;
import com.example.newvpn.app.CountriesPage;
import com.example.newvpn.app.MainPage;
import com.example.newvpn.R;
import com.example.newvpn.config.PreferencesPage;

public class MenuHelper {

    // Variable estática para almacenar el último botón activo
    private static int lastActiveButtonId = -1;
    
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
        
        // Reposicionar la marca de la página activa
        repositionMarkPage(activity, menuView);
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
    
    /**
     * Reposiciona la ImageView de mark page para indicar la página activa.
     *
     * @param activity La actividad actual.
     * @param menuView La vista del menú (ConstraintLayout).
     */
    private static void repositionMarkPage(Activity activity, View menuView) {
        // Asegurarse de que el layout es un ConstraintLayout
        if (!(menuView instanceof ConstraintLayout)) {
            return;
        }
        ConstraintLayout layout = (ConstraintLayout) menuView;
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);
        
        int activeButtonId = -1;
        if (activity instanceof MainPage) {
            activeButtonId = R.id.iv_util_menu_house;
        } else if (activity instanceof CountriesPage) {
            activeButtonId = R.id.iv_util_menu_globe;
        } else if (activity instanceof PreferencesPage) {
            activeButtonId = R.id.iv_util_menu_settings;
        } else if (activity instanceof AccountPage) {
            activeButtonId = R.id.iv_util_menu_profile;
        }
        
        if (activeButtonId != -1) {
            // Actualiza el último botón activo
            lastActiveButtonId = activeButtonId;
        } else if (lastActiveButtonId != -1) {
            // Si no se encontró la página, usa el último botón activo
            activeButtonId = lastActiveButtonId;
        } else {
            // Si no hay un último botón activo registrado, salir
            return;
        }
        
        // Reestablecer las constraints de la marca
        constraintSet.clear(R.id.iv_util_mark_page, ConstraintSet.START);
        constraintSet.clear(R.id.iv_util_mark_page, ConstraintSet.END);
        
        // Conectar la marca a los bordes del botón activo para centrarla
        constraintSet.connect(R.id.iv_util_mark_page, ConstraintSet.START, activeButtonId, ConstraintSet.START);
        constraintSet.connect(R.id.iv_util_mark_page, ConstraintSet.END, activeButtonId, ConstraintSet.END);
        
        // ...otros ajustes de constraints si es necesario...
        
        constraintSet.applyTo(layout);
    }
}
