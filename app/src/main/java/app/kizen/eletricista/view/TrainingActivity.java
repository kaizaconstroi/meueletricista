package app.kizen.eletricista.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import app.kizen.eletricista.R;

public class TrainingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Toolbar toolbar = findViewById(R.id.toolbar_training);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.practice_training_title);
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

    public void openPreviousSection(android.view.View view) {
        Intent intent = new Intent(this, SafetyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    public void openNextSection(android.view.View view) {
        Intent intent = new Intent(this, ToolsActivity.class);
        startActivity(intent);
        finish();
    }
}
