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
            getSupportActionBar().setTitle("Dimensionamento de Gerador");
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
        String[] voltages = {"127 V", "220 V", "380 V"};
        ArrayAdapter<String> voltageAdapter = new ArrayAdapter<>(
            this, 
            android.R.layout.simple_spinner_item, 
            voltages
        );
        voltageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVoltage.setAdapter(voltageAdapter);
        spinnerVoltage.setSelection(1); // Padrão: 220V
        
        // Spinner de tipo de carga
        String[] loadTypes = {
            "Resistiva (iluminação, aquecedores)",
            "Indutiva Leve (ar-condicionado, geladeiras)",
            "Indutiva Pesada (motores, bombas)",
            "Mista (combinação de cargas)"
        };
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
            edtLoadKw.setError("Informe a carga total");
            edtLoadKw.requestFocus();
            return;
        }
        
        double loadKw;
        try {
            loadKw = Double.parseDouble(loadStr);
            if (loadKw <= 0) {
                edtLoadKw.setError("Carga deve ser positiva");
                edtLoadKw.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            edtLoadKw.setError("Valor inválido");
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
            txtResult.setText("Erro: " + e.getMessage());
        }
    }
    
    private void displayResult(GeneratorSizingService.Result result, 
                              double originalLoadKw,
                              GeneratorSizingService.LoadType loadType) {
        StringBuilder sb = new StringBuilder();
        sb.append("═══ RESULTADO DO DIMENSIONAMENTO ═══\n\n");
        
        sb.append(String.format("⚡ Gerador Recomendado: %.1f kVA\n\n", result.nominalPowerKva));
        
        sb.append("┌─ Dados da Carga:\n");
        sb.append(String.format("│  Potência Ativa: %.2f kW\n", originalLoadKw));
        sb.append(String.format("│  Fator de Potência: %.2f\n", loadType.typicalPowerFactor));
        sb.append(String.format("│  Potência Aparente: %.2f kVA\n", result.activePowerKw / loadType.typicalPowerFactor));
        sb.append("└─\n\n");
        
        sb.append("┌─ Especificações do Gerador:\n");
        sb.append(String.format("│  Potência Nominal: %.1f kVA\n", result.nominalPowerKva));
        sb.append(String.format("│  Corrente Máxima: %.1f A\n", result.maxCurrent));
        sb.append(String.format("│  Margem de Segurança: %.1f%%\n", result.reserveMargin));
        sb.append("└─\n\n");
        
        // Estimativa de consumo a 75% de carga
        double fuelConsumption = GeneratorSizingService.estimateFuelConsumption(originalLoadKw, 0.75);
        sb.append("┌─ Consumo Estimado (75% carga):\n");
        sb.append(String.format("│  Diesel: %.2f L/h\n", fuelConsumption));
        sb.append("└─\n\n");
        
        sb.append("💡 OBSERVAÇÕES:\n");
        sb.append("• Valores baseados em NBR 5410\n");
        sb.append("• Considerar partida de motores\n");
        sb.append("• Verificar autonomia do tanque\n");
        
        txtResult.setVisibility(View.VISIBLE);
        txtResult.setText(sb.toString());
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
