package app.kizen.eletricista.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import app.kizen.eletricista.R;
import app.kizen.eletricista.api.AppUtil;
import app.kizen.eletricista.util.InputValidator;

public class CorrenteCircuitoActivity extends AppCompatActivity {
    // 1° passo declaração dos objetos
    EditText editresultado, editPotencia, editTensao;
    public SwitchCompat sw127, sw220, sw380, swSugetao;

    Button btnCalcular;

    SharedPreferences sharedPreferences;
    // salva valor da corrente

/**
 * Activity para cálculo de corrente de circuito a partir de potência e tensão.
 * Permite seleção rápida de tensões comuns (127/220/380) e salva valores
 * em SharedPreferences para sugestão em outras telas.
 */

    // Campos de entrada e saída
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrente_circuito);
        // metodos
        //restaurarSharedPreferences();
        initFormulario();
        entradaDeDados();
    }

    public void Calcular(View view) {
        editPotencia.setError(null);
        editTensao.setError(null);
        String tratamentoDePontoPotencia = editPotencia.getText().toString();
        String tratamentoDePontoTensao = editTensao.getText().toString();
        Double ValueA;

        if (validarDados()) {
            ValueA = Double.parseDouble(tratamentoDePontoPotencia) / Double.parseDouble(tratamentoDePontoTensao);
            editresultado.setText(AppUtil.FormatarSaida(ValueA) + " A");
    // Calcula I = P / V e formata saída
            salvarSharedPrerences(ValueA, tratamentoDePontoPotencia, tratamentoDePontoTensao);
        }
    }

    public void sugestor(View view) {
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);

        boolean checked = swSugetao.isChecked();
        if (checked) {
            editPotencia.setText(sharedPreferences.getString(getString(R.string.idPotencia), "7500"));
            editTensao.setText(sharedPreferences.getString(getString(R.string.idTensao), "127"));
        }
        swSugetao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // Quando marcado: limpa campos e mostra hints
                    editPotencia.setText(null);
                    editTensao.setText(null);
                    editPotencia.setHint(R.string.editHintPotencia);
                    editTensao.setHint(editTensao.getHint().toString());
                } else {
                    // Quando desmarcado: restaura valores salvos
                    editPotencia.setText(sharedPreferences.getString(getString(R.string.idPotencia), "7500"));
                    editTensao.setText(sharedPreferences.getString(getString(R.string.idTensao), "127"));
                }
            }
        });
    }

    public void onBackPressed() { //Botão BACK padrão do android
        startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

    }

    private void salvarSharedPrerences(Double armazenaA, String tratamentoDePontoPotencia, String tratamentoDePontoTensao) {
        // TODO SALVAR VALOR DA CORRENTE usa nome amper
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = sharedPreferences.edit();

        Double p = Double.parseDouble(tratamentoDePontoPotencia);
        dados.putString(getString(R.string.idPotencia), AppUtil.FormatarSaida(p));

        dados.putString(getString(R.string.idAmper), AppUtil.FormatarSaida(armazenaA));
        dados.putString(getString(R.string.idTensao), tratamentoDePontoTensao);


        dados.apply();

    }

    private boolean validarDados() {
        return InputValidator.isValidPower(editPotencia) 
            && InputValidator.isValidVoltage(editTensao);
    }

    private void ocultarTeclado(EditText edit) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }

    private void initFormulario() {
        btnCalcular = findViewById(R.id.btnCalcular);
        editPotencia = findViewById(R.id.editPotencia);
        editTensao = findViewById(R.id.editTensao);
        editresultado = findViewById(R.id.editResultadoConsumo);
        sw127 = findViewById(R.id.sw127);
        sw220 = findViewById(R.id.sw220);
        sw380 = findViewById(R.id.sw380);
        swSugetao = findViewById(R.id.swSugestao);

    }

    public void ajudaPotencia(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("icone", "potencia");
        bundle.putString("activity", "corrente");

        Intent intent = new Intent(CorrenteCircuitoActivity.this, Ajuda.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    public void ajudaCorrente(View view) {
        // Objeto para troca de dados entre activitys
        Bundle bundle = new Bundle();
        // setando a informação
        bundle.putString("icone", "corrente");
        bundle.putString("activity", "corrente");

        Intent intent = new Intent(CorrenteCircuitoActivity.this, Ajuda.class);
        // passando o obj para a intent
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    private void entradaDeDados() {
        sw127.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sw220.setChecked(false);
                sw380.setChecked(false);
                editTensao.setText(sw127.getText().toString());
                ocultarTeclado(editTensao);
            }
        });
        sw220.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sw127.setChecked(false);
                sw380.setChecked(false);
                editTensao.setText(sw220.getText().toString());
                ocultarTeclado(editTensao);

            }
        });
        sw380.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sw127.setChecked(false);
                sw220.setChecked(false);
                editTensao.setText(sw380.getText().toString());
                ocultarTeclado(editTensao);

            }
        });
    }

}