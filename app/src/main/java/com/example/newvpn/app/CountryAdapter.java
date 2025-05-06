package com.example.newvpn.app;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newvpn.R;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private final List<CountriesPage.Country> countries;
    private final OnCountryClickListener listener;
    private String selectedCountry;

    public interface OnCountryClickListener {
        void onCountryClick(String countryName);
    }

    public CountryAdapter(List<CountriesPage.Country> countries, OnCountryClickListener listener, String selectedCountry) {
        this.countries = countries;
        this.listener = listener;
        this.selectedCountry = selectedCountry;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelectedCountry(String countryName) {
        this.selectedCountry = countryName;
        notifyDataSetChanged();
    }


    @NonNull
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.util_item_country, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        CountriesPage.Country country = countries.get(position);
        holder.countryName.setText(country.getName());
        holder.countryFlag.setImageResource(country.getFlagResourceId());

        // Establecer imagen de nivel de señal según el valor de signalLevel
        holder.connectionQuality.setImageResource(getSignalDrawable(country.getSignalLevel()));

        // Marcar el RadioButton si es el país seleccionado
        holder.countryRadioButton.setChecked(country.getName().equals(selectedCountry));

        holder.itemView.setOnClickListener(v -> {
            listener.onCountryClick(country.getName());
        });
    }

    private int getSignalDrawable(int signalLevel) {
        switch (signalLevel) {
            case 2:
                return R.drawable.countries_signal_medium;
            case 3:
                return R.drawable.countries_signal_good;
            case 4:
                return R.drawable.countries_signal_excellent;
            default:
                return R.drawable.countries_signal_low;
        }
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {
        ImageView countryFlag;
        TextView countryName;
        RadioButton countryRadioButton;
        ImageView connectionQuality;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            countryFlag = itemView.findViewById(R.id.iv_country_flag);
            countryName = itemView.findViewById(R.id.tv_country_name);
            countryRadioButton = itemView.findViewById(R.id.rb_country_selected);
            connectionQuality = itemView.findViewById(R.id.iv_connection_quality);
        }

    }
}
