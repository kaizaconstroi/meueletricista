package app.kizen.eletricista;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import app.kizen.eletricista.domain.GeneratorSizingService;

/**
 * Activity para dimensionamento de geradores elétricos.
 */
public class GeradorActivity extends AppCompatActivity {
    
    private EditText edtLoadKw;
    private Spinner spinnerVoltage;
    private Spinner spinnerLoadType;
    private RadioButton rbMonofasico;
    private RadioButton rbTrifasico;
    private Button btnCalculate;
    private TextView txtResult;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerador);
        
        // Configurar action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.generator_toolbar_title);
        }
        
        initViews();
        setupSpinners();
        setupListeners();
    }
    
    private void initViews() {
        edtLoadKw = findViewById(R.id.edt_load_kw);
        spinnerVoltage = findViewById(R.id.spinner_voltage);
        spinnerLoadType = findViewById(R.id.spinner_load_type);
        rbMonofasico = findViewById(R.id.rb_monofasico);
        rbTrifasico = findViewById(R.id.rb_trifasico);
        btnCalculate = findViewById(R.id.btn_calculate);
        txtResult = findViewById(R.id.txt_result);
    }
    
    private void setupSpinners() {
        // Spinner de tensão
        String[] voltages = getResources().getStringArray(R.array.generator_voltages);
        ArrayAdapter<String> voltageAdapter = new ArrayAdapter<>(
            this, 
            android.R.layout.simple_spinner_item, 
            voltages
        );
        voltageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVoltage.setAdapter(voltageAdapter);
        spinnerVoltage.setSelection(1); // Padrão: 220V
        
        // Spinner de tipo de carga
        String[] loadTypes = getResources().getStringArray(R.array.generator_load_types);
        ArrayAdapter<String> loadTypeAdapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_item,
            loadTypes
        );
        loadTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoadType.setAdapter(loadTypeAdapter);
        spinnerLoadType.setSelection(3); // Padrão: Mista
    }
    
    private void setupListeners() {
        btnCalculate.setOnClickListener(v -> calculateGenerator());
    }
    
    private void calculateGenerator() {
        // Validar entrada de carga
        String loadStr = edtLoadKw.getText().toString().trim();
        if (loadStr.isEmpty()) {
            edtLoadKw.setError(getString(R.string.generator_error_inform_total_load));
            edtLoadKw.requestFocus();
            return;
        }
        
        double loadKw;
        try {
            loadKw = Double.parseDouble(loadStr);
            if (loadKw <= 0) {
                edtLoadKw.setError(getString(R.string.generator_error_load_positive));
                edtLoadKw.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            edtLoadKw.setError(getString(R.string.generator_error_invalid_value));
            edtLoadKw.requestFocus();
            return;
        }
        
        // Obter tensão selecionada
        String voltageStr = spinnerVoltage.getSelectedItem().toString().split(" ")[0];
        double voltage = Double.parseDouble(voltageStr);
        
        // Obter tipo de carga
        int loadTypeIndex = spinnerLoadType.getSelectedItemPosition();
        GeneratorSizingService.LoadType loadType;
        switch (loadTypeIndex) {
            case 0: loadType = GeneratorSizingService.LoadType.RESISTIVA; break;
            case 1: loadType = GeneratorSizingService.LoadType.INDUTIVA_LEVE; break;
            case 2: loadType = GeneratorSizingService.LoadType.INDUTIVA_PESADA; break;
            default: loadType = GeneratorSizingService.LoadType.MISTA; break;
        }
        
        // Verificar sistema (mono/trifásico)
        boolean isThreePhase = rbTrifasico.isChecked();
        
        // Calcular dimensionamento
        try {
            GeneratorSizingService.Result result = GeneratorSizingService.size(
                loadKw, 
                voltage, 
                loadType, 
                isThreePhase
            );
            
            displayResult(result, loadKw, loadType);
        } catch (IllegalArgumentException e) {
            txtResult.setVisibility(View.VISIBLE);
            txtResult.setText(getString(R.string.generator_error_prefix, e.getMessage()));
        }
    }
    
    private void displayResult(GeneratorSizingService.Result result, 
                              double originalLoadKw,
                              GeneratorSizingService.LoadType loadType) {
        double fuelConsumption = GeneratorSizingService.estimateFuelConsumption(originalLoadKw, 0.75);

        String resultText = getString(
            R.string.generator_result_template,
            result.nominalPowerKva,
            originalLoadKw,
            loadType.typicalPowerFactor,
            result.activePowerKw / loadType.typicalPowerFactor,
            result.nominalPowerKva,
            result.maxCurrent,
            result.reserveMargin,
            fuelConsumption
        );

        txtResult.setVisibility(View.VISIBLE);
        txtResult.setText(resultText);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
