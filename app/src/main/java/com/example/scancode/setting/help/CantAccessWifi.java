package com.example.scancode.setting.help;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.scancode.R;

public class CantAccessWifi extends AppCompatActivity {
    TextView txtCantAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.cant_connect_to_wifi));
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_icon)));
        getSupportActionBar().setElevation(0);
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cant_access_wifi);

        txtCantAccess = findViewById(R.id.txt_Cant_Wifi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtCantAccess.setText(Html.fromHtml("<p>" + getString(R.string.cant_access_wifi_1) +
                    "<strong>" + getString(R.string.cant_access_wifi_2) + "</strong></p>" +
                    "<p><br>" + getString(R.string.cant_access_wifi_3) +
                    "<strong>" + getString(R.string.cant_access_wifi_4) + "</strong></p>" +
                    "<p><br>" + getString(R.string.cant_access_wifi_5) +
                    "<strong>" + getString(R.string.cant_access_wifi_6) + "</strong>" +
                    getString(R.string.cant_access_wifi_7) +
                    "<strong>" + getString(R.string.cant_access_wifi_8) + "</strong></p>", Html.FROM_HTML_MODE_COMPACT));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}