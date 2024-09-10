package com.nub.studentportal.ui.activity;

import static com.nub.studentportal.utils.prempdr.fullScreen;
import static com.nub.studentportal.utils.prempdr.openActivityBack;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.nub.studentportal.R;
import com.nub.studentportal.databinding.ActivityMainBinding;
import com.nub.studentportal.ui.activity.user.loginActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fullScreen(this);
        firebaseAuth = FirebaseAuth.getInstance();

        binding.btnLogout.setOnClickListener(view -> logOut());
    }

    private void logOut() {
        firebaseAuth.signOut();
        Toast.makeText(MainActivity.this, "Logout Successfully!", Toast.LENGTH_SHORT).show();
        openActivityBack(MainActivity.this, loginActivity.class);
    }
}