package com.example.scancode.Create.createactivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scancode.History.database.DataBase;
import com.example.scancode.History.listviewhistory.CreateHistoryDatabase;
import com.example.scancode.History.listviewhistory.CreateHistoryRecycleViewAdapter;
import com.example.scancode.History.listviewhistory.History;
import com.example.scancode.R;
import com.google.zxing.WriterException;

import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRImageActivity extends AppCompatActivity {
    private ImageView qrCodeIV;
    private TextView txtQrInfor, txtQrTitle;
    private Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    private CreateHistoryRecycleViewAdapter adapter;
    private List<History> historyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrimage);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("QR Code");
        actionBar.setDisplayHomeAsUpEnabled(true);

        qrCodeIV = findViewById(R.id.imageQR);
        txtQrTitle = findViewById(R.id.QRTitle);
        txtQrInfor = findViewById(R.id.QRInf);

        QRGenerator();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.to_right_1, R.anim.to_right_2);
    }
    private void QRGenerator(){


        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        //initializing a variable for default display.
        Display display = manager.getDefaultDisplay();
        //creating a variable for point which is to be displayed in QR Code.
        Point point = new Point();
        display.getSize(point);
        //getting width and height of a point
        int width = point.x;
        int height = point.y;
        //generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;
        Bundle extras = this.getIntent().getExtras();
        Log.e("TAG", "8");
        txtQrTitle.setText(extras.get("QRtitle").toString());
        Log.e("TAG", "9");
        txtQrInfor.setText(extras.get("QRinfor").toString());
        DataBase dataBase = CreateHistoryActivity.dataBase;
        String qrname = extras.get("QRtitle").toString();
        String qrinfor = extras.get("QRinfor").toString();
        String qrtime = "10/11/2002";
        adapter = new CreateHistoryRecycleViewAdapter();
        historyList = new ArrayList<>();
        adapter.setData(historyList);
        History history = new History(qrname, qrinfor, qrtime, getIcon(qrname));
        CreateHistoryDatabase.getInstance(this).historyDAO().insertUser(history);
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        //setting this dimensions inside our qr code encoder to generate our qr code.
        qrgEncoder = new QRGEncoder(txtQrInfor.getText().toString(), null, QRGContents.Type.TEXT, dimen);
        try {
            //getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set inside our image view using .setimagebitmap method.
            qrCodeIV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            //this method is called for exception handling.
            Log.e("Tag", e.toString());
        }
    }

    private int getIcon(String name) {
        switch (name){
            case "Text":
                return R.drawable.ic_document_48;
            case "Wifi":
                return R.drawable.ic_wifi_48;
            case "Contact":
                return R.drawable.ic_contact_24;
            case "SMS":
                return R.drawable.ic_sms_24;
            case "Email":
                return R.drawable.ic_mail_24;
            case "URL":
                return R.drawable.ic_global_24;
        }
        return 0;
    }
}