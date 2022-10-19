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
import android.util.Log;
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

import com.example.miniproject.Model.EventWithData;
import com.example.miniproject.Model.UserModel;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    public String eMail;
    private String userID, userDepartment;
    private String userImageUrl, userBImageUrl;
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
        progressDialog.setMessage("Updating");


        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        eMail = sharedPreferences.getString("email", "");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User");
        Query query = databaseReference.orderByChild("userEmail").equalTo(eMail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    assert userModel != null;
                    userImageUrl = userModel.getUserImage();
                    userBImageUrl = userModel.getUserBImage();
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
                    if (!Objects.equals(userModel.getUserDescription(), "")){
                        editText2.setText(userModel.getUserDescription());
                    }
                    if (!Objects.equals(userModel.getUserJob(), "")){
                        user_job.setText(userModel.getUserJob());
                    }
                    if (!Objects.equals(userModel.getUserYear(), "")){
                        alumni_graduate_year.setText(userModel.getUserYear());
                    }
                    userDepartment = userModel.getUserDepartment();
                    userID = userModel.getUserID();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ArrayAdapter aa = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, regisCourse);
        aa.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        alumni_dept.setAdapter(aa);
        alumni_dept.post(new Runnable() {
            @Override
            public void run() {
                alumni_dept.setSelection(Arrays.asList(regisCourse).indexOf(userDepartment));
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
                                if (!Objects.equals(userBImageUrl, "https://firebasestorage.googleapis.com/v0/b/cte306miniproject.appspot.com/o/default-alumni.jpg?alt=media&token=ff9d273e-8b77-451f-b3ca-1ede46811c3f")){
                                    StorageReference photoRef = storage.getReferenceFromUrl(userBImageUrl);
                                    photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // File deleted successfully
                                            // Image uploaded successfully
                                            userBImageUrl = uri.toString();
                                            if (selectedImageUri1 == null){
                                                uploadtofirebase3();
                                            }else{
                                                uploadtofirebase2();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Uh-oh, an error occurred!
                                            Log.d("TAG", "onFailure: did not delete file");
                                        }
                                    });
                                }else {
                                    userBImageUrl = uri.toString();
                                    if (selectedImageUri1 == null){
                                        uploadtofirebase3();
                                    }else{
                                        uploadtofirebase2();
                                    }
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
        progressDialog.show();
        storage = FirebaseStorage.getInstance();
        uploader = storage.getReference("UserImage1"+new Random().nextInt(50));
        uploader.putFile(selectedImageUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        StorageReference photoRef = storage.getReferenceFromUrl(userImageUrl);
                        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // File deleted successfully
                                userImageUrl = uri.toString();
                                uploadtofirebase3();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Uh-oh, an error occurred!
                                Log.d("TAG", "onFailure: did not delete file");
                            }
                        });
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
                progressDialog.setMessage("Updating ");
            }
        });
    }
    public void uploadtofirebase3(){
        progressDialog.show();
        FirebaseDatabase  database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = database.getReference("User");
        mDatabaseRef.child(userID).child("userBImage").setValue(userBImageUrl);
        mDatabaseRef.child(userID).child("userImage").setValue(userImageUrl);
        mDatabaseRef.child(userID).child("userName").setValue(alumni_name.getText().toString());
        mDatabaseRef.child(userID).child("description").setValue(editText2.getText().toString());
        mDatabaseRef.child(userID).child("userDepartment").setValue(alumni_dept.getSelectedItem().toString());
        mDatabaseRef.child(userID).child("userJob").setValue(user_job.getText().toString());
        mDatabaseRef.child(userID).child("userYear").setValue(alumni_graduate_year.getText().toString());

        DatabaseReference mDatabaseRef1 = database.getReference("Events");
        Query query = mDatabaseRef1.orderByChild("org_email").equalTo(eMail);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mDatabaseRef1.child(snapshot.getKey()).child("org_Image").setValue(userImageUrl);
                mDatabaseRef1.child(snapshot.getKey()).child("org_name").setValue(alumni_name.getText().toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        progressDialog.dismiss();
        Toast.makeText(EditProfile.this, "Successful", Toast.LENGTH_SHORT).show();
        finish();
    }
}
