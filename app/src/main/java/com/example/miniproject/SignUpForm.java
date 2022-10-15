package com.example.miniproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
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
    private EditText regisName, regisEmail, regisPassword, regisYear, regisDepartment, regisJob;
    private String[] regisType = {"Alumni", "Student", "Tutor"};
    private Spinner spinner;
    private Button regisButton;
    private Uri selectedImageUri;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        textView = findViewById(R.id.textView_sign_in);
        regisImage = findViewById(R.id.regisImage);
        regisName = findViewById(R.id.regisName);
        regisEmail = findViewById(R.id.regisEmail);
        regisPassword = findViewById(R.id.regisPassword);
        regisYear = findViewById(R.id.regisYear);
        regisDepartment = findViewById(R.id.regisCourse);
        regisJob = findViewById(R.id.regisJob);
        spinner = findViewById(R.id.spinner);
        regisButton = findViewById(R.id.regisButton);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("UserImage1"+new Random().nextInt(50));

        regisImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        ArrayAdapter aa = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, regisType);
        aa.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        regisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validations for input email and password
                if (TextUtils.isEmpty(regisEmail.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please enter email!!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(regisPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please enter password!!", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(SignUpForm.this, "Processing...", Toast.LENGTH_SHORT).show();
                registerNewUser();
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
                        Toast.makeText(getApplicationContext(), e.getMessage() + "", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void uploadtofirebase() {

        if (selectedImageUri != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();

            // adding listeners on upload
            // or failure of image
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
                            UserModel userModel  = new UserModel(
                                    uri.toString(),
                                    regisName.getText().toString(),
                                    regisEmail.getText().toString(),
                                    regisYear.getText().toString(),
                                    regisDepartment.getText().toString(),
                                    regisJob.getText().toString(),
                                    spinner.getSelectedItem().toString(),
                                    "");
                            root.child(key).setValue(userModel);
                            Toast.makeText(SignUpForm.this, "Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginPage.class));
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
}
