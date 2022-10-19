package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class AlumniDetails extends AppCompatActivity {

    private ImageView alumniI, imageView2;
    private TextView alumniN, alumniD, alumniY;
    private AppCompatImageButton appCompatImageButton;
    private AppCompatButton appCompatButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alumni_details);

        alumniI = findViewById(R.id.alumni_image);
        alumniN =findViewById(R.id.alumni_name);
        alumniD = findViewById(R.id.alumni_dept);
        alumniY =findViewById(R.id.alumni_graduate_year);
        appCompatImageButton = findViewById(R.id.appCompatImageButton);
        appCompatButton2 = findViewById(R.id.appCompatButton2);
        imageView2 = findViewById(R.id.imageView2);

        Picasso.get().load(getIntent().getStringExtra("ALUMNI_BIMAGE")).placeholder(R.drawable.ic_baseline_image_24).into(imageView2);
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

        appCompatButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+getIntent().getStringExtra("ALUMNI_EMAIL")));
                startActivity(intent);
            }
        });
    }
}