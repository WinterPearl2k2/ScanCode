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

public class SMSActivity extends AppCompatActivity {
    TextInputEditText edRecipient, edMess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        getSupportActionBar().setTitle(getString(R.string.create_sms));
        InitMenu();
        AnhXa();
    }

    private void InitMenu() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
                    Intent intent = new Intent(SMSActivity.this, QRImageActivity.class);
                    Bundle mBundle = new Bundle();
                    String Qrtxt = "SMSTO:" + edRecipient.getText() + ":" + edMess.getText();
                    mBundle.putString("QRformat", "QR_CODE");
                    mBundle.putString("QRtitle", "SMS");
                    mBundle.putString("QRinfor", Qrtxt);
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
        edRecipient = findViewById(R.id.inputRecipient);
        edMess = findViewById(R.id.inputMessage);
    }
    private boolean CheckNull(){
        boolean check = true;
        if( edRecipient.getText().toString().length() == 0 ) {
            edRecipient.setError(getString(R.string.required_field));
            check = false;
        }
        if( edMess.getText().toString().length() == 0 ) {
            edMess.setError(getString(R.string.required_field));
            check = false;
        }
        if(check)
            return true;
        return false;
    }
}