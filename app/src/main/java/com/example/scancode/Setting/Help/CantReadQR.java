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

public class CantReadQR extends AppCompatActivity {
    TextView txtCantRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Can't read QR code");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_icon)));
        actionBar.setElevation(0);
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cant_read_qr);
        txtCantRead = findViewById(R.id.txt_Cant_Read);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtCantRead.setText(Html.fromHtml("<b>1. Để có được kết quả quét chính xác bạn cần làm theo các bước sau:</b>" +
                    "<p>\ta. Đảm bảo độ tương phản và không bị mờ.</p>" +
                    "<p>\tb. Hình ảnh không bị hắc sáng hoặc bị che bóng</p>" +
                    "<p>\tc. Hình ảnh không bị che khuyết.</p>" +
                    "<p>\td. Khi quét không được zoom quá gần.</p>" +
                    "<b><br>2. Đối với các điện thoại đời cũ," +
                    "độ phân giải máy ảnh sẽ ảnh hưởng đến quá trình quét mã.</b>" +
                    "<b><br><br>Để giải quyết chúng tôi khuyên bạn nên sử dụng hình ảnh từ thư viện " +
                    "để có thể tối ưu hơn trong quá trình quét mã.</b>" +
                    "<b><br><br>3. Nếu vẫn không thể quét được vui lòng</b> <u>phản hồi</u> <b> chúng tôi qua email, " +
                    "chúng tôi sẽ giải quyết vấn đề của bạn trong thời gian sớm nhất.</b>", Html.FROM_HTML_MODE_COMPACT));
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