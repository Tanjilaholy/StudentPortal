package com.nub.studentportal.ui.activity.user;

import static com.nub.studentportal.utils.prempdr.fullScreen;
import static com.nub.studentportal.utils.prempdr.openActivityBack;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.nub.studentportal.R;
import com.nub.studentportal.databinding.ActivityRegisterBinding;

import java.util.Objects;

public class registerActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ActivityRegisterBinding binding;
    private String umail, upassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fullScreen(this);

        firebaseAuth = FirebaseAuth.getInstance();
        binding.linkLogin.setOnClickListener(view -> openActivityBack(registerActivity.this, loginActivity.class));

        binding.btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        if (binding.userMail.getText() != null && !TextUtils.isEmpty(binding.userMail.getText())) {
            umail = binding.userMail.getText().toString();
        }
        if (binding.userPassword.getText() != null && !TextUtils.isEmpty(binding.userPassword.getText())) {
            upassword = binding.userPassword.getText().toString();
        }

        firebaseAuth.createUserWithEmailAndPassword(umail, upassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                openActivityBack(registerActivity.this, loginActivity.class);
            } else {
                String errorCode = ((FirebaseAuthException) Objects.requireNonNull(task.getException())).getErrorCode();

                switch (errorCode) {
                    case "ERROR_INVALID_CUSTOM_TOKEN":
                        Toast.makeText(registerActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_CUSTOM_TOKEN_MISMATCH":
                        Toast.makeText(registerActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_INVALID_CREDENTIAL":
                        Toast.makeText(registerActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_INVALID_EMAIL":
                        Toast.makeText(registerActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                        binding.userMail.setError("The email address is badly formatted.");
                        binding.userMail.requestFocus();
                        break;

                    case "ERROR_WRONG_PASSWORD":
                        Toast.makeText(registerActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                        binding.userPassword.setError("password is incorrect ");
                        binding.userPassword.requestFocus();
                        binding.userPassword.setText("");
                        break;

                    case "ERROR_USER_MISMATCH":
                        Toast.makeText(registerActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_REQUIRES_RECENT_LOGIN":
                        Toast.makeText(registerActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                        Toast.makeText(registerActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_EMAIL_ALREADY_IN_USE":
                        Toast.makeText(registerActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                        binding.userMail.setError("The email address is already in use by another account.");
                        binding.userMail.requestFocus();
                        break;

                    case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                        Toast.makeText(registerActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_USER_DISABLED":
                        Toast.makeText(registerActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_USER_TOKEN_EXPIRED":
                        Toast.makeText(registerActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_USER_NOT_FOUND":
                        Toast.makeText(registerActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_INVALID_USER_TOKEN":
                        Toast.makeText(registerActivity.this, "The user\\'s credential is no longer valid. The user must sign in again!", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_OPERATION_NOT_ALLOWED":
                        Toast.makeText(registerActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                        break;

                    case "ERROR_WEAK_PASSWORD":
                        Toast.makeText(registerActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                        binding.userPassword.setError("The password is invalid it must 6 characters at least");
                        binding.userPassword.requestFocus();
                        break;
                }
            }
        });
    }
}