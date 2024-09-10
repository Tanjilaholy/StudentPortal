package com.nub.studentportal.ui.activity.user;

import static com.nub.studentportal.utils.prempdr.fullScreen;
import static com.nub.studentportal.utils.prempdr.openActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.nub.studentportal.R;
import com.nub.studentportal.databinding.ActivityLoginBinding;
import com.nub.studentportal.ui.activity.MainActivity;
import com.nub.studentportal.ui.activity.admin.adminActivity;

import java.util.Objects;

public class loginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fullScreen(this);

        firebaseAuth = FirebaseAuth.getInstance();
        binding.linkRegister.setOnClickListener(view -> openActivity(loginActivity.this, registerActivity.class));
        binding.linkForgotPassword.setOnClickListener(view -> openActivity(loginActivity.this, forgotPasswordActivity.class));

        binding.btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String umail = binding.userMail.getText().toString();
        String upassword = binding.userPassword.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(umail, upassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (Objects.equals(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail(), "admin@studentportal.cse")) {
                    openActivity(loginActivity.this, adminActivity.class);
                } else {
                    openActivity(loginActivity.this, MainActivity.class);
                }
            } else {
                String errorCode = ((FirebaseAuthException) Objects.requireNonNull(task.getException())).getErrorCode();

                switch (errorCode) {
                    case "ERROR_INVALID_CUSTOM_TOKEN":
                        Toast.makeText(loginActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_CUSTOM_TOKEN_MISMATCH":
                        Toast.makeText(loginActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_INVALID_CREDENTIAL":
                        Toast.makeText(loginActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_INVALID_EMAIL":
                        Toast.makeText(loginActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                        binding.userMail.setError("The email address is badly formatted.");
                        binding.userMail.requestFocus();
                        break;

                    case "ERROR_WRONG_PASSWORD":
                        Toast.makeText(loginActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                        binding.userPassword.setError("password is incorrect ");
                        binding.userPassword.requestFocus();
                        binding.userPassword.setText("");
                        break;

                    case "ERROR_USER_MISMATCH":
                        Toast.makeText(loginActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_REQUIRES_RECENT_LOGIN":
                        Toast.makeText(loginActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                        Toast.makeText(loginActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_EMAIL_ALREADY_IN_USE":
                        Toast.makeText(loginActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                        binding.userMail.setError("The email address is already in use by another account.");
                        binding.userMail.requestFocus();
                        break;

                    case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                        Toast.makeText(loginActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_USER_DISABLED":
                        Toast.makeText(loginActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_USER_TOKEN_EXPIRED":
                        Toast.makeText(loginActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_USER_NOT_FOUND":
                        Toast.makeText(loginActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_INVALID_USER_TOKEN":
                        Toast.makeText(loginActivity.this, "The user\\'s credential is no longer valid. The user must sign in again!", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_OPERATION_NOT_ALLOWED":
                        Toast.makeText(loginActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_WEAK_PASSWORD":
                        Toast.makeText(loginActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                        binding.userPassword.setError("The password is invalid it must 6 characters at least");
                        binding.userPassword.requestFocus();
                        break;
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            if (Objects.equals(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail(), "admin@studentportal.cse")) {
                openActivity(loginActivity.this, adminActivity.class);
            } else {
                openActivity(loginActivity.this, MainActivity.class);
            }
        }
    }
}