package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIMER = 1700;

    //Variables
    ImageView splash_screen_logo;
    TextView splash_screen_title;
    TextView splash_screen_desc;

    //Animations
    Animation fadeAnim;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hooks
        splash_screen_logo = findViewById(R.id.splash_screen_logo);
        splash_screen_title = findViewById(R.id.splash_screen_title);
        splash_screen_desc = findViewById(R.id.splash_screen_desc);

        //Animations
        fadeAnim = AnimationUtils.loadAnimation(this, R.anim.fade);

        //set Animations on elements
        splash_screen_logo.setAnimation(fadeAnim);
        splash_screen_title.setAnimation(fadeAnim);
        splash_screen_desc.setAnimation(fadeAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
                boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);

                if (isFirstTime){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), OnBoarding.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                    startActivity(intent);
                }
                finish();

            }
        }, SPLASH_TIMER);
    }
}