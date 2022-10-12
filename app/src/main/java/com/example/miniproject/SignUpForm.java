package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.miniproject.adapter.PageAdapterForRegistration;

public class SignUpForm extends AppCompatActivity {

    private ViewPager2 view_pager_2;
    private PageAdapterForRegistration pageAdapterForRegistration;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupform);

        view_pager_2 = findViewById(R.id.view_pager_2);
        pageAdapterForRegistration = new PageAdapterForRegistration(this);
        view_pager_2.setAdapter(pageAdapterForRegistration);

        textView = findViewById(R.id.textView_sign_in);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
            }
        });
    }
}
