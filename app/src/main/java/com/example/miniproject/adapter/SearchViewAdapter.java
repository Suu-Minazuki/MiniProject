package com.example.miniproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.Model.UserModel;
import com.example.miniproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewAdapter.myViewHolder> {

    Context context;
    ArrayList<UserModel> list;

    public SearchViewAdapter(Context context, ArrayList<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setfiltered(ArrayList<UserModel> filteredlist){
        this.list = filteredlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_result, parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewAdapter.myViewHolder holder, int position) {

        UserModel userModel = list.get(position);

        Picasso.get().load(userModel.getUserImage()).placeholder(R.drawable.ic_baseline_image_24).into(holder.userI);

        holder.userN.setText(userModel.getUserName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView userI;
        private TextView userN;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            userI  = itemView.findViewById(R.id.userImage);
            userN = itemView.findViewById(R.id.userName);
        }
    }
}
