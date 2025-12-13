package app.kizen.eletricista.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import app.kizen.eletricista.R;

public class PowerConceptActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_concept);

        Toolbar toolbar = findViewById(R.id.toolbar_power);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Conceito de Potência");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    public void openResistanceConcept(View view) {
        Intent intent = new Intent(this, ResistanceConceptActivity.class);
        startActivity(intent);
    }

    public void openCurrentConcept(View view) {
        Intent intent = new Intent(this, CurrentConceptActivity.class);
        startActivity(intent);
    }
}
