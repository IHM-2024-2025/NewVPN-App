package com.example.newvpn.config;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

public class PermissionsPage extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchContacts, switchLocation, switchMicrophone, switchCamera;
    
    // Variables para simular el estado de permisos
    private boolean simulatedContactsPermission;
    private boolean simulatedLocationPermission;
    private boolean simulatedMicrophonePermission;
    private boolean simulatedCameraPermission;
    
    // Lanzadores para solicitar permisos con actualización de variables simuladas
    private final ActivityResultLauncher<String> requestContactsPermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
               simulatedContactsPermission = isGranted;
               handlePermissionResult(switchContacts, isGranted);
            });
            
    private final ActivityResultLauncher<String> requestLocationPermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
               simulatedLocationPermission = isGranted;
               handlePermissionResult(switchLocation, isGranted);
            });
            
    private final ActivityResultLauncher<String> requestMicrophonePermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
               simulatedMicrophonePermission = isGranted;
               handlePermissionResult(switchMicrophone, isGranted);
            });
            
    private final ActivityResultLauncher<String> requestCameraPermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
               simulatedCameraPermission = isGranted;
               handlePermissionResult(switchCamera, isGranted);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_permissions);
        HeaderHelper.injectHeader(this, R.id.header_container, getString(R.string.permissions_title));
        MenuHelper.injectMenu(this, R.id.fl_mainpage_menu_container);
        
        // Inicializar los switches
        switchContacts = findViewById(R.id.switch_contacts);
        switchLocation = findViewById(R.id.switch_location);
        switchMicrophone = findViewById(R.id.switch_microphone);
        switchCamera = findViewById(R.id.switch_camera);
        
        // Inicializar variables simuladas con el estado real inicial
        simulatedContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
        simulatedLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        simulatedMicrophonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        simulatedCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        
        // Configurar los estados iniciales de los switches
        updateSwitchStates();
        
        // Configurar listeners para los switches
        switchContacts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (checkPermission(Manifest.permission.READ_CONTACTS)) {
                    requestContactsPermission.launch(Manifest.permission.READ_CONTACTS);
                }
            } else {
                revokePermission(Manifest.permission.READ_CONTACTS, switchContacts);
            }
        });
        
        switchLocation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                }
            } else {
                revokePermission(Manifest.permission.ACCESS_FINE_LOCATION, switchLocation);
            }
        });
        
        switchMicrophone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (checkPermission(Manifest.permission.RECORD_AUDIO)) {
                    requestMicrophonePermission.launch(Manifest.permission.RECORD_AUDIO);
                }
            } else {
                revokePermission(Manifest.permission.RECORD_AUDIO, switchMicrophone);
            }
        });
        
        switchCamera.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (checkPermission(Manifest.permission.CAMERA)) {
                    requestCameraPermission.launch(Manifest.permission.CAMERA);
                }
            } else {
                revokePermission(Manifest.permission.CAMERA, switchCamera);
            }
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar estados de los switches cuando se vuelve a la actividad
        updateSwitchStates();
    }
    
    private void updateSwitchStates() {
        // Actualiza el estado de los switches según las variables simuladas
        switchContacts.setChecked(simulatedContactsPermission);
        switchLocation.setChecked(simulatedLocationPermission);
        switchMicrophone.setChecked(simulatedMicrophonePermission);
        switchCamera.setChecked(simulatedCameraPermission);
    }
    
    private boolean checkPermission(String permission) {
        // Devuelve el estado simulado en lugar del permiso real
        if (Manifest.permission.READ_CONTACTS.equals(permission)) {
            return !simulatedContactsPermission;
        } else if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {
            return !simulatedLocationPermission;
        } else if (Manifest.permission.RECORD_AUDIO.equals(permission)) {
            return !simulatedMicrophonePermission;
        } else if (Manifest.permission.CAMERA.equals(permission)) {
            return !simulatedCameraPermission;
        }
        return true;
    }
    
    private void handlePermissionResult(@SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchView, boolean isGranted) {
        if (isGranted) {
            Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
            // Ir directamente a la configuración de permisos
            openAppSettings();
            // Revertir el switch ya que el permiso fue denegado
            switchView.setChecked(false);
        }
    }
    
    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
    
    // Método para simular la revocación del permiso
    private void revokePermission(String permission, @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchView) {
        Toast.makeText(this, R.string.perm_deleted, Toast.LENGTH_SHORT).show();
        // Actualizar la variable simulada según el permiso revocado
        if (Manifest.permission.READ_CONTACTS.equals(permission)) {
            simulatedContactsPermission = false;
        } else if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {
            simulatedLocationPermission = false;
        } else if (Manifest.permission.RECORD_AUDIO.equals(permission)) {
            simulatedMicrophonePermission = false;
        } else if (Manifest.permission.CAMERA.equals(permission)) {
            simulatedCameraPermission = false;
        }
        switchView.setChecked(false);
    }
}
