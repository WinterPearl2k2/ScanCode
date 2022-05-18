package com.example.scancode.Create.createactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scancode.R;
import com.example.scancode.Scan.ResultScan;
import com.google.android.material.textfield.TextInputEditText;

public class ContactActivity extends AppCompatActivity {
    TextInputEditText edSurname, edName,
            edCompany, edJob, edPhone,
            edEmail, edAddress, edWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().setTitle("Contact");
        InitMenu();
        AnhXa();



    }

    public void InitMenu() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
//            ActionBar actionBar = getSupportActionBar();
//            actionBar.setDisplayHomeAsUpEnabled(true);
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
                    Intent intent = new Intent(ContactActivity.this, ResultScan.class);
                    Bundle mBundle = new Bundle();
//                    "BEGIN:VCARD"
//                    "\nVERSION:2.1"
//                        "\nN:" + name + ";" + Ho + ""
//                    "\nFN:" + Ho + ten
//                    "\nORG:" + Sun
//                    "\nTITLE:" + Dev
//                    "\nTEL:" + 0846608456
//                    "\nEMAIL:" + nlbien.22@gmail.com
//                    "\nADR:;;"LongBien 123 + ";"
//                    "\nURL:" + Facebook.com
//                    "\nEND:VCARD"
                    String Qrtxt =
                        "BEGIN:VCARD" +
                        "\nVERSION:2.1" +
                        "\nN:" + edName.getText() +
                        ((edSurname.getText().toString().length() == 0)?"":";" + edSurname.getText()) +
                        "\nFN:" + edSurname.getText() + " " + edName.getText() +
                        ((edCompany.getText().toString().length() == 0)?"":"\nORG:" + edCompany.getText()) +
                        ((edJob.getText().toString().length() == 0)?"":"\nTITLE:" + edJob.getText()) +
                        ((edPhone.getText().toString().length() == 0)?"":"\nTEL:" + edPhone.getText()) +
                        ((edEmail.getText().toString().length() == 0)?"":"\nEMAIL:" + edEmail.getText()) +
                        ((edAddress.getText().toString().length() == 0)?"":"\nADR:" + edAddress.getText()) +
                        ((edWeb.getText().toString().length() == 0)?"":"\nURL:" + edWeb.getText()) +
                        "\nEND:VCARD";
                    mBundle.putString("QRtitle", "QR_CODE");
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
        edSurname = findViewById(R.id.inputSurname);
        edName = findViewById(R.id.inputName);
        edCompany = findViewById(R.id.inputCompany);
        edJob = findViewById(R.id.inputJob);
        edPhone = findViewById(R.id.inputPhoneNumber);
        edEmail = findViewById(R.id.inputContactEmail);
        edAddress = findViewById(R.id.inputContactAddress);
        edWeb = findViewById(R.id.inputWeb);
    }
    private boolean CheckNull(){
        boolean check = true;
        if( edSurname.getText().toString().length() == 0) {
            edSurname.setError("Field is required!");
            check = false;
        }
        if(check)
            return true;
        return false;
    }
}