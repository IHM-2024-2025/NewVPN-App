package com.example.newvpn.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.newvpn.R;

public class HeaderHelper {
    
    /**
     * Inyecta el header en una actividad y establece el título.
     * 
     * @param activity La actividad donde se inyectará el header
     * @param containerId El ID del contenedor donde se colocará el header
     * @param title El título a mostrar en el header
     * @return La vista del header inyectado
     */
    public static View injectHeader(Activity activity, int containerId, String title) {
        FrameLayout container = activity.findViewById(containerId);
        if (container == null) {
            return null;
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
        
        return headerView;
    }
    
    /**
     * Actualiza el título del header.
     * 
     * @param headerView La vista del header
     * @param title El nuevo título
     */
    public static void setHeaderTitle(View headerView, String title) {
        if (headerView == null) return;
        
        TextView titleTextView = headerView.findViewById(R.id.tv_util_header_title);
        if (titleTextView != null && title != null) {
            titleTextView.setText(title);
        }
    }
}
