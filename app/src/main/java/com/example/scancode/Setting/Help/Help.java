package com.example.scancode.Setting.Help;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.scancode.Adapter.HelpAdapter;
import com.example.scancode.R;
import com.example.scancode.Setting.Feebback;

import java.util.ArrayList;

public class Help extends AppCompatActivity {
    ArrayList<HelpItem> helpItems;
    HelpAdapter helpAdapter;
    ListView listView;
    Button btn_problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Help");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_icon)));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_help);

        AnhXa();
        otherProblem();

        helpItems.add(new HelpItem("Mẹo quét", R.drawable.ic_outline_emoji_objects_24));
        helpItems.add(new HelpItem("Không thể đọc được mã", R.drawable.ic_baseline_qr_code_24));
        helpItems.add(new HelpItem("Không thể kết nối wifi", R.drawable.ic_baseline_wifi_off_24));
        helpItems.add(new HelpItem("Liên kết bị lỗi", R.drawable.ic_baseline_link_off_24));
        listView.setAdapter(helpAdapter);

        ClickItem();
    }

    private void ClickItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i) {
                    case 0:
                        intent = new Intent(Help.this, Tips.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(Help.this, CantReadQR.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(Help.this, CantAccessWifi.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(Help.this, ErrorLink.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void otherProblem() {
        btn_problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Help.this, Feebback.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        listView = findViewById(R.id.list_item_help);
        helpItems = new ArrayList<HelpItem>();
        helpAdapter = new HelpAdapter(Help.this, R.layout.list_item_help, helpItems);
        btn_problem = findViewById(R.id.btn_problem);
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