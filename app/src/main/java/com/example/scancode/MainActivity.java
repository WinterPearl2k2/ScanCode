package com.example.scancode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.example.scancode.Adapter.MenuAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
}