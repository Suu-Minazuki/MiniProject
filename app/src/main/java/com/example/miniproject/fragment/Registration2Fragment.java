package com.example.miniproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.miniproject.R;

public class Registration2Fragment extends Fragment {

    private EditText name, cid, email, password, confirmPw, admissionYear, phone, course, jobTitle;
    private Button registerBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration2, container, false);

        name = (EditText) view.findViewById(R.id.nameEt);
        cid = (EditText) view.findViewById(R.id.cidEt);
        email = (EditText) view.findViewById(R.id.emailEt);
        password = (EditText) view.findViewById(R.id.passwordEt);
        confirmPw = (EditText) view.findViewById(R.id.confirmPasswEt);
        phone = (EditText) view.findViewById(R.id.phoneEt);
        course = (EditText) view.findViewById(R.id.courseEt);
        jobTitle = (EditText) view.findViewById(R.id.jobTitleEt);
        registerBtn = (Button) view.findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

}