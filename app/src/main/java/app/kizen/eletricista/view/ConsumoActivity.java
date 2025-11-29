package app.kizen.eletricista.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import app.kizen.eletricista.R;
import app.kizen.eletricista.api.AppUtil;

public class ConsumoActivity extends AppCompatActivity {
    // 1° passo declaração dos objetos
    EditText editResultado, editPotencia, editTempo, editValorKva;
    AlertDialog.Builder builder;
    AlertDialog alert;
    public SwitchCompat swSugetao;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumo);
        // metodos
        initFormulario();
    }

    public void sugestor(View view) {
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);

        boolean checked = swSugetao.isChecked();
        if (checked) {
            editPotencia.setText(sharedPreferences.getString(getString(R.string.idPotencia), "7500"));
            editTempo.setText(sharedPreferences.getString(getString(R.string.idTempo), "1"));
            editValorKva.setText(sharedPreferences.getString(getString(R.string.idValorKva), "2.5"));

        }
        swSugetao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!swSugetao.isChecked()) {
                    editPotencia.setText(null);
                    editTempo.setText(null);
                    editValorKva.setText(null);
                    editPotencia.setHint(R.string.editHintCorrente);
                    editTempo.setText(R.string.editHintTempo);
                    editValorKva.setText(R.string.editHintValorKva);
                }
            }
        });
    }

    public void Calcular(View view) {
        if (validarDados()) {
            editPotencia.setError(null);
            editTempo.setError(null);
            editValorKva.setError(null);
            Double kva, resultado, vkva, tempo;
            tempo = Double.parseDouble(editTempo.getText().toString());
            if (tempo < 1) {
                tempo = (tempo / 60) * 100;
            }
            kva = Double.parseDouble(editPotencia.getText().toString()) / 1000;
            kva = kva * tempo;
            vkva = Double.parseDouble(editValorKva.getText().toString());
            resultado = kva * vkva;                                     // Double.parseDouble(editValorKva.getText().toString() );
            ocultarTeclado();
            editResultado.setText("R$ " + AppUtil.FormatarSaida(resultado));

        salvarSharedPrerences();
        }
    }
    private void salvarSharedPrerences() {
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = sharedPreferences.edit();

        Double p = Double.parseDouble( editPotencia.getText().toString());

        dados.putString(getString(R.string.idPotencia), AppUtil.FormatarSaida(p) );
        dados.putString(getString(R.string.idTempo), editTempo.getText().toString());
        dados.putString(getString(R.string.idValorKva), editValorKva.getText().toString());
        dados.apply();
    }

    private void ocultarTeclado() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(editValorKva.getWindowToken(), 0);
    }

    private boolean validarDados() {
        boolean retorno = true;
        if (TextUtils.isEmpty(editPotencia.getText().toString())) {
            retorno = false;
            editPotencia.setError("*");
            editPotencia.requestFocus();
        } else if (TextUtils.isEmpty(editTempo.getText().toString())){
            retorno = false;
            editTempo.setError("*");
            editTempo.requestFocus();
       } else if (TextUtils.isEmpty(editTempo.getText().toString())) {
            retorno = false;
            editTempo.setError("*");
            editTempo.requestFocus();
        } else if (TextUtils.isEmpty(editValorKva.getText().toString())) {
            retorno = false;
            editValorKva.setError("*");
            editValorKva.requestFocus();
        }
        return retorno;
    }

    private void initFormulario() {
        editPotencia = findViewById(R.id.editPotencia);
        editTempo = findViewById(R.id.editTempo);
        editResultado = findViewById(R.id.editResultadoConsumo);
        editValorKva = findViewById(R.id.editValorKva);
        swSugetao = findViewById(R.id.swSugestao);
    }

    // Metodo de controle do botão Voltar
    public void onBackPressed() { //Botão BACK padrão do android
        startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
    }

    public void ajudaPotencia(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("icone", "potencia");
        bundle.putString("activity", "consumo");
        Intent intent = new Intent(ConsumoActivity.this, Ajuda.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }


    public void ajudaConsumo(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("icone", "consumo");
        bundle.putString("activity", "consumo");

        Intent intent = new Intent(ConsumoActivity.this, Ajuda.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }


    public void ajudaTempo(View view) {
        builder = new AlertDialog.Builder(ConsumoActivity.this);
        builder.setTitle("TEMPO ");
        builder.setMessage("Exemplo meia hora = 0.30");
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
}
