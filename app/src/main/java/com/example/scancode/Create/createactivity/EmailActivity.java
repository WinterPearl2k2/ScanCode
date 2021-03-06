package com.example.scancode.Create.createactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scancode.R;
import com.example.scancode.Scan.ResultScan;
import com.google.android.material.textfield.TextInputEditText;

public class EmailActivity extends AppCompatActivity {
    TextInputEditText edEmail, edTitle, edMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        InitActionBar();
        AnhXa();
    }

    private void InitActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.create_email));
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
                    Intent intent = new Intent(EmailActivity.this, QRImageActivity.class);
                    Bundle mBundle = new Bundle();
                    String Qrtxt = "MATMSG:TO:" + edEmail.getText()
                            + ((edTitle.getText().toString().length() == 0 )?"":";SUB:") + edTitle.getText()
                            + ((edMess.getText().toString().length() == 0)?"":";BODY:") + edMess.getText() + ";";
                    mBundle.putString("QRformat", "QR_CODE");
                    mBundle.putString("QRtitle", "Email");
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
        edEmail = findViewById(R.id.inputContactEmail);
        edTitle = findViewById(R.id.inputEmailTitle);
        edMess = findViewById(R.id.inputEmailMessage);
    }
    private boolean CheckNull(){
        boolean check = true;
        if( edEmail.getText().toString().length() == 0 ) {
            edEmail.setError(getString(R.string.required_field));
            check = false;
        }
        if (!edEmail.getText().toString().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(edEmail.getText().toString()).matches()){
            edEmail.setError(getString(R.string.email_validation));
            check = false;
        }
        if(check)
            return true;
        return false;
    }
}