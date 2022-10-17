package com.example.miniproject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class EventDetails extends AppCompatActivity {

    private ImageView eventI, eventOI;
    private TextView eventN, eventDe, eventDa, eventV, eventON,eventOD,eventOT;

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

        Picasso.get().load(getIntent().getStringExtra("EVENT_IMAGE")).placeholder(R.drawable.ic_launcher_background).into(eventI);
        eventN.setText(getIntent().getStringExtra("EVENT_NAME"));
        eventDe.setText(getIntent().getStringExtra("EVENT_DESCRIPTION"));
        eventV.setText(getIntent().getStringExtra("EVENT_VENUE"));
        Picasso.get().load(getIntent().getStringExtra("EVENT_ORGI")).placeholder(R.drawable.ic_launcher_background).into(eventOI);
        eventON.setText(getIntent().getStringExtra("EVENT_ORGN"));
        eventOT.setText(getIntent().getStringExtra("EVENT_ORGT"));

    }
}
