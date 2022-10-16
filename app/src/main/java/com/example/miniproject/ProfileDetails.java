package com.example.miniproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.Model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileDetails extends AppCompatActivity {

    private CircleImageView profileImage;
    private TextView profileName, profileDepartment, profileYear, profileDescription, profileEmail, profileJob, profileType;
    private ImageButton editP, logBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public String orgName, orgDept, orgType, orgImage;
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_profile);

        profileImage = findViewById(R.id.profileImage);
        profileName = findViewById(R.id.profileName);
        profileDepartment = findViewById(R.id.profileDepartment);
        profileYear = findViewById(R.id.profileYear);
        profileDescription = findViewById(R.id.profileDescription);
        profileEmail = findViewById(R.id.profileEmail);
        profileType = findViewById(R.id.profileType);
        profileJob = findViewById(R.id.profileJob);
        editP = findViewById(R.id.editBtn);
        logBtn = findViewById(R.id.logBtn);

        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
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

                    SharedPreferences sharedPreferences = ProfileDetails.this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
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
                startActivity(new Intent(ProfileDetails.this, ProfileDetails.class));
            }
        });

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = ProfileDetails.this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                startActivity(new Intent(ProfileDetails.this, LoginPage.class));
                finish();
            }
        });

    }
}