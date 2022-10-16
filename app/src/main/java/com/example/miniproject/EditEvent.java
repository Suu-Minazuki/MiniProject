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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.Model.EventWithData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

public class EditEvent extends AppCompatActivity {

    private EditText ed_name, ed_description, ed_venue;
    private ImageView IVPreviewImage;
    private String eventOccursOn, org_name, org_image, org_usertype, org_dept;
    private ImageButton cancel;
    private Uri selectedImageUri;
    private FirebaseStorage storage;
    private StorageReference uploader;
    private String key;
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event);

        ed_name = findViewById(R.id.ed_Name);
        ed_description = findViewById(R.id.ed_description);
        ed_venue = findViewById(R.id.ed_Venue);
        IVPreviewImage = findViewById(R.id.add_image);
        cancel = findViewById(R.id.cancel_btn);
        CalendarView calendar_view = findViewById(R.id.calender_view);
        Button submit_btn = findViewById(R.id.submit_btn);

        calendar_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Calendar c = Calendar.getInstance();
                c.set(i, i1, i2);
                eventOccursOn = String.valueOf(c.getTimeInMillis());
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        org_image = sharedPreferences.getString("Image", "");
        org_name = sharedPreferences.getString("Name", "");
        org_dept = sharedPreferences.getString("Department", "");
        org_usertype = sharedPreferences.getString("Type", "");

        IVPreviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadtofirebase();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                        IVPreviewImage.setImageBitmap(selectedImageBitmap);
                    }
                }
            });

    private void uploadtofirebase() {
        storage = FirebaseStorage.getInstance();
        uploader = storage.getReference("Image1"+new Random().nextInt(50));

        if (selectedImageUri != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // adding listeners on upload
            // or failure of image
            uploader.putFile(selectedImageUri)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Image uploaded successfully
                                            // Dismiss dialog
                                            progressDialog.dismiss();

                                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                                            DatabaseReference root=db.getReference("Events");
                                            key = root.push().getKey();

                                            EventWithData eventWithData  = new EventWithData(
                                                    ed_name.getText().toString(),
                                                    ed_description.getText().toString(),
                                                    ed_venue.getText().toString(),
                                                    uri.toString(),
                                                    eventOccursOn,
                                                    org_image,
                                                    org_name,
                                                    org_dept,
                                                    org_usertype
                                            );
                                            root.child(key).setValue(eventWithData);

                                            Toast.makeText(EditEvent.this, "Successful", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(EditEvent.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
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
    }

}
