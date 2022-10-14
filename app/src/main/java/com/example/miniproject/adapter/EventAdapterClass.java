package com.example.miniproject.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.Model.EventWithData;
import com.example.miniproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventAdapterClass extends RecyclerView.Adapter<EventAdapterClass.myviewholder> {

    Context context;
    ArrayList<EventWithData> list = new ArrayList<>();

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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder{

        TextView event_date, event_name, event_description, event_venue;
        ImageView event_image;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            event_date = itemView.findViewById(R.id.event_date);
            event_name = itemView.findViewById(R.id.event_name);
            event_description = itemView.findViewById(R.id.event_description);
            event_venue = itemView.findViewById(R.id.event_venue);
            event_image = itemView.findViewById(R.id.image);


        }
    }

}
