package app.kizen.eletricista.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import app.kizen.eletricista.R;

public class ResistanceConceptActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resistance_concept);

        Toolbar toolbar = findViewById(R.id.toolbar_resistance);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Conceito de Resistência");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    public void openPowerConcept(android.view.View view) {
        android.content.Intent intent = new android.content.Intent(this, PowerConceptActivity.class);
        startActivity(intent);
    }

    public void openLearnIntro(android.view.View view) {
        android.content.Intent intent = new android.content.Intent(this, LearnActivity.class);
        startActivity(intent);
    }
}
