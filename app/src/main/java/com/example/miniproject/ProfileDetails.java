package com.example.miniproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.miniproject.Model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileDetails extends AppCompatActivity {

    public CircleImageView profileImage;
    private ImageView imageView2;
    private TextView profileName, editText2, userCourse, userJob, userBatch, profileEmail, profileJob, profileType;
    private AppCompatImageButton appCompatImageButton;
    private AppCompatButton appCompatButton, logBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public String orgName, orgDept, orgType, orgImage;
    private Intent intent;
    private UserModel userModel;
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        profileImage = findViewById(R.id.profileImage);
        profileName = findViewById(R.id.textView4);
        imageView2 = findViewById(R.id.imageView2);
        editText2 = findViewById(R.id.editText2);
        userCourse = findViewById(R.id.userCourse);
        userJob = findViewById(R.id.userJob);
        userBatch = findViewById(R.id.userBatch);
        profileType = findViewById(R.id.textView5);
        logBtn = findViewById(R.id.logBtn);
        appCompatButton = findViewById(R.id.appCompatButton);
        appCompatImageButton = findViewById(R.id.appCompatImageButton);

        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String eMail = sharedPreferences.getString("email", "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        Query query = databaseReference.orderByChild("userEmail").equalTo(eMail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    userModel = dataSnapshot.getValue(UserModel.class);
                    assert userModel != null;
                    //user profile image
                    if (!Objects.equals(userModel.getUserImage(), "")){
                        Picasso.get().load(userModel.getUserImage()).placeholder(R.drawable.ic_launcher_background).into(profileImage);
                    }
                    //user background Image
                    if (!Objects.equals(userModel.getUserBImage(), "")){
                        Picasso.get().load(userModel.getUserBImage()).placeholder(R.drawable.ic_launcher_background).into(imageView2);
                    }
                    //USER NAME
                    if (!Objects.equals(userModel.getUserName(), "")){
                        profileName.setText(userModel.getUserName());
                    }
                    //user type
                    if (!Objects.equals(userModel.getUserType(), "")){
                        profileType.setText(userModel.getUserType());
                    }
                    if (!Objects.equals(userModel.getUserDescription(), "")){
                        editText2.setText(userModel.getUserDescription());
                    }
                    if (!Objects.equals(userModel.getUserDepartment(), "")){
                        userCourse.setText(userModel.getUserDepartment());
                    }
                    if (!Objects.equals(userModel.getUserJob(), "")){
                        userJob.setText(userModel.getUserJob());
                    }
                    if (!Objects.equals(userModel.getUserYear(), "")){
                        userBatch.setText(userModel.getUserYear());
                    }

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

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileDetails.this, EditProfile.class));
            }
        });

        appCompatImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
