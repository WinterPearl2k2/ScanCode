package com.example.scancode.Create.createactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scancode.R;
import com.example.scancode.Scan.ResultScan;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class WifiActivity extends AppCompatActivity {
    ArrayAdapter<CharSequence> adapter;
    AutoCompleteTextView typeWifi;
    TextInputEditText edSSID, edPass;
    TextInputLayout txtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        getSupportActionBar().setTitle("Wifi");
        AnhXa();
        init();



    }

    public void init() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        adapter = new ArrayAdapter<CharSequence>(
                WifiActivity.this,
                getAdapterItemLayout(),
                getResources().getStringArray(R.array.strings_type_internet_dropdown));
        typeWifi.setAdapter(adapter);
        typeWifi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if (item.equals(adapterView.getItemAtPosition(2).toString()))
                    txtPass.setVisibility(View.INVISIBLE);
                else
                    txtPass.setVisibility(View.VISIBLE);

            }
        });
    }
    public int getAdapterItemLayout() {
        return R.layout.list_item_dropdown;
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
                if(CheckNull())  {
                    Intent intent = new Intent(WifiActivity.this, QRImageActivity.class);
                    Bundle mBundle = new Bundle();
                    String Qrtxt = "WIFI:T:" + TypeWifi() + ";S:" + edSSID.getText() + ";P:" + edPass.getText() + ";H:;";
                    mBundle.putString("QRformat", "QR_CODE");
                    mBundle.putString("QRtitle", "Wifi");
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
        typeWifi = findViewById(R.id.dropdownTypeWifi);
        edSSID = findViewById(R.id.inputSSID);
        edPass = findViewById(R.id.inputPassword);
        txtPass = findViewById(R.id.WifiPass);
    }
    private boolean CheckNull(){
        boolean check = true;
        if( edSSID.getText().toString().length() == 0 ) {
            edSSID.setError(getString(R.string.required_field));
            check = false;
        }
        if( edPass.getText().toString().length() == 0 && txtPass.getVisibility() == View.VISIBLE) {
            edPass.setError(getString(R.string.required_field));
            check = false;
        }
        if( typeWifi.getText().toString().equals(adapter.getItem(0).toString()) && edPass.getText().toString().length() < 8){
            edPass.setError(getString(R.string.required_quantity));
            check = false;
        }
        if(check)
            return true;
        return false;
    }
    private String TypeWifi(){
        if(typeWifi.getText().toString().equals(adapter.getItem(0).toString()))
            return "WPA";
        else if(typeWifi.getText().toString().equals(adapter.getItem(1).toString()))
            return "WEP";
        else
            return "nopass";
    }
}