package com.example.scancode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.Window;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager2.widget.ViewPager2;

import com.example.scancode.Adapter.MenuAdapter;
import com.example.scancode.Setting.Main_introduction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    public static ViewPager2 viewPager2;
    MenuAdapter menuAdapter;
    Locale locale;
    SeekBar seekBar;
    boolean introduce = true;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    private static final String MY_PREF = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);

        Language();
        setContentView(R.layout.activity_main);

        ORM(); //Ánh xạ
        SetUpViewPager2(); //Cài đặt View
        EventButtonNavigation(); //Bắt sự kiện của button navigation
        //DarkMode();//darkmode

    }

    private void Introduce() {
        if(introduce) {
            Intent intent = new Intent(MainActivity.this, Main_introduction.class);
            startActivity(intent);
        }
    }

    private void EventButtonNavigation() {
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_scan:
                        viewPager2.setCurrentItem(0, false);
                        break;
                    case R.id.nav_create:
                        viewPager2.setCurrentItem(1, false);
                        break;
                    case R.id.nav_history:
                        viewPager2.setCurrentItem(2, false);
                        break;
                    case R.id.nav_setting:
                        viewPager2.setCurrentItem(3, false);
                        break;
                }
                return false;
            }
        });
    }

    private void SetUpViewPager2() {
        menuAdapter = new MenuAdapter(this);
        viewPager2.setAdapter(menuAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        navigation.getMenu().findItem(R.id.nav_scan).setChecked(true);
                        break;
                    case 1:
                        navigation.getMenu().findItem(R.id.nav_create).setChecked(true);
                        break;
                    case 2:
                        navigation.getMenu().findItem(R.id.nav_history).setChecked(true);
                        break;
                    case 3:
                        navigation.getMenu().findItem(R.id.nav_setting).setChecked(true);
                        break;
                    default:
                        navigation.getMenu().findItem(R.id.nav_scan).setChecked(true);
                        break;
                }
            }
        });
    }

    private void ORM() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        viewPager2 = (ViewPager2) findViewById(R.id.viewPager2);
    }

    public void DarkMode(){
        SharedPreferences sharedPreferences = getSharedPreferences("darkmode",0);
        boolean check = sharedPreferences.getBoolean("darkmode",false);
        if(check==false)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        //Language();
    }
    public void Language(){

        SharedPreferences sharedPreferences1 = getSharedPreferences("language",0);
        int item = sharedPreferences1.getInt("language",1);
        switch (item) {
            case 0:
                Locale localeEN = new Locale("en");
                setLocale(localeEN);
                break;
            case 1:
                Locale localeHU = new Locale("vi");
                setLocale(localeHU);
                break;

        }
    }

    public void setLocale(Locale locale) {
        Locale.setDefault(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        //recreate();
        //finish();
        //startActivity(getIntent());
        //if these are not commented, main activity wont show at start at all
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        super.onConfigurationChanged(newConfig);
    }



}