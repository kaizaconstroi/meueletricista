package app.kizen.eletricista.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import app.kizen.eletricista.R;

public class PracticalGuideActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_guide);

        Toolbar toolbar = findViewById(R.id.toolbar_practical_guide);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.practical_guide_title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    public void openCalculos(android.view.View view) {
        startActivity(new Intent(this, CalculationsHubActivity.class));
    }

    public void openCorrente(android.view.View view) {
        startActivity(new Intent(this, CorrenteCircuitoActivity.class));
    }

    public void openCondutor(android.view.View view) {
        startActivity(new Intent(this, CondutorActivity.class));
    }

    public void openCondutorDisjuntor(android.view.View view) {
        startActivity(new Intent(this, CondutorCompletoctivity.class));
    }
}
