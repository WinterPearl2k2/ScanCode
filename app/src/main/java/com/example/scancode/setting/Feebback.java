package com.example.scancode.setting;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scancode.R;

public class Feebback extends AppCompatActivity {
    CheckBox check1,check2,check3;
    EditText edit;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Feedback");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_icon)));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feebback);
        initUI();
        submitFeedback();

    }
    public String Check(){
        String t = "\n";
        
        if(check1.isChecked())
        {
            t=t+check1.getText().toString()+"\n";
        }

        if(check2.isChecked())
            t=t+check2.getText().toString()+"\n";
        if(check3.isChecked())
            t=t+check3.getText().toString()+"\n";
        return t;

    }
    public void submitFeedback(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  check;
                check = Check();
                String finalFeedback = edit.getText().toString();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setType("text/html");
                String aEmailList[] = {"hothianhduong16112002@gmail.com"};
                emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
                String feedback_msg = finalFeedback + check;
                emailIntent.putExtra(Intent.EXTRA_TEXT,   finalFeedback + "\n"+Check());
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback's customer about Scancode");

                PackageManager packageManager = Feebback.this.getPackageManager();
                boolean isIntentSafe = emailIntent.resolveActivity(packageManager) != null;
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send Email"));
                } catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(Feebback.this, "Email not installed", Toast.LENGTH_SHORT).show();
                }
               // startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
            });
    }
    public void initUI(){
        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        check3 = findViewById(R.id.check3);
        edit   = findViewById(R.id.feebackedit);
        btn    = findViewById(R.id.feebackbtn);
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