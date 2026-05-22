package app.kizen.eletricista.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import app.kizen.eletricista.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private ProgressBar progressBar;
    private ImageButton btnToggleLoginPassword;
    private View tvNoAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);
        btnToggleLoginPassword = findViewById(R.id.btnToggleLoginPassword);
        btnToggleLoginPassword.setOnClickListener(v -> toggleLoginPasswordVisibility());
        tvNoAccount = findViewById(R.id.tvNoAccount);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
            navigateToMain();
            finish();
            return;
        }

        tvNoAccount.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Preencha email e senha", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            navigateToMain();
                            finish();
                        } else {
                            if (user != null) {
                                user.sendEmailVerification();
                            }
                            mAuth.signOut();
                            Toast.makeText(this, "Verifique seu email. Enviamos um email de verificação.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String msg = task.getException() != null ? task.getException().getMessage() : "Erro de autenticação";
                        Toast.makeText(this, "Erro: " + msg, Toast.LENGTH_LONG).show();
                    }
                });
        });

    }

    private boolean loginPasswordVisible = false;

    private void toggleLoginPasswordVisibility() {
        loginPasswordVisible = !loginPasswordVisible;
        if (loginPasswordVisible) {
            edtPassword.setTransformationMethod(null);
            btnToggleLoginPassword.setImageResource(R.drawable.ic_visibility_off);
        } else {
            edtPassword.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
            btnToggleLoginPassword.setImageResource(R.drawable.ic_visibility);
        }
        edtPassword.setSelection(edtPassword.getText().length());
    }

    private void navigateToMain() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
