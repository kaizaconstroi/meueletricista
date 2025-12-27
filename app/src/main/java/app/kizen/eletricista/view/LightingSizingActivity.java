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
            getSupportActionBar().setTitle("Dimensionamento de Iluminação (NBR 5410)");
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
            resultadoIluminacao.setText("Informe a área do ambiente em m².");
            return;
        }
        double area;
        try {
            area = Double.parseDouble(areaStr);
        } catch (NumberFormatException e) {
            resultadoIluminacao.setText("Valor de área inválido.");
            return;
        }
        if (area <= 0) {
            resultadoIluminacao.setText("Área deve ser maior que zero.");
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

        StringBuilder sb = new StringBuilder();
        sb.append("Potência mínima de iluminação (NBR 5410):\n\n");
        sb.append(String.format("• Área: %.2f m²\n", area));
        sb.append("• Potência mínima instalada: ").append(potenciaVA).append(" VA\n");
        sb.append("• Pontos de luz: mínimo 1; distribuir conforme projeto e uniformidade luminosa.\n\n");
        sb.append("Observação: a potência mínima é referência de previsão de carga; escolha de luminárias (LED) e quantidade de pontos deve considerar níveis de iluminância conforme uso do ambiente.");

        resultadoIluminacao.setText(sb.toString());
    }

    public void calcularProjetoLuminotecnico(View view) {
        String ambiente = (String) ambienteIluminacaoSpinner.getSelectedItem();
        String areaStr = areaDesignInput.getText().toString().trim();
        String luxStr = luxInput.getText().toString().trim();
        String alturaStr = alturaTetoInput.getText().toString().trim();
        String lumensLampStr = lumensLampadaInput.getText().toString().trim();

        if (TextUtils.isEmpty(areaStr) || TextUtils.isEmpty(luxStr)) {
            resultadoProjeto.setText("Informe a área (m²) e o lux desejado.");
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
            resultadoProjeto.setText("Valores numéricos inválidos.");
            return;
        }
        if (area <= 0 || luxDesejado <= 0 || lumensPorLamp <= 0) {
            resultadoProjeto.setText("Área, lux e lúmens por lâmpada devem ser maiores que zero.");
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
        if (ambiente.contains("Sala") || ambiente.contains("Quarto")) sugestaoKelvin = "2700K - 3000K (luz quente)";
        else if (ambiente.contains("Cozinha") || ambiente.contains("Serviço") || ambiente.contains("Escritório") || ambiente.contains("Trabalho") || ambiente.contains("Banheiro")) sugestaoKelvin = "4000K - 5000K (neutra)";
        else sugestaoKelvin = "3000K - 4000K (intermediária)";

        StringBuilder sb = new StringBuilder();
        sb.append("Guia de Projeto Luminotécnico (Lux → Lúmens)\n\n");
        sb.append("1) Ambiente: ").append(ambiente).append("\n");
        sb.append(String.format("2) Área: %.2f m² | Lux: %.0f lx\n", area, luxDesejado));
        sb.append(String.format("→ Lúmens base: %.0f lm\n", lumensBase));
        sb.append(String.format("3) Ajustes – Altura: %.2f m (×%.2f), Refletância: %s (×%.2f)\n", altura, fatorAltura, refletancia, fatorRefletancia));
        sb.append(String.format("→ Lúmens ajustados: %.0f lm\n", lumensAjustados));
        sb.append(String.format("4) Lâmpadas: ~%.0f lm por lâmpada → Recomendar %d unid.\n\n", lumensPorLamp, qtdLampadas));

        sb.append("Camadas de iluminação:\n");
        sb.append("• Ambiente: plafon/pendente central para o nível geral.\n");
        sb.append("• Tarefa: abajures/spot sobre bancadas/mesa de trabalho.\n");
        sb.append("• Destaque: spots para quadros/fitas LED em prateleiras.\n\n");

        sb.append("Temperatura de cor sugerida: ").append(sugestaoKelvin).append("\n");
        sb.append("Dimerização: recomenda-se dimmers para ajustar a intensidade conforme uso.\n\n");
        sb.append("Nota: luminárias LED típicas entregam 80–100 lm/W; escolha conforme eficiência e distribuição óptica (difusor/feixe).\n");

        resultadoProjeto.setText(sb.toString());
    }
}
