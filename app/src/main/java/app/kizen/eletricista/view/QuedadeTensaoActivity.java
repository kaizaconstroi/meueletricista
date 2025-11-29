package app.kizen.eletricista.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import app.kizen.eletricista.R;
import app.kizen.eletricista.api.AppUtil;

public class QuedadeTensaoActivity extends AppCompatActivity {
    // 1° passo declaração dos objetos
    Button btnCalcular;
    SharedPreferences sharedPreferences;
    public SwitchCompat sw127, sw220, sw380, swSugetao;
    EditText editSecao, editCorrente, editDistancia, editTensao, editresultado, editresultado2, editInfImportanteQuedaDeTensao;

    // AlertDialog
    AlertDialog.Builder builder;
    AlertDialog alert;

    // Variavel de seleçaõ de voltagem


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quedade_tensao);
        initFormulario();
        entradaDeDados();

    }

    public void sugestor(View view) {
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);

        boolean checked = swSugetao.isChecked();
        if (checked) {
            editSecao.setText(sharedPreferences.getString(getString(R.string.idSecao), "2.5"));
            editCorrente.setText(sharedPreferences.getString(getString(R.string.idAmper), "10"));
            editDistancia.setText(sharedPreferences.getString(getString(R.string.idDistancia), "100"));
            editTensao.setText(sharedPreferences.getString(getString(R.string.idTensao) ,"127"));
        }
        swSugetao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!swSugetao.isChecked()) {
                    editSecao.setText(null);
                    editCorrente.setText(null);
                    editDistancia.setText(null);
                    editTensao.setText(null);

                    editSecao.setHint(R.string.editHintSecaoCondutor);
                    editCorrente.setHint(R.string.editHintCorrente);
                    editDistancia.setHint(R.string.editHintDistancia);
                    editTensao.setHint(editTensao.getHint().toString());
                } else {
                    editSecao.setText(sharedPreferences.getString(getString(R.string.idSecao), "2.5"));
                    editCorrente.setText(sharedPreferences.getString(getString(R.string.idAmper), "10"));
                    editDistancia.setText(sharedPreferences.getString(getString(R.string.idDistancia), "100"));
                    editTensao.setText(sharedPreferences.getString(getString(R.string.idTensao) ,"127"));
                }
            }
        });
    }

    public void Calcular(View view) {
        double resistencia, quedaDeTensao, perdadeTensao, tensao;
        editresultado.setError(null);
        if (validarDados()) {
            tensao = Double.parseDouble(editTensao.getText().toString());
            if (tensao >= 100 && tensao <= 140) {
                resistencia = ((Double.parseDouble(editDistancia.getText().toString()) * 2) * AppUtil.resistenciaDoCobre) / Double.parseDouble(editSecao.getText().toString());
                quedaDeTensao = resistencia * Double.parseDouble(editCorrente.getText().toString());
                //// valores aceitaveis entre 120 a 1132
                perdadeTensao = (resistencia * Double.parseDouble(editCorrente.getText().toString()) * 100 / 127);
                editresultado.setText(getString(R.string.perdaTensao) + AppUtil.FormatarSaida(perdadeTensao) + " %");
                editresultado2.setText(getString(R.string.tensaoAtual) + AppUtil.FormatarSaida(Double.parseDouble(editTensao.getText().toString()) - quedaDeTensao) + " V");
                editInfImportanteQuedaDeTensao.setText(R.string.tresPorCentoDeQuedaTensao);
            } else if (tensao >= 140 && tensao <= 240) {
                resistencia = ((Double.parseDouble(editDistancia.getText().toString()) * 2) * AppUtil.resistenciaDoCobre) / Double.parseDouble(editSecao.getText().toString());
                quedaDeTensao = resistencia * Double.parseDouble(editCorrente.getText().toString());
                //// valores aceitaveis entre 220 a 211,2
                perdadeTensao = (resistencia * Double.parseDouble(editCorrente.getText().toString()) * 100 / 220);
                editresultado.setText(getString(R.string.perdaTensao) + AppUtil.FormatarSaida(perdadeTensao) + " %");
                editresultado2.setText(getString(R.string.tensaoAtual) + AppUtil.FormatarSaida(Double.parseDouble(editTensao.getText().toString()) - quedaDeTensao) + " V");
                editInfImportanteQuedaDeTensao.setText(R.string.tresPorCentoDeQuedaTensao);
            } else if (tensao >= 240 && tensao <= 390) {
                // Sistema trifásico 220/380V / Queda de tensão admissível 5%.
                resistencia = AppUtil.resistenciaDoCobre * (Double.parseDouble(editDistancia.getText().toString()) * Math.sqrt(3)) / Double.parseDouble(editSecao.getText().toString());
                quedaDeTensao = Math.ceil(resistencia * Double.parseDouble(editCorrente.getText().toString()));
                perdadeTensao = (resistencia * Double.parseDouble(editCorrente.getText().toString()) * 100 / 380);
                editresultado.setText(getString(R.string.perdaTensao) + AppUtil.FormatarSaida(perdadeTensao) + " %");
                editresultado2.setText(getString(R.string.tensaoAtual) + AppUtil.FormatarSaida(Double.parseDouble(editTensao.getText().toString()) - quedaDeTensao) + " V");
                editInfImportanteQuedaDeTensao.setText(R.string.cincoPorCentoquedaDeTensao);
            } else if (tensao < 100) {
                editresultado.requestFocus();
                editresultado.setError(getString(R.string.valorMinimoTensao));
                editTensao.setError("*");
            } else if (tensao > 390) {
                procedimentoDeErro();
                editresultado.requestFocus();
                editresultado.setError(getString(R.string.valormaximotensao));
                editTensao.setError("*");
            }
            salvarSharedPrerences();
        }

    }

    private void procedimentoDeErro() {
        editTensao.setText(null);
        editInfImportanteQuedaDeTensao.setText(null);
        editTensao.setError("*");
        editresultado.requestFocus();
    }
    private void salvarSharedPrerences() {
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = sharedPreferences.edit();

        dados.putString(getString(R.string.idSecao),editSecao.getText().toString());

        Double a = Double.parseDouble( editCorrente.getText().toString());

        dados.putString(getString(R.string.idPotencia), AppUtil.FormatarSaida(a) );

        dados.putString(getString(R.string.idDistancia),editDistancia.getText().toString());

        dados.putString(getString(R.string.idTensao),editTensao.getText().toString());

        dados.apply();
    }
    @Override
    public void onBackPressed() { //Botão BACK padrão do android
        startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

    }

    private void initFormulario() {

        btnCalcular = findViewById(R.id.btnCalcular);
        editSecao = findViewById(R.id.editNumeroDeCondutoresCarregados);
        editCorrente = findViewById(R.id.editCorrente);
        editDistancia = findViewById(R.id.editDistancia);
        editTensao = findViewById(R.id.editTensao);
        editresultado = findViewById(R.id.editResultadoConsumo);
        editresultado2 = findViewById(R.id.editTensaoAtual);
        editInfImportanteQuedaDeTensao = findViewById(R.id.editInfImportanteQuedaDeTensao);
        sw127 = findViewById(R.id.sw127);
        sw220 = findViewById(R.id.sw220);
        sw380 = findViewById(R.id.sw380);
        swSugetao = findViewById(R.id.swSugestao);

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

    private boolean validarDados() {
        boolean retorno = true;

        if (TextUtils.isEmpty(editSecao.getText().toString())) {
            editSecao.setError("*");
            editSecao.requestFocus();
            retorno = false;

        } else if (TextUtils.isEmpty(editCorrente.getText().toString())) {
            editCorrente.setError("*");
            editCorrente.requestFocus();
            retorno = false;
        }

        if (TextUtils.isEmpty(editDistancia.getText().toString())) {
            editDistancia.setError("*");
            editDistancia.requestFocus();
            retorno = false;
        } else if (TextUtils.isEmpty(editTensao.getText().toString())) {
            editTensao.setError("*");
            editTensao.requestFocus();
            retorno = false;
        } else if (AppUtil.sessaoDosCondutores(Double.parseDouble(editSecao.getText().toString()))) {
            editSecao.setError("*");
            editSecao.requestFocus();
            retorno = false;
        }

        return retorno;
    }


    /**
     * oculta o teclado de edit
     */
    private void ocultarTeclado() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(editTensao.getWindowToken(), 0);
    }

    public void ajudaCorrente(View view) {
        Bundle bundle = new Bundle();

        bundle.putString("icone", "corrente");
        bundle.putString("activity", "queda");

        Intent intent = new Intent(QuedadeTensaoActivity.this, Ajuda.class);

        intent.putExtras(bundle);

        startActivity(intent);
        finish();

    }


    public void ajudaDistancia(View view) {
        builder = new AlertDialog.Builder(QuedadeTensaoActivity.this);
        builder.setTitle("Distancia ");
        builder.setMessage(R.string.mensagemajudaDisntancia);
        builder.setCancelable(true);
        builder.setIcon(R.mipmap.ic_ajuda);
        builder.setPositiveButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert = builder.create();
        alert.show();
    }

    public void AjudaSecao(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("icone", "condutor");
        bundle.putString("activity", "queda");

        Intent intent = new Intent(QuedadeTensaoActivity.this, Ajuda.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();

    }

    public void ajudaQuedadeTensao(View view) {
        Bundle bundle = new Bundle();

        bundle.putString("icone", "queda");
        bundle.putString("activity", "queda");

        Intent intent = new Intent(QuedadeTensaoActivity.this, Ajuda.class);

        intent.putExtras(bundle);

        startActivity(intent);
        finish();

    }
}