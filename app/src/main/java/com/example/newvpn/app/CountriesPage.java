package com.example.newvpn.app;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newvpn.R;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CountriesPage extends AppCompatActivity implements CountryAdapter.OnCountryClickListener {

    private static final String TAG = "CountriesPage";
    private RecyclerView countriesRecyclerView;
    private CountryAdapter countryAdapter;
    private List<Country> countries;
    private String currentCountry = "España"; // País por defecto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_countries);
        
        try {
            // Inyectar el header

            // Inyectar el menú
            MenuHelper.injectMenu(this, R.id.fl_countries_menu_container);
            
            // Inicializar la lista de países
            initCountriesList();
            
            // Configurar el RecyclerView
            countriesRecyclerView = findViewById(R.id.rv_countries_list);
            countriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            countryAdapter = new CountryAdapter(countries, this, currentCountry);
            countriesRecyclerView.setAdapter(countryAdapter);
        } catch (Exception e) {
            Log.e(TAG, "Error en onCreate: " + e.getMessage());
        }
    }
    
    private void initCountriesList() {
        countries = new ArrayList<>();
        Random random = new Random();
        
        // Añadir los países según los criterios de aceptación
        countries.add(new Country("Alemania", R.drawable.country_germany, getRandomSignalLevel(random)));
        countries.add(new Country("Australia", R.drawable.country_australia, getRandomSignalLevel(random)));
        countries.add(new Country("Canadá", R.drawable.country_canada, getRandomSignalLevel(random)));
        countries.add(new Country("China", R.drawable.country_china, getRandomSignalLevel(random)));
        countries.add(new Country("España", R.drawable.country_spain, 4)); // Siempre conexión máxima (4)
        countries.add(new Country("EE. UU", R.drawable.country_usa, getRandomSignalLevel(random)));
        countries.add(new Country("India", R.drawable.country_india, getRandomSignalLevel(random)));
        countries.add(new Country("Japón", R.drawable.country_japan, getRandomSignalLevel(random)));
        countries.add(new Country("Rusia", R.drawable.country_rusia, getRandomSignalLevel(random)));
        countries.add(new Country("Reino Unido", R.drawable.country_ingland, getRandomSignalLevel(random)));
    }
    
    // Método para generar un nivel de señal aleatorio (1-3)
    private int getRandomSignalLevel(Random random) {
        return random.nextInt(3) + 1; // Valores entre 1 y 3
    }
    
    @Override
    public void onCountryClick(String countryName) {
        // Mostrar un mensaje cuando el usuario selecciona un país

        // Actualizar el país seleccionado
        currentCountry = countryName;
        countryAdapter.setSelectedCountry(countryName);
        

    }
    

    
    // Clase para almacenar datos de país
    public static class Country {
        private String name;
        private int flagResourceId;
        private int signalLevel; // 1: baja, 2: media, 3: buena, 4: excelente
        
        public Country(String name, int flagResourceId) {
            this(name, flagResourceId, 1);
        }
        
        public Country(String name, int flagResourceId, int signalLevel) {
            this.name = name;
            this.flagResourceId = flagResourceId;
            this.signalLevel = signalLevel;
        }
        
        public String getName() {
            return name;
        }
        
        public int getFlagResourceId() {
            return flagResourceId;
        }
        
        public int getSignalLevel() {
            return signalLevel;
        }
    }
}
