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

public class ErrorLink extends AppCompatActivity {
    TextView txtErrorLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.link_is_broken));
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_icon)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_link);

        txtErrorLink = findViewById(R.id.txt_Error_Link);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtErrorLink.setText(Html.fromHtml("<p>" + getString(R.string.erro_link_1) +
                    "<strong>" + getString(R.string.erro_link_2) +
                    "</strong> " + getString(R.string.erro_link_3) + "</p>" +
                    "<p><br>" + getString(R.string.erro_link_4) +
                    " <strong>" + getString(R.string.erro_link_5) + "</strong> " +
                    getString(R.string.erro_link_6) +
                    " <strong>" + getString(R.string.erro_link_7) + "</strong>" +
                    getString(R.string.erro_link_8) +
                    " <strong> " + getString(R.string.erro_link_9) + "</strong>" +
                    getString(R.string.erro_link_10) + " </p>" +
                    "<p><br>" +getString(R.string.erro_link_11) +
                    "<strong>" + getString(R.string.erro_link_12) + "</strong> " +
                    getString(R.string.erro_link_13) +
                    "<strong>" + getString(R.string.erro_link_14) + "</strong>" +
                    getString(R.string.erro_link_15) +
                    "<strong>" + getString(R.string.erro_link_16) + "</strong></p>", Html.FROM_HTML_MODE_COMPACT));
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