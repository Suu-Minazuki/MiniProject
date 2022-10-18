package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class AlumniDetails extends AppCompatActivity {

    private ImageView alumniI;
    private TextView alumniN, alumniD, alumniY;
    private AppCompatImageButton appCompatImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alumni_details);

        alumniI = findViewById(R.id.alumni_image);
        alumniN =findViewById(R.id.alumni_name);
        alumniD = findViewById(R.id.alumni_dept);
        alumniY =findViewById(R.id.alumni_graduate_year);
        appCompatImageButton = findViewById(R.id.appCompatImageButton);

        Picasso.get().load(getIntent().getStringExtra("ALUMNI_IMAGE")).placeholder(R.drawable.ic_baseline_image_24).into(alumniI);
        alumniN.setText(getIntent().getStringExtra("ALUMNI_NAME"));
        alumniD.setText(getIntent().getStringExtra("ALUMNI_DEPARTMENT"));
        alumniY.setText(getIntent().getStringExtra("ALUMNI_YEAR")+ "");

        appCompatImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}