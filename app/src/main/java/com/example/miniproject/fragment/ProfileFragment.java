package com.example.miniproject.fragment;

import android.content.Context;
import android.content.Intent;
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

import com.example.miniproject.EditEvent;
import com.example.miniproject.EditProfile;
import com.example.miniproject.LoginPage;
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
    private TextView profileName, profileDepartment, profileYear, profileDescription, profileEmail, profileJob, profileType;
    private ImageButton editP, logBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public String orgName, orgDept, orgType, orgImage;
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
        profileType = view.findViewById(R.id.profileType);
        profileJob = view.findViewById(R.id.profileJob);
        editP = view.findViewById(R.id.editBtn);
        logBtn = view.findViewById(R.id.logBtn);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String eMail = sharedPreferences.getString("email", "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        Query query = databaseReference.orderByChild("userEmail").equalTo(eMail);
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
                    profileDescription.setText(userModel.getDescription());
                    profileEmail.setText(userModel.getUserEmail());
                    profileType.setText(userModel.getUserType());
                    profileJob.setText(userModel.getUserJob());

                    orgImage = userModel.getUserImage();
                    orgName = userModel.getUserName();
                    orgDept = userModel.getUserDepartment();
                    orgType = userModel.getUserType();

                    SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Name", orgName);
                    editor.putString("Department", orgDept);
                    editor.putString("Type", orgType);
                    editor.putString("Image", orgImage);
                    editor.apply();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        editP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditProfile.class));
            }
        });

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                startActivity(new Intent(getContext(), LoginPage.class));
                getActivity().finish();
            }
        });

        return view;
    }
}