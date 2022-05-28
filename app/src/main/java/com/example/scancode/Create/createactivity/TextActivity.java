package com.example.scancode.Create.createactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scancode.R;

public class TextActivity extends AppCompatActivity {
    EditText edTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        InitActionBar();
        AnhXa();
    }

    private void InitActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.create_text));
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
                if( edTxt.getText().toString().length() == 0 )
                    edTxt.setError( getString(R.string.required_field));
                else {
                    Intent intent = new Intent(TextActivity.this, QRImageActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("QRformat", "QR_CODE");
                    mBundle.putString("QRtitle", "Text");
                    mBundle.putString("QRinfor", edTxt.getText().toString());
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
        edTxt = findViewById(R.id.inputText);
    }
}