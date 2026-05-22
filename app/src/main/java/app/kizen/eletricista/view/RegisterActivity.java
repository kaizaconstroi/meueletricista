package app.kizen.eletricista.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.widget.ImageButton;
import com.google.firebase.firestore.FirebaseFirestore;
import app.kizen.eletricista.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtAddress, edtPhone, edtPassword;
    private EditText edtPasswordConfirm;
    private Button btnRegister;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ImageButton btnTogglePassword, btnTogglePasswordConfirm;
    private boolean passwordVisible = false;
    private boolean passwordConfirmVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        edtPasswordConfirm = findViewById(R.id.edtPasswordConfirm);
        btnTogglePassword = findViewById(R.id.btnTogglePassword);
        btnTogglePasswordConfirm = findViewById(R.id.btnTogglePasswordConfirm);

        btnTogglePassword.setOnClickListener(v -> togglePasswordVisibility());
        btnTogglePasswordConfirm.setOnClickListener(v -> togglePasswordConfirmVisibility());
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String passwordConfirm = edtPasswordConfirm.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            if (name.isEmpty()) edtName.setError("Obrigatório");
            if (email.isEmpty()) edtEmail.setError("Obrigatório");
            if (password.isEmpty()) edtPassword.setError("Obrigatório");
            if (passwordConfirm.isEmpty()) edtPasswordConfirm.setError("Obrigatório");
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(passwordConfirm)) {
            edtPasswordConfirm.setError("Senhas não coincidem");
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            edtPassword.setError("Senha deve ter ao menos 6 caracteres");
            Toast.makeText(this, "Senha muito curta", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        String uid = firebaseUser.getUid();
                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name);
                        user.put("email", email);
                        user.put("address", address);
                        user.put("phone", phone);

                        db.collection("users").document(uid)
                            .set(user)
                            .addOnCompleteListener(writeTask -> {
                                if (writeTask.isSuccessful()) {
                                    // send verification email
                                    firebaseUser.sendEmailVerification()
                                        .addOnCompleteListener(emailTask -> {
                                            progressBar.setVisibility(View.GONE);
                                            if (emailTask.isSuccessful()) {
                                                Toast.makeText(this, "Cadastro realizado. Enviamos um email de verificação.", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(this, "Cadastro salvo, mas não foi possível enviar verificação.", Toast.LENGTH_LONG).show();
                                            }
                                            mAuth.signOut();
                                            startActivity(new Intent(this, LoginActivity.class));
                                            finish();
                                        });
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    String msg = writeTask.getException() != null ? writeTask.getException().getMessage() : "Erro ao salvar perfil";
                                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                                }
                            });
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    String msg = task.getException() != null ? task.getException().getMessage() : "Erro no cadastro";
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                }
            });
    }

    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            edtPassword.setTransformationMethod(null);
            btnTogglePassword.setImageResource(R.drawable.ic_visibility_off);
        } else {
            edtPassword.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
            btnTogglePassword.setImageResource(R.drawable.ic_visibility);
        }
        edtPassword.setSelection(edtPassword.getText().length());
    }

    private void togglePasswordConfirmVisibility() {
        passwordConfirmVisible = !passwordConfirmVisible;
        if (passwordConfirmVisible) {
            edtPasswordConfirm.setTransformationMethod(null);
            btnTogglePasswordConfirm.setImageResource(R.drawable.ic_visibility_off);
        } else {
            edtPasswordConfirm.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
            btnTogglePasswordConfirm.setImageResource(R.drawable.ic_visibility);
        }
        edtPasswordConfirm.setSelection(edtPasswordConfirm.getText().length());
    }
}
