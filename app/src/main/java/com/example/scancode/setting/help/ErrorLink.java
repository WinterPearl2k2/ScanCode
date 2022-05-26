package com.example.scancode.setting.help;

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

public class ErrorLink extends AppCompatActivity {
    TextView txtErrorLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Error link");
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_icon)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_link);

        txtErrorLink = findViewById(R.id.txt_Error_Link);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtErrorLink.setText(Html.fromHtml("<p>1. Ứng dụng không thể thay đổi nội dung của mã, " +
                    "nếu trang web không hoạt động khả năng cao là <strong>do bên sở hữu trang web.</strong> " +
                    "Rất tiếc chúng tôi không thể hỗ trợ cho bạn vấn đề này.</p>" +
                    "<p><br>2. Có thể là do <strong>người tạo mã QR này đã nhập sai liên kết</strong>" +
                    "  dẫn tới <strong>đường liên kết đến sai địa chỉ.</strong> Hoặc cũng có thể là do mã" +
                    " <strong>QR đã hết hạn</strong>, trường hợp này <strong>bạn vui lòng liên hệ với người cung cấp" +
                    " mã</strong> để của bạn để xác nhận rõ.</p>" +
                    "<p><br>3. Với một số trường hợp, một số trang web cần <strong>mở ở một điều kiện đặc biệt,</strong> " +
                    "ví dụ như mở bằng một <strong>ứng dụng nhất định.</strong> Với thường hợp này bạn vui lòng " +
                    "<strong>làm theo các bước hướng dẫn của nhà cung cấp QR cho bạn.</strong></p>", Html.FROM_HTML_MODE_COMPACT));
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