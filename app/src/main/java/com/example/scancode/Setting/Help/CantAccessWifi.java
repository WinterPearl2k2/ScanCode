package com.example.scancode.Setting.Help;

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

public class CantAccessWifi extends AppCompatActivity {
    TextView txtCantAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Can't connect to Wi-Fi");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_icon)));
        getSupportActionBar().setElevation(0);
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cant_access_wifi);

        txtCantAccess = findViewById(R.id.txt_Cant_Wifi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtCantAccess.setText(Html.fromHtml("<p>1. Hãy đảm bảo rằng bạn đang<strong> nằm trong vùng phủ sóng Wifi," +
                    " tên người dùng và mật khẩu chính xác. </strong></p>" +
                    "<p><br>Nếu vẫn không thể kết nối bạn vui lòng <strong>hỏi nhà cung " +
                    "cấp dịch vụ của bạn và hỏi xem mã đã được thay đổi chưa.</strong></p>" +
                    "<p><br>2. Nếu kết quả hiển thị dưới dạng văn bản, bạn vui lòng <strong>kết nối vào Wifi </strong>" +
                    "bằng cách thủ công với các thiết bị <strong>android 10 trở lên, do giới hạn hệ thống.</strong></p>", Html.FROM_HTML_MODE_COMPACT));
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