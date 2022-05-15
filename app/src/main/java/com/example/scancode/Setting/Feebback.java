package com.example.scancode.Setting;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.scancode.R;

public class Feebback extends AppCompatActivity {
    CheckBox check1,check2,check3;
    EditText edit;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
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
                if (isIntentSafe) {
                    startActivity(emailIntent);
                } else {
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
}