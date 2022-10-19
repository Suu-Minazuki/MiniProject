package com.example.miniproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.Model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Random;

public class SignUpForm extends AppCompatActivity {

    private TextView textView;
    private ImageView regisImage;
    private EditText regisName, regisEmail, regisPassword, regisCPass, regisYear, regisJob;
    private String[] regisType = {"User Type", "Alumni", "Student", "Tutor"};
    private String[] regisCourse = {"Programme" ,"BE in IT", "BE in Civil Engineering", "BE in ECE", "BE in ICE", "BE in ECE","BE in Engineering Geology", "B Architecture"};
    private static String[] colors = {"#ff00ff", "#ff0000", "#0000ff", "#003366", "#800080", "#ffff00", "#00ff00", "#008000", "#990000", "#420420"};
    private int index;
    private Spinner spinner, spinner1;
    private Button regisButton;
    private static final String userBImage = "https://firebasestorage.googleapis.com/v0/b/cte306miniproject.appspot.com/o/default-alumni.jpg?alt=media&token=ff9d273e-8b77-451f-b3ca-1ede46811c3f";
    private Uri selectedImageUri;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        textView = findViewById(R.id.textView_sign_in);
        regisImage = findViewById(R.id.regisImage);
        regisName = findViewById(R.id.regisName);
        regisEmail = findViewById(R.id.regisEmail);
        regisPassword = findViewById(R.id.regisPassword);
        regisCPass = findViewById(R.id.regisConfirmPassword);
        regisYear = findViewById(R.id.regisYear);
        regisJob = findViewById(R.id.regisJob);
        spinner = findViewById(R.id.spinner);
        spinner1 = findViewById(R.id.spinner1);
        regisButton = findViewById(R.id.regisButton);

        Random random = new Random();
        index = random.nextInt(colors.length);

        // Code for showing progressDialog while uploading
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("UserImage1"+new Random().nextInt(50));

        regisImage.setOnClickListener(view -> imageChooser());

        ArrayAdapter aa = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, regisType);
        aa.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        ArrayAdapter aa1 = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, regisCourse);
        aa1.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner1.setAdapter(aa1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        break;
                    case 1:
                        regisJob.setVisibility(View.VISIBLE);
                        spinner1.setVisibility(View.VISIBLE);
                        regisYear.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        spinner1.setVisibility(View.VISIBLE);
                        regisYear.setVisibility(View.VISIBLE);
                        regisJob.setVisibility(View.GONE);
                        break;
                    case 3:
                        regisJob.setVisibility(View.GONE);
                        spinner1.setVisibility(View.GONE);
                        regisYear.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //For Spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, regisType){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, regisCourse){
            @Override
            public boolean isEnabled(int position) {

                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter2);

        //register button
        regisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validations for input email and password
                if (TextUtils.isEmpty(regisEmail.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please enter email!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(regisPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(regisCPass.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please Confirm Password!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!regisCPass.getText().toString().equals(regisPassword.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Password not similar!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (spinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), "Please Select User Type", Toast.LENGTH_LONG).show();
                    return;
                }
                switch (spinner.getSelectedItem().toString()){
                    case "Alumni":
                        if (TextUtils.isEmpty(regisYear.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Please enter Batch!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (TextUtils.isEmpty(spinner1.getSelectedItem().toString())) {
                            Toast.makeText(getApplicationContext(), "Please Choose a Course", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (TextUtils.isEmpty(regisJob.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Please enter your Job Title", Toast.LENGTH_LONG).show();
                            return;
                        }
                        break;
                    case "Student":
                        if (TextUtils.isEmpty(regisYear.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Please enter Batch!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (spinner1.getSelectedItemPosition() == 0) {
                            Toast.makeText(getApplicationContext(), "Please Choose a Course", Toast.LENGTH_LONG).show();
                            return;
                        }
                        break;
                }
                if (selectedImageUri != null){
                    registerNewUser();
                }else{
                    Toast.makeText(SignUpForm.this, "Select an Image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
            }
        });

    }



    private void imageChooser() {
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
                regisImage.setImageBitmap(selectedImageBitmap);
            }
        }
    });

    private void registerNewUser() {
        progressDialog.show();
        progressDialog.setMessage("Registering 0%");
        // create new user or register new user
        firebaseAuth.createUserWithEmailAndPassword(regisEmail.getText().toString(), regisPassword.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        uploadtofirebase();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), e.getMessage() + "", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void uploadtofirebase() {
        storageReference.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Image uploaded successfully
                        // Dismiss dialog
                        progressDialog.dismiss();
                        FirebaseDatabase db=FirebaseDatabase.getInstance();
                        DatabaseReference root=db.getReference("User");
                        String key = root.push().getKey();
                        switch (spinner.getSelectedItem().toString()){
                            case "Alumni":
                                UserModel userModel  = new UserModel(
                                        uri.toString(),
                                        regisName.getText().toString(),
                                        regisEmail.getText().toString(),
                                        regisYear.getText().toString(),
                                        spinner1.getSelectedItem().toString(),
                                        regisJob.getText().toString(),
                                        spinner.getSelectedItem().toString(),
                                        "",
                                        userBImage,
                                        colors[index],
                                        key);
                                root.child(key).setValue(userModel);
                                break;
                            case "Student":
                                UserModel userModel1  = new UserModel(
                                        uri.toString(),
                                        regisName.getText().toString(),
                                        regisEmail.getText().toString(),
                                        regisYear.getText().toString(),
                                        spinner1.getSelectedItem().toString(),
                                        "",
                                        spinner.getSelectedItem().toString(),
                                        "",
                                        userBImage,
                                        colors[index],
                                        key);
                                root.child(key).setValue(userModel1);
                                break;
                            case "Tutor":
                                    UserModel userModel2  = new UserModel(
                                            uri.toString(),
                                            regisName.getText().toString(),
                                            regisEmail.getText().toString(),
                                            "",
                                            "",
                                            "Lecturer",
                                            spinner.getSelectedItem().toString(),
                                            "",
                                            userBImage,
                                            colors[index],
                                            key);
                                    root.child(key).setValue(userModel2);
                                    break;
                        }
                        Toast.makeText(SignUpForm.this, "Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginPage.class));
                        finish();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Error, Image not uploaded
                progressDialog.dismiss();
                Toast.makeText(SignUpForm.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            // Progress Listener for loading
            // percentage on the dialog box
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Registering " + (int)progress + "%");
            }
        });

    }
}
