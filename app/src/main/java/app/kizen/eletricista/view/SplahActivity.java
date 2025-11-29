package app.kizen.eletricista.view;


import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;
import android.content.SharedPreferences;




import android.os.Bundle;
import android.os.Handler;

import android.webkit.WebView;

import app.kizen.eletricista.R;
import app.kizen.eletricista.api.AppUtil;

public class SplahActivity extends AppCompatActivity {
    // Declaração do Obje WebView
    WebView sloganWebview, sloganTitleWebview;
    int tempoDeEspera = 3500;
    boolean isPoliticaDePrivacidade = false;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah);
        sloganWebview = (WebView) findViewById(R.id.sloganWebview);
        sloganTitleWebview = (WebView) findViewById(R.id.sloganTitleWebview);
        restaurarSharedPreferences();
        construtorWebView();
        trocarTela();

    }


    private void trocarTela() {
        // Intent classe intenção de algo
        // trocarDeTela Obj
        new Handler().postDelayed(() -> {
            Intent trocarDeTela;
            // Precisa refazer
            trocarDeTela = new Intent(SplahActivity.this, MainActivity.class);
            startActivity(trocarDeTela);
            finish();

        }, tempoDeEspera);

    }

    private void construtorWebView() {
        String text = "<html><body>"
                + "<h3 align=\"center\">"
                + "</title>"
                + getString(R.string.textoSlogan)
                + "</h3> "
                + "</body></html>";

        sloganWebview.loadData(text, "text/html;charset=UTF-8", null);

        String text2 = "<html><body>"
                + "<h2 align=\"center\">"
                + getString(R.string.titleTxtoSlogan)
                + "<h2> "
                + "</body></html>";

        sloganTitleWebview.loadData(text2, "text/html;charset=UTF-8", null);
    }

    private void restaurarSharedPreferences() {
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        isPoliticaDePrivacidade = sharedPreferences.getBoolean("isPoliticaDeprivacidade", false);
    }


}