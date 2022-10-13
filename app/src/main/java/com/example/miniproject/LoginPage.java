package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginPage extends AppCompatActivity {

    private TextView textView;
    private EditText email_eT, password_eT;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        final EditText email = findViewById(R.id.email_eT);
        final EditText password = findViewById(R.id.password_eT);

        email_eT = findViewById(R.id.email_eT);
        password_eT = findViewById(R.id.password_eT);


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
        //String name = email_eT.getText().toString();
        //root.setValue(name);
    }
}