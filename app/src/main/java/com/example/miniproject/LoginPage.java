package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.BreakIterator;

public class LoginPage extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth = FirebaseAuth.getInstance();

        final EditText email = findViewById(R.id.email_eT);
        final EditText password = findViewById(R.id.password_eT);

        textView = findViewById(R.id.textView8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpForm.class));
            }
        });
    }
    public void loginBtn(View view) {
        startActivity(new Intent(getApplicationContext(), Events.class));
    }
}