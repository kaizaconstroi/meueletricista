package app.kizen.eletricista.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import app.kizen.eletricista.R;

public class CalculationsHubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculations_hub);

        Toolbar toolbar = findViewById(R.id.toolbar_calc_hub);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.calc_hub_title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    public void openCorrente(android.view.View view) {
        startActivity(new Intent(this, CorrenteCircuitoActivity.class));
    }

    public void openPotencia(android.view.View view) {
        startActivity(new Intent(this, PotenciaActivity.class));
    }

    public void openQueda(android.view.View view) {
        startActivity(new Intent(this, QuedadeTensaoActivity.class));
    }

    public void openCondutor(android.view.View view) {
        startActivity(new Intent(this, CondutorActivity.class));
    }

    public void openCondutorDisjuntor(android.view.View view) {
        startActivity(new Intent(this, CondutorCompletoctivity.class));
    }

    public void openDisjuntor(android.view.View view) {
        startActivity(new Intent(this, DisjuntorActivity.class));
    }

    public void openGerador(android.view.View view) {
        startActivity(new Intent(this, app.kizen.eletricista.GeradorActivity.class));
    }
}
