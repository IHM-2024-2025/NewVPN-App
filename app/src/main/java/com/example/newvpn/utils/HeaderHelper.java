package com.example.newvpn.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newvpn.R;

public class HeaderHelper {
    
    /**
     * Inyecta el header en una actividad y establece el título.
     *
     * @param activity    La actividad donde se inyectará el header
     * @param containerId El ID del contenedor donde se colocará el header
     * @param title       El título a mostrar en el header
     */
    public static void injectHeader(Activity activity, int containerId, String title) {
        FrameLayout container = activity.findViewById(containerId);
        if (container == null) {
            return;
        }
        
        // Inflar el layout del header
        View headerView = LayoutInflater.from(activity).inflate(
                R.layout.util_app_header, container, false);
        
        // Establecer el título
        TextView titleTextView = headerView.findViewById(R.id.tv_util_header_title);
        if (titleTextView != null && title != null) {
            titleTextView.setText(title);
        }
        
        // Añadir el header al contenedor
        container.removeAllViews();
        container.addView(headerView);

    }

    /**
     * Inyecta solo el logo del header en una actividad.
     *
     * @param activity    La actividad donde se inyectará el logo
     * @param containerId El ID del contenedor donde se colocará el logo
     */
    public static void injectHeaderLogo(Activity activity, int containerId) {
        FrameLayout container = activity.findViewById(containerId);
        if (container == null) {
            return;
        }

        // Crear un ImageView para el logo
        ImageView logoImageView = new ImageView(activity);
        logoImageView.setImageResource(R.drawable.logo);
        logoImageView.setContentDescription(activity.getString(R.string.app_name));

        // Configurar tamaño a 95dp x 89dp como en el layout original
        int width = (int) (95 * activity.getResources().getDisplayMetrics().density);
        int height = (int) (89 * activity.getResources().getDisplayMetrics().density);

        // Posicionar el logo en la esquina superior derecha
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.gravity = Gravity.TOP | Gravity.END;
        logoImageView.setLayoutParams(params);

        // Añadir el logo al contenedor
        container.removeAllViews();
        container.addView(logoImageView);
    }
}
