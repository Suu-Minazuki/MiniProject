package com.example.miniproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.Model.UserModel;
import com.example.miniproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlumniAdapter extends RecyclerView.Adapter<AlumniAdapter.myViewHolder> {

    Context context;
    ArrayList<UserModel> aList;

    public AlumniAdapter(Context context, ArrayList<UserModel> aList) {
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

        UserModel userModel = aList.get(position);

        Picasso.get().load(userModel.getUserImage()).placeholder(R.drawable.ic_baseline_image_24).into(holder.alumniI);

        holder.alumniN.setText(userModel.getUserName());
        holder.alumniD.setText(userModel.getUserDepartment());
        holder.alumniY.setText(userModel.getUserYear() + "");
        holder.firstView.setBackgroundColor(Color.parseColor(userModel.getUserColor()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, com.example.miniproject.AlumniDetails.class);
                intent.putExtra("ALUMNI_BIMAGE", userModel.getUserBImage());
                intent.putExtra("ALUMNI_IMAGE", userModel.getUserImage());
                intent.putExtra("ALUMNI_EMAIL", userModel.getUserEmail());
                intent.putExtra("ALUMNI_NAME", userModel.getUserName());
                intent.putExtra("ALUMNI_DEPARTMENT", userModel.getUserDepartment());
                intent.putExtra("ALUMNI_YEAR", userModel.getUserYear() + "");
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
        View firstView;
        View root;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.alumniClicked);
            alumniN = itemView.findViewById(R.id.alumni_name);
            alumniD = itemView.findViewById(R.id.alumni_department);
            alumniY = itemView.findViewById(R.id.alumni_batch);
            alumniI = itemView.findViewById(R.id.alumni_cardview_photo);
            firstView = itemView.findViewById(R.id.first_view);

        }
    }

}
