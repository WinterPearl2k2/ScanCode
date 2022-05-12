package com.example.scancode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.Window;

import com.example.scancode.Adapter.MenuAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    public static ViewPager2 viewPager2;
    MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ORM(); //Ánh xạ
        SetUpViewPager2(); //Cài đặt View
        EventButtonNavigation(); //Bắt sự kiện của button navigation
        DarkMode();//darkmode
        Language();//language
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
                return true;
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
    }
    public void Language(){
        Locale locale;
        SharedPreferences sharedPreferences = getSharedPreferences("language",0);
        int item = sharedPreferences.getInt("language",1);
        if(item == 1){
            locale = new Locale("vi","VN");
        }
        else {
            locale = new Locale("en","US");
        }
        ChangeLanguage(locale);
    }
    public void ChangeLanguage(Locale locale) {
        Resources resources = this.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = new Configuration(resources.getConfiguration());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            configuration.setLocale(locale);
        else
            configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);


    }
}