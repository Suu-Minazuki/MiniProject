package com.example.miniproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.Model.AlumniDetails;
import com.example.miniproject.Model.EventWithData;
import com.example.miniproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlumniAdapter extends RecyclerView.Adapter<AlumniAdapter.myViewHolder> {

    Context context;
    ArrayList<AlumniDetails> aList;

    public AlumniAdapter(Context context, ArrayList<AlumniDetails> aList) {
        this.context = context;
        this.aList = aList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.alumni_cardview,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        AlumniDetails alumniDetails = aList.get(position);

        Picasso.get().load(alumniDetails.getAlumniImage()).placeholder(R.drawable.ic_baseline_image_24).into(holder.alumniI);

        holder.alumniN.setText(alumniDetails.getAlumniName());
        holder.alumniD.setText(alumniDetails.getAlumniDepartment());
        holder.alumniY.setText(alumniDetails.getAlumniYear() + "");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, com.example.miniproject.AlumniDetails.class);
                intent.putExtra("ALUMNI_IMAGE", alumniDetails.getAlumniImage());
                intent.putExtra("ALUMNI_NAME", alumniDetails.getAlumniName());
                intent.putExtra("ALUMNI_DEPARTMENT", alumniDetails.getAlumniDepartment());
                intent.putExtra("ALUMNI_YEAR", alumniDetails.getAlumniYear() + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return aList.size();
    }


    public static class myViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView alumniN, alumniD, alumniY;
        ImageView alumniI;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.alumniClicked);

            alumniN = itemView.findViewById(R.id.alumni_name);
            alumniD = itemView.findViewById(R.id.alumni_department);
            alumniY = itemView.findViewById(R.id.alumni_batch);
            alumniI = itemView.findViewById(R.id.alumni_cardview_photo);

        }
    }

}
