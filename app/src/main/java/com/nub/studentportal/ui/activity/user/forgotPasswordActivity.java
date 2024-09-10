package com.nub.studentportal.ui.activity.user;

import static com.nub.studentportal.utils.prempdr.fullScreen;
import static com.nub.studentportal.utils.prempdr.openActivity;
import static com.nub.studentportal.utils.prempdr.openActivityBack;

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
import com.nub.studentportal.databinding.ActivityForgotPasswordBinding;
import com.nub.studentportal.ui.activity.MainActivity;
import com.nub.studentportal.ui.activity.admin.adminActivity;

import java.util.Objects;

public class forgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fullScreen(this);

        firebaseAuth = FirebaseAuth.getInstance();
        binding.btnReset.setOnClickListener(view -> forgotPassword());
    }

    private void forgotPassword() {
        String umail = binding.userMail.getText().toString();
        firebaseAuth.sendPasswordResetEmail(umail)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(forgotPasswordActivity.this, "Please check your Email", Toast.LENGTH_SHORT).show();
                        openActivityBack(forgotPasswordActivity.this, loginActivity.class);
                    } else {
                        String errorCode = ((FirebaseAuthException) Objects.requireNonNull(task.getException())).getErrorCode();

                        switch (errorCode) {
                            case "ERROR_INVALID_EMAIL":
                                Toast.makeText(forgotPasswordActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                binding.userMail.setError("The email address is badly formatted.");
                                binding.userMail.requestFocus();
                                break;

                            case "ERROR_USER_MISMATCH":
                                Toast.makeText(forgotPasswordActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_USER_DISABLED":
                                Toast.makeText(forgotPasswordActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_USER_NOT_FOUND":
                                Toast.makeText(forgotPasswordActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_OPERATION_NOT_ALLOWED":
                                Toast.makeText(forgotPasswordActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
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
                openActivity(forgotPasswordActivity.this, adminActivity.class);
            } else {
                openActivity(forgotPasswordActivity.this, MainActivity.class);
            }
        }
    }
}