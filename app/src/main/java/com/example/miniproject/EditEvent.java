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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class EditEvent extends AppCompatActivity {

    private EditText ed_name, ed_description, ed_venue;
    private CalendarView calendar_view;
    private TimePicker timePicker;
    private ImageView IVPreviewImage;
    private AppCompatImageButton appCompatImageButton;
    private String org_name, org_image, org_usertype, org_dept, event_month, event_day, event_year, event_time, event_add;
    private Button submit_btn;
    private Uri selectedImageUri;
    private FirebaseStorage storage;
    private StorageReference uploader;
    private String key, email;
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_add);

        ed_name = findViewById(R.id.ed_Name);
        ed_description = findViewById(R.id.ed_description);
        ed_venue = findViewById(R.id.ed_Venue);
        IVPreviewImage = findViewById(R.id.add_image);
        calendar_view = findViewById(R.id.calendarView);
        timePicker = findViewById(R.id.timePicker3);
        submit_btn = findViewById(R.id.ed_submit);
        appCompatImageButton = findViewById(R.id.appCompatImageButton);

        appCompatImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Getting day month and year from user profile
        calendar_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                event_year = String.valueOf(i);
                event_month = String.valueOf(i1);
                event_day = String.valueOf(i2);
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                int hour = i;
                int minutes = i1;
                String timeSet = "";
                if (hour > 12) {
                    hour -= 12;
                    timeSet = "PM";
                } else if (hour == 0) {
                    hour += 12;
                    timeSet = "AM";
                } else if (hour == 12){
                    timeSet = "PM";
                }else{
                    timeSet = "AM";
                }
                String min = "";
                if (minutes < 10)
                    min = "0" + minutes ;
                else
                    min = String.valueOf(minutes);

                // Append in a StringBuilder
                event_time = new StringBuilder().append(hour).append(':').append(min ).append("-").append(timeSet).toString();
            }
        });

        //getting organizer profile

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        org_image = sharedPreferences.getString("Image", "");
        org_name = sharedPreferences.getString("Name", "");
        org_dept = sharedPreferences.getString("Department", "");
        org_usertype = sharedPreferences.getString("Type", "");
        email = sharedPreferences.getString("email", "");


        IVPreviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        //Submit the files
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(ed_name.getText().toString())){
                    Toast.makeText(EditEvent.this, "Insert Title!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(event_day)){
                    Toast.makeText(EditEvent.this, "Select a Date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(event_time)){
                    Toast.makeText(EditEvent.this, "Select a Time", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ed_description.getText().toString())){
                    Toast.makeText(EditEvent.this, "Description is Empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ed_venue.getText().toString())){
                    Toast.makeText(EditEvent.this, "Venue is Empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                uploadtofirebase();
            }
        });

        //Cancel upload
        appCompatImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        IVPreviewImage.setImageBitmap(selectedImageBitmap);
                    }
                }
            });

    private void uploadtofirebase() {
        if (TextUtils.isEmpty(selectedImageUri.toString())){
            Toast.makeText(EditEvent.this, "Please Select and Image!", Toast.LENGTH_SHORT).show();
            return;
        }
        storage = FirebaseStorage.getInstance();
        uploader = storage.getReference("Image1"+new Random().nextInt(50));

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();

            event_add = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

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
                                            LoginPage loginPage = new LoginPage();
                                            EventWithData eventWithData  = new EventWithData(
                                                    ed_name.getText().toString(),
                                                    ed_description.getText().toString(),
                                                    ed_venue.getText().toString(),
                                                    uri.toString(),
                                                    event_day,
                                                    event_month,
                                                    event_year,
                                                    event_add,
                                                    org_image,
                                                    org_name,
                                                    org_dept,
                                                    org_usertype,
                                                    email,
                                                    event_time,
                                                    key
                                            );
                                            root.child(key).setValue(eventWithData);

                                            Toast.makeText(EditEvent.this, "Successful", Toast.LENGTH_SHORT).show();
                                            finish();
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
                                    progressDialog.setMessage("Creating " + (int)progress + "%");
                                }
                            });
        }
}
