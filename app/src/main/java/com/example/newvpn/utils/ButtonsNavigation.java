package com.example.newvpn.utils;

import android.content.Context;
import android.content.Intent;

public class ButtonsNavigation {
    
    /**
     * Navigate to a specific activity.
     *
     * @param currentContext The current activity/page.
     * @param destinationClass The destination activity/page.
     */
    public static void navigateTo(Context currentContext, Class<?> destinationClass) {
        Intent intent = new Intent(currentContext, destinationClass);
        currentContext.startActivity(intent);
    }
}
