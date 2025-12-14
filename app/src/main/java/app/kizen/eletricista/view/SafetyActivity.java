package app.kizen.eletricista.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import app.kizen.eletricista.R;

public class SafetyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety);

        Toolbar toolbar = findViewById(R.id.toolbar_safety);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.practice_safety_title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> goBackToPractice());
    }

    @Override
    public void onBackPressed() {
        goBackToPractice();
    }

    public void goBackToPractice() {
        Intent intent = new Intent(this, PracticeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    public void openNextSection(android.view.View view) {
        Intent intent = new Intent(this, TrainingActivity.class);
        startActivity(intent);
        finish();
    }
}
