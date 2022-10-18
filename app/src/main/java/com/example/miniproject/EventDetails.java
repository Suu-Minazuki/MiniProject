package com.example.miniproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;

public class EventDetails extends AppCompatActivity {

    private ImageView eventI, eventOI;
    private TextView eventN, eventDe, eventDa, eventV, eventON,eventOT;
    private AppCompatImageButton appCompatImageButton;
    private CardView orgCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_event);

        eventI = findViewById(R.id.eventImage);
        eventN = findViewById(R.id.eventName);
        eventDe = findViewById(R.id.eventDescription);
        eventDa = findViewById(R.id.textView6);
        eventV = findViewById(R.id.eventVenue);
        eventOI = findViewById(R.id.OrgImage);
        eventON = findViewById(R.id.OrgName);
        eventOT = findViewById(R.id.OrgType);
        appCompatImageButton = findViewById(R.id.appCompatImageButton);
        orgCard = findViewById(R.id.orgCard);

        Picasso.get().load(getIntent().getStringExtra("EVENT_IMAGE")).placeholder(R.drawable.ic_launcher_background).into(eventI);
        eventN.setText(getIntent().getStringExtra("EVENT_NAME"));
        eventDe.setText(getIntent().getStringExtra("EVENT_DESCRIPTION"));
        eventV.setText(getIntent().getStringExtra("EVENT_VENUE"));
        Picasso.get().load(getIntent().getStringExtra("EVENT_ORGI")).placeholder(R.drawable.ic_launcher_background).into(eventOI);
        eventON.setText(getIntent().getStringExtra("EVENT_ORGN"));
        eventOT.setText(getIntent().getStringExtra("EVENT_ORGT"));

        appCompatImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        orgCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
