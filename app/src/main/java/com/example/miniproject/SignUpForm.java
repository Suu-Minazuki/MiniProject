package com.example.miniproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class SignUpForm extends AppCompatActivity {

    private ViewPager2 view_pager_2;
    private PageAdapterForRegistration pageAdapterForRegistration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupform);

        view_pager_2 = findViewById(R.id.view_pager_2);
        pageAdapterForRegistration = new PageAdapterForRegistration(this);
        view_pager_2.setAdapter(pageAdapterForRegistration);

    }
}
