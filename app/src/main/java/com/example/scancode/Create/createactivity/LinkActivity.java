package com.example.scancode.Create.createactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.scancode.R;
import com.example.scancode.Scan.ResultScan;
import com.google.android.material.textfield.TextInputEditText;

public class LinkActivity extends AppCompatActivity {
    TextInputEditText edURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        InitActionBar();
        AnhXa();

    }

    private void InitActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.create_url));
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.accept_nav, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.icon_accept:
                if(CheckNull()) {
                    Intent intent = new Intent(LinkActivity.this, QRImageActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("QRformat", "QR_CODE");
                    mBundle.putString("QRtitle", "URL");
                    mBundle.putString("QRinfor", edURL.getText().toString());
                    intent.putExtras(mBundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.to_left_1, R.anim.to_left_2);
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.to_right_1, R.anim.to_right_2);
    }
    private void AnhXa(){
        edURL = findViewById(R.id.inputURl);
    }
    private boolean CheckNull(){
        boolean check = true;
        if( edURL.getText().toString().length() == 0 ) {
            edURL.setError(getString(R.string.required_field));
            check = false;
        }
        if(check)
            return true;
        return false;
    }
}