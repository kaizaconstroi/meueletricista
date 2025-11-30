package app.kizen.eletricista.view;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import app.kizen.eletricista.R;
import app.kizen.eletricista.api.AppUtil;
import app.kizen.eletricista.domain.BreakerService;
import app.kizen.eletricista.util.InputValidator;

public class DisjuntorActivity extends AppCompatActivity {
    EditText editresultado, editCorrente;
    Button btnCalcular;
    public SwitchCompat swSugetao;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disjuntor);
        initFormulario();
    }

    /**
     * Calcula disjuntor usando BreakerService com margem de 15%.
     */
    public void Calcular(View view) {
        if (!InputValidator.isValidCurrent(editCorrente)) {
            return;
        }
        
        try {
            double corrente = Double.parseDouble(editCorrente.getText().toString());
            int disjuntor = BreakerService.selectBreaker(corrente);
            
            ocultarTeclado();
            
            if (disjuntor == 0) {
                editresultado.setText(R.string.alertcorrenteExcedeu);
            } else {
                editresultado.setText(String.format("Disjuntor: %d A", disjuntor));
                salvarSharedPrerences(disjuntor);
            }
        } catch (NumberFormatException e) {
            editresultado.setText("Erro: valor inválido");
        }
    }
    public void sugestor(View view) {
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);

        boolean checked = swSugetao.isChecked();
        if (checked) {
            /**
             * Activity para seleção do disjuntor adequado a partir da corrente calculada.
             * Aplica margem (ex.: 15%) e utiliza tabela de disjuntores padronizados.
             */
            editCorrente.setText(sharedPreferences.getString(getString(R.string.idAmper), "10"));


        }
        swSugetao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    editCorrente.setText(null);
                    editCorrente.setHint(R.string.editHintCorrente);
                } else {
                    editCorrente.setText(sharedPreferences.getString(getString(R.string.idAmper), "10"));
                }
                // Ajusta campo conforme sugestão ativada/desativada
            }
        });
    }

    private void salvarSharedPrerences(Integer d) {
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = sharedPreferences.edit();

        Double a = Double.parseDouble( editCorrente.getText().toString());
        dados.putString(getString(R.string.idPotencia), AppUtil.FormatarSaida(a) );

        dados.putString(getString(R.string.idDisjuntor), editresultado.getText().toString());

        dados.putString(getString(R.string.idAmper), editCorrente.getText().toString());
        dados.apply();
    }


    private void initFormulario() {
        btnCalcular = findViewById(R.id.btnCalcular);
        editresultado = findViewById(R.id.editResultadoConsumo);
        editCorrente = findViewById(R.id.editCorrente);
        swSugetao = findViewById(R.id.swSugestao);
    }

    private boolean validarDados() {
        return InputValidator.isValidCurrent(editCorrente);
    }

    public void onBackPressed() { //Botão BACK padrão do android
        startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

    }

    public void ajudaCorrente(View view) {
        // Objeto para troca de dados entre activitys
        Bundle bundle = new Bundle();
        // setando a informação
        bundle.putString("icone", "corrente");
        bundle.putString("activity", "disjuntor");

        Intent intent = new Intent(DisjuntorActivity.this, Ajuda.class);
        // passando o obj para a intent
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }


    public void AjudaSecao(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("icone", "ajudaPolitica");
        bundle.putString("activity", "politica");

        Intent intent = new Intent(DisjuntorActivity.this, Ajuda.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    public void ajudaDisjuntor(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("icone", "disjuntor");
        bundle.putString("activity", "disjuntor");

        Intent intent = new Intent(DisjuntorActivity.this, Ajuda.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();

    }

    private void ocultarTeclado() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(editCorrente.getWindowToken(), 0);
    }

}