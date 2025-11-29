package app.kizen.eletricista.view;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;

import app.kizen.eletricista.R;
import app.kizen.eletricista.api.AppUtil;

public class PotenciaActivity extends AppCompatActivity {

    EditText editCorrente, editTensao, editResultado;
    public SwitchCompat sw127, sw220, sw380;
    public SwitchCompat swSugetao;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potencia);
        initFormulario();
        entradaDeDados();
    }

    private void salvarSharedPrerences(Double r) {
        // TODO SALVAR VALOR DA CORRENTE usa nome amper
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = sharedPreferences.edit();

        dados.putString(getString(R.string.idPotencia), AppUtil.FormatarSaida(r) );

       // Double a = Double.parseDouble( editCorrente.getText().toString());
        //dados.putString(getString(R.string.idAmper), AppUtil.FormatarSaida(a) );

        dados.putString(getString(R.string.idTensao),editTensao.getText().toString());

        dados.apply();
    }

    public void onBackPressed() { //Botão BACK padrão do android
        startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

    }

    public void Calcular(View view) {
        editCorrente.setError(null);
        editTensao.setError(null);
        String tratamentoDePontoTensao = editTensao.getText().toString();
        Log.i("decimal", "Calcular: string Tensao "+tratamentoDePontoTensao);

        String tratamentoCorrente = editCorrente.getText().toString();
        Log.i("decimal", "Calcular: string Corrente "+tratamentoCorrente);

        //tratamentoDePontoTensao = tratamentoDePontoTensao.replace(",",".");
        //Log.i("decimal", "Calcular: string tensão sem virgula "+tratamentoDePontoTensao);

        //tratamentoCorrente = tratamentoCorrente.replace(",",".");
        Log.i("decimal", "Calcular: string Corrente sem virgula "+tratamentoCorrente);
        if (validarDados()) {
            Double result = Double.parseDouble(tratamentoCorrente) * Double.parseDouble(tratamentoDePontoTensao);
            editResultado.setText(AppUtil.FormatarSaida(result) + " W");
            salvarSharedPrerences(result);
        }

    }
    public void sugestor(View view) {
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);

        boolean checked = swSugetao.isChecked();
        if (checked) {
            editCorrente.setText(sharedPreferences.getString(getString(R.string.idAmper), "10"));
            editTensao.setText(sharedPreferences.getString(getString(R.string.idTensao) ,"127"));

        }
        swSugetao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!swSugetao.isChecked()) {
                    editCorrente.setText(null);
                    editTensao.setText(null);
                    editCorrente.setHint(R.string.editHintCorrente);
                    editTensao.setHint(editTensao.getHint().toString());
                } else {
                    editCorrente.setText(sharedPreferences.getString(getString(R.string.idAmper), "10"));
                    editTensao.setText(sharedPreferences.getString(getString(R.string.idTensao), "127"));
                }
            }
        });
    }

    private boolean validarDados() {
        boolean retorno = true;
        if (TextUtils.isEmpty(editCorrente.getText().toString())) {
            retorno = false;
            editCorrente.setError("*");
            editCorrente.requestFocus();
        } else if (TextUtils.isEmpty(editTensao.getText().toString())) {
            retorno = false;
            editTensao.setError("*");
            editTensao.requestFocus();
        }
        return retorno;
    }

    private void entradaDeDados() {
        sw127.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sw220.setChecked(false);
                sw380.setChecked(false);
                editTensao.setText(sw127.getText().toString());
                ocultarTeclado();
            }
        });
        sw220.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sw127.setChecked(false);
                sw380.setChecked(false);
                editTensao.setText(sw220.getText().toString());
                ocultarTeclado();
            }
        });
        sw380.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sw127.setChecked(false);
                sw220.setChecked(false);
                editTensao.setText(sw380.getText().toString());
                ocultarTeclado();
            }
        });
    }

    private void initFormulario() {
        editCorrente = findViewById(R.id.editCorrente);
        editTensao = findViewById(R.id.editTensao);
        editResultado = findViewById(R.id.editResultadoConsumo);
        sw127 = findViewById(R.id.sw127);
        sw220 = findViewById(R.id.sw220);
        sw380 = findViewById(R.id.sw380);
        swSugetao = findViewById(R.id.swSugestao);
    }

    public void ajudaCorrente(View view) {
        Bundle bundle = new Bundle();

        bundle.putString("icone", "corrente");
        bundle.putString("activity", "potencia");

        Intent intent = new Intent(PotenciaActivity.this, Ajuda.class);

        intent.putExtras(bundle);

        startActivity(intent);
        finish();

    }

    private void ocultarTeclado() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(editCorrente.getWindowToken(), 0);
    }

    public void ajudaPotencia(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("icone", "potencia");
        bundle.putString("activity", "potencia");
        Intent intent = new Intent(PotenciaActivity.this, Ajuda.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();

    }
}