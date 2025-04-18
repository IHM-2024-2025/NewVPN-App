package com.example.newvpn.config;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConnexionPage extends AppCompatActivity {

    private static final String PREFS_NAME = "VpnConnectionPrefs";
    private static final String KEY_CONNECTION_TYPE = "connection_type";
    private static final String KEY_DNS = "custom_dns";
    private static final String KEY_AD_BLOCKER = "ad_blocker";
    private static final String KEY_TRACKER_BLOCKER = "tracker_blocker";
    private static final String KEY_SPLIT_ROUTING = "split_routing";
    private static final String KEY_SPLIT_ROUTING_APPS = "split_routing_apps";

    private TextInputLayout tilDns;
    private TextInputEditText etDns;
    private Spinner spinnerConnectionType;
    private SwitchCompat switchAdBlocker;
    private SwitchCompat switchTrackerBlocker;
    private SwitchCompat switchSplitRouting;
    private ConstraintLayout containerSplitRouting;
    private View containerAppList;
    private RecyclerView rvApps;

    private static final int AUTO_POSITION = 0;
    private static final int MANUAL_POSITION = 1;
    private static final int ANIMATION_DURATION = 300; // 300ms

    private AppAdapter appAdapter;
    private final List<AppInfo> installedApps = new ArrayList<>();
    private Set<String> selectedApps = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_connexion);
        HeaderHelper.injectHeader(this, R.id.header_container, getString(R.string.connexion_title));
        MenuHelper.injectMenu(this, R.id.fl_mainpage_menu_container);

        // Inicializar componentes
        initializeViews();
        setupConnectionTypeSpinner();
        setupSplitRoutingInfo();
        setupSwitchListeners();
        setupEditTextListener();
        setupAppsList();

        // Cargar configuración guardada
        loadSavedConfig();
    }

    private void initializeViews() {
        spinnerConnectionType = findViewById(R.id.spinner_connection_type);
        tilDns = findViewById(R.id.til_dns);
        etDns = findViewById(R.id.et_dns);
        switchAdBlocker = findViewById(R.id.switch_ad_blocker);
        switchTrackerBlocker = findViewById(R.id.switch_tracker_blocker);
        switchSplitRouting = findViewById(R.id.switch_split_routing);
        containerSplitRouting = findViewById(R.id.container_split_routing);
        containerAppList = findViewById(R.id.container_app_list);
        rvApps = findViewById(R.id.rv_apps);
        View containerAdBlocker = findViewById(R.id.container_ad_blocker);
        View containerTrackerBlocker = findViewById(R.id.container_tracker_blocker);
        containerAdBlocker.setOnClickListener(v -> {
            switchAdBlocker.setChecked(!switchAdBlocker.isChecked());
        });
        containerTrackerBlocker.setOnClickListener(v -> {
            switchTrackerBlocker.setChecked(!switchTrackerBlocker.isChecked());
        });
    }

    private void setupConnectionTypeSpinner() {
        // Crear adapter para el spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{getString(R.string.connection_auto), getString(R.string.connection_manual)}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConnectionType.setAdapter(adapter);

        // Configurar listener para cambios de selección
        spinnerConnectionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == MANUAL_POSITION) {
                    showDnsInput();

                    // Mostrar el teclado virtual automáticamente
                    etDns.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etDns, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    hideDnsInput();
                }
                saveConfiguration();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hideDnsInput();
            }
        });
    }

    private void setupSplitRoutingInfo() {
        containerSplitRouting.setOnClickListener(v -> showSplitRoutingInfo());
    }

    private void setupSwitchListeners() {
        switchAdBlocker.setOnCheckedChangeListener((buttonView, isChecked) -> saveConfiguration());
        switchTrackerBlocker.setOnCheckedChangeListener((buttonView, isChecked) -> saveConfiguration());
        switchSplitRouting.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                scanInstalledApps();
                showAppList();
            } else {
                hideAppList();
            }
            saveConfiguration();
        });
    }

    private void setupEditTextListener() {
        etDns.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                saveConfiguration();
            }
        });
    }

    private void setupAppsList() {
        rvApps.setLayoutManager(new LinearLayoutManager(this));
        appAdapter = new AppAdapter();
        rvApps.setAdapter(appAdapter);
    }

    private void showDnsInput() {
        if (tilDns.getVisibility() == View.VISIBLE) {
            return;
        }

        tilDns.setVisibility(View.VISIBLE);
        tilDns.setAlpha(0f);
        tilDns.animate()
                .alpha(1f)
                .setDuration(ANIMATION_DURATION)
                .setListener(null);
    }

    private void hideDnsInput() {
        if (tilDns.getVisibility() != View.VISIBLE) {
            return;
        }

        tilDns.animate()
                .alpha(0f)
                .setDuration(ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tilDns.setVisibility(View.GONE);
                    }
                });
    }

    private void showAppList() {
        if (containerAppList.getVisibility() == View.VISIBLE) {
            return;
        }

        containerAppList.setVisibility(View.VISIBLE);
        containerAppList.setAlpha(0f);
        containerAppList.animate()
                .alpha(1f)
                .setDuration(ANIMATION_DURATION)
                .setListener(null);
    }

    private void hideAppList() {
        if (containerAppList.getVisibility() != View.VISIBLE) {
            return;
        }

        containerAppList.animate()
                .alpha(0f)
                .setDuration(ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        containerAppList.setVisibility(View.GONE);
                    }
                });
    }

    private void showSplitRoutingInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.split_routing_title))
                .setMessage(getString(R.string.split_routing_message))
                .setPositiveButton(getString(R.string.understood), (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void scanInstalledApps() {
        if (!installedApps.isEmpty()) {
            return; // Ya se escanearon las apps previamente
        }

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo appInfo : packages) {
            // Omitir aplicaciones del sistema sin launcher
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                if (!isAppLaunchable(pm, appInfo.packageName)) {
                    continue;
                }
            }

            String appName = appInfo.loadLabel(pm).toString();
            Drawable icon = appInfo.loadIcon(pm);
            String packageName = appInfo.packageName;

            installedApps.add(new AppInfo(appName, icon, packageName));
        }

        // Ordenar aplicaciones por nombre
        installedApps.sort((app1, app2) ->
                app1.getName().compareToIgnoreCase(app2.getName()));

        appAdapter.notifyDataSetChanged();
    }

    private boolean isAppLaunchable(PackageManager pm, String packageName) {
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        return intent != null;
    }

    private void saveConfiguration() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Guardar tipo de conexión y DNS
        int connectionType = spinnerConnectionType.getSelectedItemPosition();
        editor.putInt(KEY_CONNECTION_TYPE, connectionType);

        if (connectionType == MANUAL_POSITION && etDns.getText() != null) {
            editor.putString(KEY_DNS, etDns.getText().toString().trim());
        } else {
            editor.remove(KEY_DNS);
        }

        // Guardar estado de los switches
        editor.putBoolean(KEY_AD_BLOCKER, switchAdBlocker.isChecked());
        editor.putBoolean(KEY_TRACKER_BLOCKER, switchTrackerBlocker.isChecked());
        editor.putBoolean(KEY_SPLIT_ROUTING, switchSplitRouting.isChecked());

        // Guardar aplicaciones seleccionadas para split routing
        editor.putStringSet(KEY_SPLIT_ROUTING_APPS, selectedApps);

        // Aplicar cambios
        editor.apply();
    }

    private void loadSavedConfig() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Cargar tipo de conexión
        int connectionType = prefs.getInt(KEY_CONNECTION_TYPE, AUTO_POSITION);
        spinnerConnectionType.setSelection(connectionType);

        // Cargar DNS personalizado si existe
        if (connectionType == MANUAL_POSITION) {
            String dns = prefs.getString(KEY_DNS, "");
            etDns.setText(dns);
            showDnsInput();
        }

        // Cargar estado de los switches
        switchAdBlocker.setChecked(prefs.getBoolean(KEY_AD_BLOCKER, true));
        switchTrackerBlocker.setChecked(prefs.getBoolean(KEY_TRACKER_BLOCKER, true));
        switchSplitRouting.setChecked(prefs.getBoolean(KEY_SPLIT_ROUTING, false));

        // Cargar aplicaciones seleccionadas
        selectedApps = new HashSet<>(prefs.getStringSet(KEY_SPLIT_ROUTING_APPS, new HashSet<>()));

        // Si el split routing está activado, mostrar las aplicaciones
        if (switchSplitRouting.isChecked()) {
            scanInstalledApps();
            showAppList();
        }
    }

    // Clase para representar información de una aplicación
    private static class AppInfo {
        private final String name;
        private final Drawable icon;
        private final String packageName;

        AppInfo(String name, Drawable icon, String packageName) {
            this.name = name;
            this.icon = icon;
            this.packageName = packageName;
        }

        public String getName() {
            return name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public String getPackageName() {
            return packageName;
        }
    }

    // Adapter para la lista de aplicaciones
    private class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {

        @NonNull
        @Override
        public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.util_config_recyclerview, parent, false);
            return new AppViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
            AppInfo app = installedApps.get(position);
            holder.tvAppName.setText(app.getName());
            holder.ivAppIcon.setImageDrawable(app.getIcon());
            holder.switchApp.setChecked(selectedApps.contains(app.getPackageName()));

            holder.switchApp.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedApps.add(app.getPackageName());
                } else {
                    selectedApps.remove(app.getPackageName());
                }
                saveConfiguration();
            });
        }

        @Override
        public int getItemCount() {
            return installedApps.size();
        }

        class AppViewHolder extends RecyclerView.ViewHolder {
            ImageView ivAppIcon;
            TextView tvAppName;
            SwitchCompat switchApp;

            AppViewHolder(View itemView) {
                super(itemView);
                ivAppIcon = itemView.findViewById(R.id.iv_app_icon);
                tvAppName = itemView.findViewById(R.id.tv_app_name);
                switchApp = itemView.findViewById(R.id.switch_app);
            }
        }
    }
}
