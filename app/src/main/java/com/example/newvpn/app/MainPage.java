package com.example.newvpn.app;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

import java.util.Random;

public class MainPage extends AppCompatActivity {

    private boolean isVpnOn = false;
    private View innerCircle;
    private TextView uploadSpeedTextView;
    private TextView downloadSpeedTextView;
    private Handler speedUpdateHandler;
    private Random random;
    private Runnable speedUpdateRunnable;
    private static final int MIN_SPEED = 30;
    private static final int MAX_SPEED = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_mainpage);

        // Inyectar el logo en su contenedor específico
        HeaderHelper.injectHeaderLogo(this, R.id.fl_mainpage_logo_container);

        // Inyectar el menú en su contenedor
        MenuHelper.injectMenu(this, R.id.fl_mainpage_menu_container);
        innerCircle = findViewById(R.id.innerCircle);
        FrameLayout vpnButton = findViewById(R.id.vpnButton);

        // Obtener referencias a los TextViews de velocidad
        uploadSpeedTextView = findViewById(R.id.tv_mainpage_upload_num);
        downloadSpeedTextView = findViewById(R.id.tv_mainpage_download_num);

        // Inicializar el generador de números aleatorios
        random = new Random();

        // Establecer valores iniciales en 0 (VPN desactivada por defecto)
        uploadSpeedTextView.setText("0");
        downloadSpeedTextView.setText("0");

        // Configurar el Handler y Runnable para actualizar las velocidades cada segundo
        speedUpdateHandler = new Handler();
        speedUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                if (isVpnOn) {
                    // Generar velocidades aleatorias entre MIN_SPEED y MAX_SPEED
                    int uploadSpeed = MIN_SPEED + random.nextInt(MAX_SPEED - MIN_SPEED + 1);
                    int downloadSpeed = MIN_SPEED + random.nextInt(MAX_SPEED - MIN_SPEED + 1);

                    // Actualizar los TextViews con las nuevas velocidades
                    uploadSpeedTextView.setText(String.valueOf(uploadSpeed));
                    downloadSpeedTextView.setText(String.valueOf(downloadSpeed));
                } else {
                    // Si la VPN está apagada, mostrar 0 KB/s
                    uploadSpeedTextView.setText("0");
                    downloadSpeedTextView.setText("0");
                }

                // Programar la siguiente actualización en 1 segundo
                speedUpdateHandler.postDelayed(this, 1000);
            }
        };

        vpnButton.setOnClickListener(v -> {
            isVpnOn = !isVpnOn;
            if (isVpnOn) {
                innerCircle.setBackgroundResource(R.drawable.circle_green);
                Toast.makeText(this, R.string.vpn_on, Toast.LENGTH_SHORT).show();
                // Iniciar inmediatamente las actualizaciones con valores aleatorios
                updateSpeeds();
            } else {
                innerCircle.setBackgroundResource(R.drawable.circle_dark);
                Toast.makeText(this, R.string.vpn_off, Toast.LENGTH_SHORT).show();
                // Establecer inmediatamente las velocidades a 0
                uploadSpeedTextView.setText("0");
                downloadSpeedTextView.setText("0");
            }
        });
    }

    // Método para actualizar las velocidades de forma inmediata
    private void updateSpeeds() {
        if (isVpnOn) {
            int uploadSpeed = MIN_SPEED + random.nextInt(MAX_SPEED - MIN_SPEED + 1);
            int downloadSpeed = MIN_SPEED + random.nextInt(MAX_SPEED - MIN_SPEED + 1);
            uploadSpeedTextView.setText(String.valueOf(uploadSpeed));
            downloadSpeedTextView.setText(String.valueOf(downloadSpeed));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Iniciar las actualizaciones cuando la actividad esté visible
        speedUpdateHandler.post(speedUpdateRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Detener las actualizaciones cuando la actividad no esté visible
        speedUpdateHandler.removeCallbacks(speedUpdateRunnable);
    }
}
