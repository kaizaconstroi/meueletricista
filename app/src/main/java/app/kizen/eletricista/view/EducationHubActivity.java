package app.kizen.eletricista.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import app.kizen.eletricista.R;

public class EducationHubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_hub);

        Toolbar toolbar = findViewById(R.id.toolbar_education_hub);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Educação");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    public void openTeoria(android.view.View view) {
        startActivity(new Intent(this, LearnActivity.class));
    }

    public void openSeguranca(android.view.View view) {
        startActivity(new Intent(this, SafetyActivity.class));
    }

    public void openPratica(android.view.View view) {
        startActivity(new Intent(this, PracticeActivity.class));
    }

    public void openLightingIntro(android.view.View view) {
        startActivity(new Intent(this, LightingIntroActivity.class));
    }
}
