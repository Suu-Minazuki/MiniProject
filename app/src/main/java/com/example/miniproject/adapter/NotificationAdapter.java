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

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.myViewHolder> {

    private Context context;
    private ArrayList<EventWithData> list;

    public NotificationAdapter(Context context, ArrayList<EventWithData> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notification_layout_design,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        EventWithData eventDataModel = list.get(position);
        Picasso.get().load(eventDataModel.getOrg_Image()).placeholder(R.drawable.ic_baseline_image_24).into(holder.notImage);
        holder.notOrg.setText(eventDataModel.getOrg_name() + " has organized " + eventDataModel.getEvent_name() + " on "+ eventDataModel.getEvent_day() + ":" + eventDataModel.getEvent_month() + ":" + eventDataModel.getEvent_Year());
        holder.notEvent.setText(eventDataModel.getEvent_add());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        private ImageView notImage;
        private TextView notOrg, notEvent;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            notImage = itemView.findViewById(R.id.notImage);
            notOrg = itemView.findViewById(R.id.notOrg);
            notEvent = itemView.findViewById(R.id.notEvent);

        }
    }

}
