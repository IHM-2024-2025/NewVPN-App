package com.example.newvpn.monetize;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newvpn.R;
import com.example.newvpn.app.AccountPage;
import com.example.newvpn.utils.ButtonsNavigation;
import com.example.newvpn.utils.HeaderHelper;
import com.example.newvpn.utils.MenuHelper;

public class PayPage extends AppCompatActivity {

    // Componentes de la UI
    private Button btnPayCard, btnPayPaypal, btnPaySubmit;
    private LinearLayout llPayCardForm, llPayPaypalForm;
    private RadioGroup rgPayPlans;
    private RadioButton rbPayMonthly, rbPayYearly;
    private TextView tvPayPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monetize_pay);

        // Inicializar componentes de UI
        initializeUI();

        // Configurar Header y Menu
        HeaderHelper.injectHeader(this, R.id.fl_pay_header_container, getString(R.string.pay_title));
        MenuHelper.injectMenu(this, R.id.fl_pay_menu_container);

        // Configurar listeners
        setupListeners();
    }

    private void initializeUI() {
        // Botones de método de pago
        btnPayCard = findViewById(R.id.btn_pay_card);
        btnPayPaypal = findViewById(R.id.btn_pay_paypal);
        btnPaySubmit = findViewById(R.id.btn_pay_submit);

        // Formularios
        llPayCardForm = findViewById(R.id.ll_pay_card_form);
        llPayPaypalForm = findViewById(R.id.ll_pay_paypal_form);

        // Planes y precios
        rgPayPlans = findViewById(R.id.rg_pay_plans);
        rbPayMonthly = findViewById(R.id.rb_pay_monthly);
        rbPayYearly = findViewById(R.id.rb_pay_yearly);
        tvPayPrice = findViewById(R.id.tv_pay_price);
    }

    private void setupListeners() {
        // Botones de método de pago
        btnPayCard.setOnClickListener(v -> {
            llPayCardForm.setVisibility(View.VISIBLE);
            llPayPaypalForm.setVisibility(View.GONE);
            btnPayCard.setAlpha(1.0f);
            btnPayPaypal.setAlpha(0.7f);
        });

        btnPayPaypal.setOnClickListener(v -> {
            llPayCardForm.setVisibility(View.GONE);
            llPayPaypalForm.setVisibility(View.VISIBLE);
            btnPayCard.setAlpha(0.7f);
            btnPayPaypal.setAlpha(1.0f);
        });

        // Cambio de plan
        rgPayPlans.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_pay_monthly) {
                tvPayPrice.setText(getString(R.string.pay_price_monthly));
            } else {
                tvPayPrice.setText(getString(R.string.pay_price_yearly));
            }
        });

        // Botón de pago
        btnPaySubmit.setOnClickListener(v -> showPaymentConfirmation());
    }

    private void showPaymentConfirmation() {
        new AlertDialog.Builder(this)
            .setTitle(getString(R.string.pay_confirm_title))
            .setMessage(getString(R.string.pay_confirm_message))
            .setPositiveButton(getString(R.string.pay_confirm_yes), (dialog, which) -> {
                processPayment();
            })
            .setNegativeButton(getString(R.string.pay_confirm_no), null)
            .show();
    }

    private void processPayment() {
        // Aquí iría la lógica real de procesamiento del pago
        // Por ahora, simularemos un pago exitoso

        Toast.makeText(this, getString(R.string.pay_success), Toast.LENGTH_SHORT).show();

        // Redirigir al perfil del usuario
        ButtonsNavigation.navigateTo(this, AccountPage.class);
    }
}
