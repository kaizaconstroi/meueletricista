package app.kizen.eletricista.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

import app.kizen.eletricista.R;

public class LightingSizingActivity extends AppCompatActivity {
    private EditText areaInput;
    private TextView resultadoIluminacao;

    private Spinner ambienteIluminacaoSpinner;
    private EditText areaDesignInput;
    private EditText luxInput;
    private EditText alturaTetoInput;
    private Spinner refletanciaSpinner;
    private EditText lumensLampadaInput;
    private TextView resultadoProjeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lighting_sizing);

        Toolbar toolbar = findViewById(R.id.toolbar_lighting_sizing);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.lighting_toolbar_title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        areaInput = findViewById(R.id.input_area);
        resultadoIluminacao = findViewById(R.id.text_resultado_iluminacao);
        MaterialButton btnCalcIluminacao = findViewById(R.id.btn_calcular_iluminacao);
        btnCalcIluminacao.setOnClickListener(this::calcularIluminacao);

        ambienteIluminacaoSpinner = findViewById(R.id.spinner_ambiente_iluminacao);
        ArrayAdapter<CharSequence> ambAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.ambiente_iluminacao_tipos,
            android.R.layout.simple_spinner_item
        );
        ambAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ambienteIluminacaoSpinner.setAdapter(ambAdapter);

        refletanciaSpinner = findViewById(R.id.spinner_refletancia);
        ArrayAdapter<CharSequence> refAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.refletancia_paredes_opcoes,
            android.R.layout.simple_spinner_item
        );
        refAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        refletanciaSpinner.setAdapter(refAdapter);

        areaDesignInput = findViewById(R.id.input_area_design);
        luxInput = findViewById(R.id.input_lux);
        alturaTetoInput = findViewById(R.id.input_altura_teto);
        lumensLampadaInput = findViewById(R.id.input_lumens_lampada);
        resultadoProjeto = findViewById(R.id.text_resultado_projeto);

        MaterialButton btnProjeto = findViewById(R.id.btn_calcular_projeto);
        btnProjeto.setOnClickListener(this::calcularProjetoLuminotecnico);
    }

    public void openLightingIntro(View view) {
        startActivity(new Intent(this, LightingIntroActivity.class));
    }

    public void calcularIluminacao(View view) {
        String areaStr = areaInput.getText().toString().trim();
        if (TextUtils.isEmpty(areaStr)) {
            resultadoIluminacao.setText(R.string.lighting_error_inform_area);
            return;
        }
        double area;
        try {
            area = Double.parseDouble(areaStr);
        } catch (NumberFormatException e) {
            resultadoIluminacao.setText(R.string.lighting_error_invalid_area);
            return;
        }
        if (area <= 0) {
            resultadoIluminacao.setText(R.string.lighting_error_area_positive);
            return;
        }

        int potenciaVA;
        if (area <= 6.0) {
            potenciaVA = 100;
        } else {
            double excedente = area - 6.0;
            int blocos = (int) Math.ceil(excedente / 4.0);
            potenciaVA = 100 + blocos * 60;
        }

        resultadoIluminacao.setText(getString(R.string.lighting_result_template, area, potenciaVA));
    }

    public void calcularProjetoLuminotecnico(View view) {
        String ambiente = (String) ambienteIluminacaoSpinner.getSelectedItem();
        String areaStr = areaDesignInput.getText().toString().trim();
        String luxStr = luxInput.getText().toString().trim();
        String alturaStr = alturaTetoInput.getText().toString().trim();
        String lumensLampStr = lumensLampadaInput.getText().toString().trim();

        if (TextUtils.isEmpty(areaStr) || TextUtils.isEmpty(luxStr)) {
            resultadoProjeto.setText(R.string.lighting_project_error_inform_area_lux);
            return;
        }

        double area;
        double luxDesejado;
        double altura = 2.6;
        double lumensPorLamp = 800;
        try {
            area = Double.parseDouble(areaStr);
            luxDesejado = Double.parseDouble(luxStr);
            if (!TextUtils.isEmpty(alturaStr)) altura = Double.parseDouble(alturaStr);
            if (!TextUtils.isEmpty(lumensLampStr)) lumensPorLamp = Double.parseDouble(lumensLampStr);
        } catch (NumberFormatException e) {
            resultadoProjeto.setText(R.string.lighting_project_error_invalid_numbers);
            return;
        }
        if (area <= 0 || luxDesejado <= 0 || lumensPorLamp <= 0) {
            resultadoProjeto.setText(R.string.lighting_project_error_positive_values);
            return;
        }

        double lumensBase = area * luxDesejado;

        double fatorAltura;
        if (altura <= 2.6) fatorAltura = 1.00;
        else if (altura <= 3.0) fatorAltura = 1.10;
        else if (altura <= 3.5) fatorAltura = 1.20;
        else fatorAltura = 1.30;

        String refletancia = (String) refletanciaSpinner.getSelectedItem();
        double fatorRefletancia = 1.00;
        if (refletancia != null) {
            if (refletancia.startsWith("Neutro")) fatorRefletancia = 1.10;
            else if (refletancia.startsWith("Escuro")) fatorRefletancia = 1.15;
        }

        double lumensAjustados = lumensBase * fatorAltura * fatorRefletancia;
        int qtdLampadas = (int) Math.ceil(lumensAjustados / lumensPorLamp);

        String sugestaoKelvin;
        int ambientePos = ambienteIluminacaoSpinner.getSelectedItemPosition();
        if (ambientePos == 0) {
            sugestaoKelvin = getString(R.string.lighting_kelvin_warm);
        } else if (ambientePos == 1 || ambientePos == 2 || ambientePos == 3) {
            sugestaoKelvin = getString(R.string.lighting_kelvin_neutral);
        } else {
            sugestaoKelvin = getString(R.string.lighting_kelvin_intermediate);
        }

        resultadoProjeto.setText(
                getString(
                        R.string.lighting_project_result_template,
                        ambiente,
                        area,
                        luxDesejado,
                        lumensBase,
                        altura,
                        fatorAltura,
                        refletancia,
                        fatorRefletancia,
                        lumensAjustados,
                        lumensPorLamp,
                        qtdLampadas,
                        sugestaoKelvin
                )
        );
    }
}
