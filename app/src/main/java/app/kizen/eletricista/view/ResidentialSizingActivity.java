package app.kizen.eletricista.view;

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

public class ResidentialSizingActivity extends AppCompatActivity {
    private Spinner tipoAmbienteSpinner;
    private EditText perimetroInput;
    private TextView resultadoTomadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residential_sizing);

        Toolbar toolbar = findViewById(R.id.toolbar_residential_sizing);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Dimensionamento Residencial (NBR 5410)");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        tipoAmbienteSpinner = findViewById(R.id.spinner_tipo_ambiente);
        perimetroInput = findViewById(R.id.input_perimetro);
        resultadoTomadas = findViewById(R.id.text_resultado_tomadas);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.tipos_ambiente_tomadas,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoAmbienteSpinner.setAdapter(adapter);

        MaterialButton btnCalcTomadas = findViewById(R.id.btn_calcular_tomadas);
        btnCalcTomadas.setOnClickListener(this::calcularTomadas);
    }

    public void calcularTomadas(View view) {
        String tipo = (String) tipoAmbienteSpinner.getSelectedItem();
        String perimetroStr = perimetroInput.getText().toString().trim();
        if (TextUtils.isEmpty(perimetroStr)) {
            resultadoTomadas.setText("Informe o perímetro do ambiente em metros.");
            return;
        }
        double perimetro;
        try {
            perimetro = Double.parseDouble(perimetroStr);
        } catch (NumberFormatException e) {
            resultadoTomadas.setText("Valor de perímetro inválido.");
            return;
        }

        int tomadasMin = 1;
        if (tipo.contains("Sala") || tipo.contains("Quarto") || tipo.contains("Corredor")) {
            tomadasMin = (int) Math.ceil(perimetro / 5.0);
        } else if (tipo.contains("Cozinha") || tipo.contains("Serviço") || tipo.contains("Despensa")) {
            tomadasMin = (int) Math.ceil(perimetro / 3.5);
        } else if (tipo.contains("Banheiro")) {
            tomadasMin = 1;
        }
        if (tomadasMin < 1) tomadasMin = 1;

        StringBuilder sb = new StringBuilder();
        sb.append("Recomendação mínima de tomadas (NBR 5410):\n\n");
        sb.append(" Tipo: ").append(tipo).append("\n");
        sb.append(String.format(" Perímetro: %.2f m\n", perimetro));
        sb.append(" Quantidade mínima: ").append(tomadasMin).append(" TUGs\n\n");
        sb.append("Observação: equipamentos de grande potência (geladeira, micro-ondas, forno, máquina de lavar) devem considerar circuitos dedicados e não entram no cômputo das TUGs gerais.");

        resultadoTomadas.setText(sb.toString());
    }
}
