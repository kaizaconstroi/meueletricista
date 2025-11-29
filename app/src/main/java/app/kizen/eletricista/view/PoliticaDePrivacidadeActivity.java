package app.kizen.eletricista.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;

import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import app.kizen.eletricista.R;
import app.kizen.eletricista.api.AppUtil;

public class PoliticaDePrivacidadeActivity extends AppCompatActivity {

    // Variaveis de verificação da politica de privacidade
    SharedPreferences sharedPreferences;
    boolean isPoliticaDePrivacidade = false;
    CheckBox chTermo;
    AlertDialog.Builder builder;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politica_de_privacidade);

        restaurarSharedPreferences();
        chTermo = findViewById(R.id.chTermo);
        construtorWebView();
        verificaPolitica();
    }

    private void verificaPolitica() {
        if (isPoliticaDePrivacidade) {
            chTermo.setChecked(true);
        }
    }

    private void construtorWebView() {
        // inicializara webView
        WebView webView = findViewById(R.id.webview);
        String text = "<html><body>"
                + "<p align=\"center\">"
                + getString(R.string.txtPolitica)
                + "</p> "
                + "</body></html>";
        webView.loadData(text, "text/html;charset=UTF-8", null);
    }

    // Metodo de controle do botão Voltar
    public void onBackPressed() { //Botão BACK padrão do android
        if (isPoliticaDePrivacidade) { // ve
            startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
            finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

        } else {
            builder = new AlertDialog.Builder(PoliticaDePrivacidadeActivity.this);
            builder.setTitle(R.string.tituloAlertPoliticadePrivacidade);
            builder.setMessage(R.string.alertPoliticadePrivacidade);
            builder.setCancelable(true);
            builder.setIcon(R.mipmap.ic_ajuda);
            builder.setPositiveButton("Voltar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Obrigado Aproveite o app", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent trocarDeTela;
                    trocarDeTela = new Intent(PoliticaDePrivacidadeActivity.this, MainActivity.class);
                    startActivity(trocarDeTela);
                    finish();
                }
            });
            alert = builder.create();
            alert.show();
        }


    }

    public void validarTermo(View view) {
        //Salvar no Shared Preferences
        isPoliticaDePrivacidade = true;
        salvarSharedPrerences();
    }

    private void salvarSharedPrerences() {
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = sharedPreferences.edit();
        dados.putBoolean("isPoliticaDeprivacidade", true);
        dados.apply();
    }

    private void restaurarSharedPreferences() {
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        isPoliticaDePrivacidade = sharedPreferences.getBoolean("isPoliticaDeprivacidade", false);
    }
}