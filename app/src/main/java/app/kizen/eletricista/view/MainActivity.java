package app.kizen.eletricista.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import app.kizen.eletricista.R;
import app.kizen.eletricista.api.AppUtil;

public class MainActivity extends AppCompatActivity {
    /**
     * Activity principal: navegação para calculadoras e política de privacidade.
     * Também oferece compartilhamento do link do app na Play Store.
     */
    // AlertDialog
    AlertDialog.Builder builder;
    AlertDialog alert;
    boolean isPoliticaDePrivacidade = false;
    SharedPreferences sharedPreferences;
    TextView sharedApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurarSharedPreferences();
        //sharedApp=findViewById(R.id.txtSharedApp);
    }

    private void restaurarSharedPreferences() {
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        isPoliticaDePrivacidade = sharedPreferences.getBoolean("isPoliticaDeprivacidade", false);
    }

    /** Verifica conectividade (API legada) para abrir páginas online. */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();

            return ni != null && ni.isConnected();
        }

        return false;
    }

    public void CorrenteEletrica(View view) {
        Intent intent = new Intent(MainActivity.this, CorrenteCircuitoActivity.class);
        startActivity(intent);
        finish();
    }

    public void QuedaDeTensao(View view) {
        Intent intent = new Intent(MainActivity.this, QuedadeTensaoActivity.class);
        startActivity(intent);
        finish();
    }

    public void SecaoCondutor(View view) {
        Intent intent = new Intent(MainActivity.this, CondutorActivity.class);
        startActivity(intent);
        finish();
    }

    public void CondutoreDisjuntor(View view) {
        Intent intent = new Intent(MainActivity.this, CondutorCompletoctivity.class);
        startActivity(intent);
        finish();
    }

    public void Consumo(View view) {
        Intent intent = new Intent(MainActivity.this, ConsumoActivity.class);
        startActivity(intent);
        finish();
    }

    public void Potencia(View view) {
        Intent intent = new Intent(MainActivity.this, PotenciaActivity.class);
        startActivity(intent);
        finish();
    }

    public void Disjuntor(View view) {
        Intent intent = new Intent(MainActivity.this, DisjuntorActivity.class);
        startActivity(intent);
        finish();
    }

    /** Abre a política de privacidade apenas se houver conexão. */
    public void PoliticadePrivacidade(View view) {
        if (isConnected(getApplicationContext())) {
            Intent intent = new Intent(MainActivity.this, PoliticaDePrivacidadeActivity.class);
            startActivity(intent);
            finish();
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Aviso");
            builder.setMessage(R.string.conexaoComInternet);
            builder.setCancelable(true);
            builder.setIcon(R.mipmap.ic_ajuda);
            builder.setPositiveButton("Volta", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Intent trocarDeTela;
                    trocarDeTela = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(trocarDeTela);
                    finish();

                }
            });
            builder.setNegativeButton("Fechar App", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });
            alert = builder.create();
            alert.show();
        }


    }

    /** Abre tela de ajuda com informações sobre o app (sobre). */
    public void sobreoaPP(View view) {
        // Objeto para troca de dados entre activitys
        Bundle bundle = new Bundle();
        // setando a informação utilizadas na classe ajuda para abrir o site correspondente
        bundle.putString("icone", "sobre");
        bundle.putString("activity", "main");
        // chamando a classe de ajuda
        Intent intent = new Intent(MainActivity.this, Ajuda.class);
        // passando o obj para a intent
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }


    /** Compartilha o link do app na Play Store. */
    public void shared(View view) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=app.kizen.eletricista");
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}