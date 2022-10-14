package com.example.miniproject.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniproject.Model.EventWithData;
import com.example.miniproject.Model.UserModel;
import com.example.miniproject.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private CircleImageView profileImage;
    private TextView profileName, profileDepartment, profileYear, profileDescription, profileEmail;
    private ImageButton editP, logBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = view.findViewById(R.id.profileImage);
        profileName = view.findViewById(R.id.profileName);
        profileDepartment = view.findViewById(R.id.profileDepartment);
        profileYear = view.findViewById(R.id.profileYear);
        profileDescription = view.findViewById(R.id.profileDescription);
        profileEmail = view.findViewById(R.id.profileEmail);
        editP = view.findViewById(R.id.editBtn);
        logBtn = view.findViewById(R.id.logBtn);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String eMail = sharedPreferences.getString("email", "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        Query query = databaseReference.child("Student").orderByChild("userEmail").equalTo(eMail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    assert userModel != null;
                    Picasso.get().load(userModel.getUserImage()).placeholder(R.drawable.ic_baseline_image_24).into(profileImage);
                    profileName.setText(userModel.getUserName());
                    profileDepartment.setText(userModel.getUserDepartment());
                    profileYear.setText(userModel.getUserYear());
                    if (userModel.getDescription() == "Description"){
                        profileDescription.setText("No Description");
                    }else {
                        profileDescription.setText(userModel.getDescription());
                    }
                    profileEmail.setText(userModel.getUserEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        editP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        return view;
    }
}