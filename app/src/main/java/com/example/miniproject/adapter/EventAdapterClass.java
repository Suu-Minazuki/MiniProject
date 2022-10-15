package com.example.miniproject.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.EventDetails;
import com.example.miniproject.Model.EventWithData;
import com.example.miniproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventAdapterClass extends RecyclerView.Adapter<EventAdapterClass.myviewholder> {

    Context context;
    ArrayList<EventWithData> list;

    public EventAdapterClass(Context context, ArrayList<EventWithData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.event_card_design,parent,false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        EventWithData eventDataModel = list.get(position);

        Picasso.get().load(eventDataModel.getEvent_image()).placeholder(R.drawable.ic_baseline_image_24).into(holder.event_image);

        holder.event_name.setText(eventDataModel.getEvent_name());
        holder.event_date.setText(eventDataModel.getEvent_date());
        holder.event_description.setText(eventDataModel.getEvent_description());
        holder.event_venue.setText(eventDataModel.getEvent_venue());

        holder.eventClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetails.class);

                intent.putExtra("EVENT_IMAGE", eventDataModel.getEvent_image());
                intent.putExtra("EVENT_NAME", eventDataModel.getEvent_name());
                intent.putExtra("EVENT_DESCRIPTION", eventDataModel.getEvent_description());
                intent.putExtra("EVENT_DATE", eventDataModel.getEvent_date());
                intent.putExtra("EVENT_VENUE", eventDataModel.getEvent_venue());
                intent.putExtra("EVENT_ORGI", eventDataModel.getOrg_Image());
                intent.putExtra("EVENT_ORGN", eventDataModel.getOrg_name());
                intent.putExtra("EVENT_ORGD", eventDataModel.getOrg_dept());
                intent.putExtra("EVENT_ORGT", eventDataModel.getOrg_type());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder{

        TextView event_date, event_name, event_description, event_venue;
        ImageView event_image;
        CardView eventClick;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            eventClick = itemView.findViewById(R.id.eventClick);

            event_date = itemView.findViewById(R.id.event_date);
            event_name = itemView.findViewById(R.id.event_name);
            event_description = itemView.findViewById(R.id.event_description);
            event_venue = itemView.findViewById(R.id.event_venue);
            event_image = itemView.findViewById(R.id.image);


        }
    }

}
