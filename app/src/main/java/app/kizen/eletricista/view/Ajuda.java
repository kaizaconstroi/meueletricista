package app.kizen.eletricista.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import app.kizen.eletricista.R;

public class Ajuda extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);

        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
       webSettings.setJavaScriptEnabled(true);// se tirar alguns site não funcionam
        // Recupera a informação da outra activity
        Bundle bundle = getIntent().getExtras();
        if (isConnected(getApplicationContext())) { // Verifica se tem conexão com a internet
            if (bundle.getString("icone").equals("corrente")) {

                webView.loadUrl("https://www.mundodaeletrica.com.br/o-que-e-corrente-eletrica/");

            } else if (bundle.getString("icone").equals("potencia")) {

                webView.loadUrl("https://www.educamaisbrasil.com.br/enem/fisica/potencia-eletrica");

            } else if (bundle.getString("icone").equals("tempo")) {

                webView.loadUrl("https://www.zoom.com.br/tv/deumzoom/como-calcular-e-economizar-energia-eletrica-residencial");
            } else if (bundle.getString("icone").equals("instalacao")) {

                webView.loadUrl("https://yinnihisoka.wixsite.com/kizenaplicativos/tabela-33-nbr5410");
            } else if (bundle.getString("icone").equals("disjuntor")) {

                 webView.loadUrl("https://www.youtube.com/watch?v=bjQi4TgyFBs");
            } else if (bundle.getString("icone").equals("condutor")) {

                webView.loadUrl("https://www.mundodaeletrica.com/bitola-de-fios-como-identificar/");
            } else if (bundle.getString("icone").equals("queda")) {

                webView.loadUrl("https://www.mundodaeletrica.com.br/como-calcular-queda-de-tensao-nos-condutores/");
            } else if (bundle.getString("icone").equals("ajudaPolitica")) {

                webView.loadUrl("https://support.google.com/googleplay/answer/2666094?hl=pt-BR");
            } else if (bundle.getString("icone").equals("numeroCondutorCar")) {

                webView.loadUrl("https://yinnihisoka.wixsite.com/kizenaplicativos/p%C3%A1gina-em-branco");
            }else if (bundle.getString("icone").equals("temp")) {

                webView.loadUrl("https://suporte.altoqi.com.br/hc/pt-br/articles/115003776554-Como-%C3%A9-calculada-a-corrente-corrigida-");
            }else if (bundle.getString("icone").equals("consumo")) {

                webView.loadUrl("https://www.zoom.com.br/tv/deumzoom/como-calcular-e-economizar-energia-eletrica-residencial");
            }
            else if (bundle.getString("icone").equals("sobre")) {

                webView.loadUrl("https://yinnihisoka.wixsite.com/kizenaplicativos/sobre-o-aplicativo");
            } else if (bundle.getString("icone").equals("iso")) {

                webView.loadUrl("https://www.luminuseletricidade.com.br/conheca-os-principais-tipos-de-condutores-eletricos#:~:text=S%C3%A3o%20cabos%20condutores%20com%20isolamento,fabricantes%20sugerem%20capacidade%20at%C3%A9%20105%C2%BAC).");
            }

        } else {
            String text = "<html><body>"
                    + "<p align=\"center\">"
                    + getString(R.string.txtWebErro)
                    + "</p> "
                    + "</body></html>";

            webView.loadData(text, "text/html;charset=UTF-8", null);
        }


    }

    public void onBackPressed() { //Botão BACK padrão do android
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("activity").equals("corrente")) { // Verifica em qual Activity estava
            startActivity(new Intent(this, CorrenteCircuitoActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
            finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

        } else if (bundle.getString("activity").equals("potencia")) {
            startActivity(new Intent(this, PotenciaActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
            finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

        } else if (bundle.getString("activity").equals("consumo")) {
            startActivity(new Intent(this, ConsumoActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
            finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

        }  else if (bundle.getString("activity").equals("condutor")) {
            startActivity(new Intent(this, CondutorActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
            finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

        } else if (bundle.getString("activity").equals("disjuntor")) {
            startActivity(new Intent(this, DisjuntorActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
            finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

        } else if (bundle.getString("activity").equals("condutor")) {
            startActivity(new Intent(this, CondutorActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
            finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

        } else if (bundle.getString("activity").equals("queda")) {
            startActivity(new Intent(this, QuedadeTensaoActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
            finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

        } else if (bundle.getString("activity").equals("politica")) {
            startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
            finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

        }else if (bundle.getString("activity").equals("main")) {
            startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
            finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

        }

    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();

            return ni != null && ni.isConnected();
        }

        return false;
    }
}