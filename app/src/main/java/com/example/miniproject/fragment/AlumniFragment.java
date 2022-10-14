package com.example.miniproject.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miniproject.Model.AlumniDetails;
import com.example.miniproject.Model.EventWithData;
import com.example.miniproject.Model.UserModel;
import com.example.miniproject.R;
import com.example.miniproject.adapter.AlumniAdapter;
import com.example.miniproject.adapter.EventAdapterClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AlumniFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference database;
    AlumniAdapter alumniAdapter;
    ArrayList<UserModel> aList;

    public AlumniFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_alumni, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        database = FirebaseDatabase.getInstance().getReference("User");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        aList = new ArrayList<>();
        alumniAdapter = new AlumniAdapter(getContext(), aList);
        recyclerView.setAdapter(alumniAdapter);
        Query query = database.orderByChild("userType").equalTo("Alumni");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    aList.add(userModel);
                }
                alumniAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}