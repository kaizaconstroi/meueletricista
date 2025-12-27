package app.kizen.eletricista.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import app.kizen.eletricista.R;

public class PracticeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        Toolbar toolbar = findViewById(R.id.toolbar_practice);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.practice_title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> goBackToHome());
    }

    @Override
    public void onBackPressed() {
        goBackToHome();
    }

    public void goBackToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    public void openSafetySection(android.view.View view) {
        Intent intent = new Intent(this, SafetyActivity.class);
        startActivity(intent);
    }

    public void openTrainingSection(android.view.View view) {
        Intent intent = new Intent(this, TrainingActivity.class);
        startActivity(intent);
    }

    public void openToolsSection(android.view.View view) {
        Intent intent = new Intent(this, ToolsActivity.class);
        startActivity(intent);
    }

    public void openLegalSection(android.view.View view) {
        Intent intent = new Intent(this, LegalActivity.class);
        startActivity(intent);
    }

    public void openPracticalGuide(android.view.View view) {
        Intent intent = new Intent(this, PracticalGuideActivity.class);
        startActivity(intent);
    }

    public void openGeneralSockets(android.view.View view) {
        Intent intent = new Intent(this, GeneralSocketsActivity.class);
        startActivity(intent);
    }

    public void openResidentialSizingTomadas(android.view.View view) {
        Intent intent = new Intent(this, ResidentialSizingActivity.class);
        startActivity(intent);
    }

    public void openResidentialSizingIluminacao(android.view.View view) {
        Intent intent = new Intent(this, LightingSizingActivity.class);
        startActivity(intent);
    }
}
