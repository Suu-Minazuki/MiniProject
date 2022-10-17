package com.example.miniproject.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.EventDetails;
import com.example.miniproject.Model.EventWithData;
import com.example.miniproject.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
        View v = LayoutInflater.from(context).inflate(R.layout.event_card,parent,false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        EventWithData eventDataModel = list.get(position);

        Picasso.get().load(eventDataModel.getEvent_image()).placeholder(R.drawable.ic_baseline_image_24).into(holder.eventImage);
        Picasso.get().load(eventDataModel.getOrg_Image()).placeholder(R.drawable.ic_baseline_image_24).into(holder.orgImage);

        holder.event_name.setText(eventDataModel.getEvent_name());
        holder.event_venue.setText(eventDataModel.getEvent_venue());
        holder.eventDay.setText(eventDataModel.getEvent_day());
        switch (eventDataModel.getEvent_month()){
            case "0":
                holder.eventMonth.setText("JAN");
                break;
            case "1":
                holder.eventMonth.setText("FEB");
                break;
            case "2":
                holder.eventMonth.setText("MAR");
                break;
            case "3":
                holder.eventMonth.setText("APR");
                break;
            case "4":
                holder.eventMonth.setText("MAY");
                break;
            case "5":
                holder.eventMonth.setText("JUN");
                break;
            case "6":
                holder.eventMonth.setText("JUL");
                break;
            case "7":
                holder.eventMonth.setText("AUG");
                break;
            case "8":
                holder.eventMonth.setText("SEP");
                break;
            case "9":
                holder.eventMonth.setText("OCT");
                break;
            case "10":
                holder.eventMonth.setText("NOV");
                break;
            case "11":
                holder.eventMonth.setText("DEC");
                break;
        }
        holder.eventTime.setText(eventDataModel.getEvent_time());
        holder.orgName.setText(eventDataModel.getOrg_name());

        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetails.class);

                intent.putExtra("EVENT_IMAGE", eventDataModel.getEvent_image());
                intent.putExtra("EVENT_NAME", eventDataModel.getEvent_name());
                intent.putExtra("EVENT_DESCRIPTION", eventDataModel.getEvent_description());
                intent.putExtra("EVENT_VENUE", eventDataModel.getEvent_venue());
                intent.putExtra("EVENT_DAY", eventDataModel.getEvent_day());
                intent.putExtra("EVENT_MONTH", eventDataModel.getEvent_month());
                intent.putExtra("EVENT_YEAR", eventDataModel.getEvent_Year());
                intent.putExtra("EVENT_TIME", eventDataModel.getEvent_time());
                intent.putExtra("EVENT_ADD", eventDataModel.getEvent_add());
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

        TextView event_name, event_venue, eventDay, eventMonth, eventTime, orgName;
        ShapeableImageView eventImage;
        CircleImageView orgImage;
        CardView click;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            click = itemView.findViewById(R.id.click);
            orgImage = itemView.findViewById(R.id.OrgImage);
            event_name = itemView.findViewById(R.id.eventName);
            event_venue = itemView.findViewById(R.id.eventVenue);
            eventImage = itemView.findViewById(R.id.eventImage);
            eventDay = itemView.findViewById(R.id.eventDay);
            eventMonth = itemView.findViewById(R.id.eventMonth);
            eventTime = itemView.findViewById(R.id.eventTime);
            orgName = itemView.findViewById(R.id.OrgName);


        }
    }

}
