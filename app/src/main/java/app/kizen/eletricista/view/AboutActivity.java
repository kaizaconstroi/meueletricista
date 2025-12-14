package app.kizen.eletricista.view;

import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import app.kizen.eletricista.R;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.about_title);
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

    public void sendEmail(android.view.View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"contato@kaizenapps.com.br"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Dúvida sobre Meu Eletricista");
        intent.putExtra(Intent.EXTRA_TEXT, "Olá,\n\nEstou usando o app Meu Eletricista e gostaria de esclarecer uma dúvida...");
        
        try {
            startActivity(Intent.createChooser(intent, "Enviar email"));
        } catch (android.content.ActivityNotFoundException e) {
            android.widget.Toast.makeText(this, "Nenhum cliente de email configurado", android.widget.Toast.LENGTH_SHORT).show();
        }
    }
}
