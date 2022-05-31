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

public class CantReadQR extends AppCompatActivity {
    TextView txtCantRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.code_cant_be_read));
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_icon)));
        actionBar.setElevation(0);
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cant_read_qr);
        txtCantRead = findViewById(R.id.txt_Cant_Read);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtCantRead.setText(Html.fromHtml("<b>" + getString(R.string.help_can_read_qr_1) + "</b>" +
                    "<p>\t" + getString(R.string.help_can_read_qr_2) + "</p>" +
                    "<p>\t" + getString(R.string.help_can_read_qr_3) + "</p>" +
                    "<p>\t" + getString(R.string.help_can_read_qr_4) + "</p>" +
                    "<p>\t" + getString(R.string.help_can_read_qr_5) + "</p>" +
                    "<b><br>" + getString(R.string.help_can_read_qr_6) +
                    getString(R.string.help_can_read_qr_7) + "</b>" +
                    "<b><br><br>" + getString(R.string.help_can_read_qr_8) +
                    getString(R.string.help_can_read_qr_9) + "</b>" +
                    "<b><br><br>" + getString(R.string.help_can_read_qr_10) +
                    "</b> <u>" + getString(R.string.help_can_read_qr_11) +
                    "</u> <b> " + getString(R.string.help_can_read_qr_12) +
                    getString(R.string.help_can_read_qr_13) + "</b>", Html.FROM_HTML_MODE_COMPACT));
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