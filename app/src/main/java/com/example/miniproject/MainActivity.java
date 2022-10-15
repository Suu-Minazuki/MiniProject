package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;

    private SliderAdapter sliderAdapter;
    TextView[] dots;

    AppCompatButton getStartedBtn;
    Animation animation;

    int currentPos;

    SaveState saveState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hooks
        viewPager = (ViewPager) findViewById(R.id.slider);
        dotsLayout = (LinearLayout) findViewById(R.id.dots);
        getStartedBtn = (AppCompatButton)  findViewById(R.id.getStartedBtn);

        //call Adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDots(0);

        saveState = new SaveState(MainActivity.this, "0B");
        if(saveState.getState() == 1){
            startActivity(new Intent(this,LoginPage.class));
            finish();
        }

        viewPager.addOnPageChangeListener(changeListener);

    }

    public void skip(View view){
        startActivity(new Intent(this, LoginPage.class));
        finish();
    }

    public void next(View view){
        viewPager.setCurrentItem(currentPos + 1);
    }

    private void addDots(int position){

        dots = new TextView[4];
        dotsLayout.removeAllViews();

        for(int i=0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dots_color));

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.teal_200));
        }

    }
    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);

            currentPos = position;

            if(position == 0){
                getStartedBtn.setVisibility(View.INVISIBLE);
            }
            else if(position == 1){
                getStartedBtn.setVisibility(View.INVISIBLE);
            }
            else if(position == 2){
                getStartedBtn.setVisibility(View.INVISIBLE);
            }
            else{
                animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_anim);
                getStartedBtn.setAnimation(animation);
                getStartedBtn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void getStarted(View view) {

        saveState.setState(1);
        startActivity(new Intent(this,LoginPage.class));
        finish();
    }

}