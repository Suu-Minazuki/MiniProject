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
    private TextView eventN, eventDe, eventDa, eventV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);

        eventI = findViewById(R.id.eventImage);
        eventN = findViewById(R.id.eventName);
        eventDe = findViewById(R.id.eventDescription);
        eventDa = findViewById(R.id.eventDate);
        eventV = findViewById(R.id.eventVenue);

        Picasso.get().load(getIntent().getStringExtra("EVENT_IMAGE")).placeholder(R.drawable.ic_baseline_image_24).into(eventI);
        eventN.setText(getIntent().getStringExtra("EVENT_NAME"));
        eventDe.setText(getIntent().getStringExtra("EVENT_DESCRIPTION"));
        eventDa.setText(getIntent().getStringExtra("EVENT_DATE")+ "");
        eventV.setText(getIntent().getStringExtra("EVENT_VENUE")+ "");

    }
}
