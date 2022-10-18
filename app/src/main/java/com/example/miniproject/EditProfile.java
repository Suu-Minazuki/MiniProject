package com.example.miniproject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.miniproject.Model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private ImageView imageView2;
    private CircleImageView alumni_image;
    private EditText alumni_name, editText2, user_job, alumni_graduate_year;
    private Spinner alumni_dept;
    private Uri selectedImageUri, selectedImageUri1;
    private AppCompatImageButton appCompatImageButton;
    private AppCompatButton save;
    private FirebaseStorage storage;
    private StorageReference uploader;
    private String email;
    private String url1, url2;
    private String[] regisCourse = {"BE in IT", "BE in Civil Engineering", "BE in ECE", "BE in ICE", "BE in ECE","BE in Engineering Geology", "B Architecture"};
    private ProgressDialog progressDialog;
    public static final String SHARED_PREFS = "sharedPrefs";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        imageView2 = findViewById(R.id.imageView2);
        alumni_image = findViewById(R.id.alumni_image);
        alumni_name = findViewById(R.id.alumni_name);
        editText2 = findViewById(R.id.editText2);
        user_job = findViewById(R.id.user_job);
        alumni_dept = findViewById(R.id.alumni_dept);
        alumni_graduate_year = findViewById(R.id.alumni_graduate_year);
        save = findViewById(R.id.save);
        appCompatImageButton = findViewById(R.id.appCompatImageButton);
        progressDialog = new ProgressDialog(this);

        ArrayAdapter aa = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, regisCourse);
        aa.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        alumni_dept.setAdapter(aa);

        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String eMail = sharedPreferences.getString("email", "");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User");
        Query query = databaseReference.orderByChild("userEmail").equalTo(eMail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    assert userModel != null;
                    //user profile image
                    if (!Objects.equals(userModel.getUserImage(), "")){
                        Picasso.get().load(userModel.getUserImage()).placeholder(R.drawable.ic_launcher_background).into(alumni_image);
                    }
                    //user background Image
                    if (!Objects.equals(userModel.getUserBImage(), "")){
                        Picasso.get().load(userModel.getUserBImage()).placeholder(R.drawable.ic_launcher_background).into(imageView2);
                    }
                    //USER NAME
                    if (!Objects.equals(userModel.getUserName(), "")){
                        alumni_name.setText(userModel.getUserName());
                    }
                    if (!Objects.equals(userModel.getDescription(), "")){
                        editText2.setText(userModel.getDescription());
                    }
                    if (!Objects.equals(userModel.getUserJob(), "")){
                        user_job.setText(userModel.getUserJob());
                    }
                    if (!Objects.equals(userModel.getUserYear(), "")){
                        alumni_graduate_year.setText(userModel.getUserYear());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        alumni_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser1();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImageUri == null){
                    if (selectedImageUri1 == null){
                        uploadtofirebase3();
                    }else{
                        uploadtofirebase2();
                    }
                }else{
                    uploadtofirebase();
                }
            }
        });

        appCompatImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfile.this, ProfileDetails.class));
                finish();
            }
        });

    }

    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            // do your operation from here....
            if (data != null && data.getData() != null) {
                selectedImageUri = data.getData();
                Bitmap selectedImageBitmap = null;
                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                imageView2.setImageBitmap(selectedImageBitmap);
            }
        }
    });

    private void imageChooser1()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity1.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            // do your operation from here....
            if (data != null && data.getData() != null) {
                selectedImageUri1 = data.getData();
                Bitmap selectedImageBitmap = null;
                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri1);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                alumni_image.setImageBitmap(selectedImageBitmap);
            }
        }
    });

    private void uploadtofirebase() {
        storage = FirebaseStorage.getInstance();
        uploader = storage.getReference("UserImage1"+new Random().nextInt(50));

        // Code for showing progressDialog while uploading
        progressDialog.show();

        // adding listeners on upload
        // or failure of image
        uploader.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {



                                // Image uploaded successfully
                                url1 = uri.toString();
                                if (selectedImageUri1 == null){
                                    uploadtofirebase3();
                                }else{
                                    uploadtofirebase2();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast.makeText(EditProfile.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void uploadtofirebase2(){
        uploader.putFile(selectedImageUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        progressDialog.dismiss();
                        url2 = uri.toString();
                        uploadtofirebase3();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(EditProfile.this, e.getMessage() +"", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            // Progress Listener for loading
            // percentage on the dialog box
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
            {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded " + (int)progress + "%");
            }
        });
    }
    public void uploadtofirebase3(){
        FirebaseDatabase  database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = database.getReference("User");
        mDatabaseRef.child("-NEX8N_BHzxVav3UFA_-").child("userBImage").setValue(url1);
        mDatabaseRef.child("-NEX8N_BHzxVav3UFA_-").child("userImage").setValue(url2);
        mDatabaseRef.child("-NEX8N_BHzxVav3UFA_-").child("userName").setValue(alumni_name.getText().toString());
        mDatabaseRef.child("-NEX8N_BHzxVav3UFA_-").child("description").setValue(editText2.getText().toString());
        mDatabaseRef.child("-NEX8N_BHzxVav3UFA_-").child("userDepartment").setValue(alumni_dept.getSelectedItem().toString());
        mDatabaseRef.child("-NEX8N_BHzxVav3UFA_-").child("userJob").setValue(user_job.getText().toString());
        mDatabaseRef.child("-NEX8N_BHzxVav3UFA_-").child("userYear").setValue(alumni_graduate_year.getText().toString());
        Toast.makeText(EditProfile.this, "Successful", Toast.LENGTH_SHORT).show();
        finish();
    }
}
