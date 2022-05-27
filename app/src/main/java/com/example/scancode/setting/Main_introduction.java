package com.example.scancode.setting;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.scancode.R;

public class Main_introduction extends AppCompatActivity {

    private ViewPager slide_Viewpaper;
    private LinearLayout mDolayout;
    private TextView[] mDots;
    private Slide_layout slide_layout;
    private ImageView next;
    private  int mcurrent;
    private  SharedPreferences.Editor editor;
    private static final String MY_PREF = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_introduction);
        //initUI();
        slide_Viewpaper = findViewById(R.id.view_paper);
        mDolayout = findViewById(R.id.dotshot);
        next=findViewById(R.id.button3);
        slide_layout = new Slide_layout(this);
        slide_Viewpaper.setAdapter(slide_layout);
        addDot(0);
        slide_Viewpaper.addOnPageChangeListener(viewListner);
        introduce();

    }

    private void introduce() {
        editor = getSharedPreferences(MY_PREF, MODE_PRIVATE).edit();
        editor.putBoolean("noIntroduce", false);
        editor.apply();
    }

    @Override
    public void recreate() {
        super.recreate();
    }

    private void initUI() {

    }

    public void addDot(int position){
        mDots = new TextView[3];
        mDolayout.removeAllViews();

        for (int i=0;i<3;i++)
        {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.pink_light));
            mDolayout.addView(mDots[i]);
        }
        if(mDots.length>0)
                mDots[position].setTextColor(getResources().getColor(R.color.white));
    }
    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDot(position);
            mcurrent = position;

            if (mcurrent == 2) {
                next.setEnabled(true);
                next.setVisibility(View.VISIBLE);
                next.setImageResource(R.drawable.check3);

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            } else {
                next.setEnabled(false);
                next.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}