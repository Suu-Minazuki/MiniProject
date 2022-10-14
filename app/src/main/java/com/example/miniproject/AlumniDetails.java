package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class AlumniDetails extends AppCompatActivity {

    private ImageView alumniI;
    private TextView alumniE, alumniN, alumniD, alumniY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_details);

        alumniI = findViewById(R.id.alumni_image);
        alumniE = findViewById(R.id.alumniEmail);
        alumniN =findViewById(R.id.alumni_name);
        alumniD = findViewById(R.id.alumni_dept);
        alumniY =findViewById(R.id.alumni_graduate_year);

        Picasso.get().load(getIntent().getStringExtra("ALUMNI_IMAGE")).placeholder(R.drawable.ic_baseline_image_24).into(alumniI);
        alumniE.setText(getIntent().getStringExtra("ALUMNI_EMAIL"));
        alumniN.setText(getIntent().getStringExtra("ALUMNI_NAME"));
        alumniD.setText(getIntent().getStringExtra("ALUMNI_DEPARTMENT"));
        alumniY.setText(getIntent().getStringExtra("ALUMNI_YEAR")+ "");
    }
}