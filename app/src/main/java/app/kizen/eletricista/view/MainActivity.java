package app.kizen.eletricista.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import app.kizen.eletricista.R;
import app.kizen.eletricista.api.AppUtil;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * Activity principal: navegação para calculadoras e política de privacidade.
     * Também oferece compartilhamento do link do app na Play Store.
     */
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    
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
        
        // Inicializar componentes
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        
        // Configurar toggle do drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        
        // Marcar o item Início como selecionado
        navigationView.setCheckedItem(R.id.nav_home);
        
        restaurarSharedPreferences();
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Fechar o drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        
        // Tratar navegação
        int id = item.getItemId();
        
        if (id == R.id.nav_home) {
            // Já está na home
            return true;
        } else if (id == R.id.nav_corrente) {
            CorrenteEletrica(null);
        } else if (id == R.id.nav_potencia) {
            Potencia(null);
        } else if (id == R.id.nav_queda_tensao) {
            QuedaDeTensao(null);
        } else if (id == R.id.nav_condutor) {
            SecaoCondutor(null);
        } else if (id == R.id.nav_disjuntor) {
            Disjuntor(null);
        } else if (id == R.id.nav_condutor_disjuntor) {
            CondutoreDisjuntor(null);
        } else if (id == R.id.nav_gerador) {
            Gerador(null);
        } else if (id == R.id.nav_consumo) {
            Consumo(null);
        } else if (id == R.id.nav_compartilhar) {
            shared(null);
        } else if (id == R.id.nav_sobre) {
            sobreoaPP(null);
        } else if (id == R.id.nav_privacidade) {
            PoliticadePrivacidade(null);
        }
        
        return true;
    }
    
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    public void Gerador(View view) {
        Intent intent = new Intent(MainActivity.this, app.kizen.eletricista.GeradorActivity.class);
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

    /** Abre o app de email com destinatário e assunto preenchidos. */
    public void contactEmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(android.net.Uri.parse("mailto:kaizaconstroi@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Voltrix - Suporte e novas funcionalidades");
        // Opcional: corpo inicial
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Olá, tenho uma dúvida/sugestão sobre o app Voltrix:");

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email"));
        } catch (android.content.ActivityNotFoundException ex) {
            // Nenhum app de email instalado
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Aviso");
            builder.setMessage("Nenhum aplicativo de email encontrado.");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        }
    }
}