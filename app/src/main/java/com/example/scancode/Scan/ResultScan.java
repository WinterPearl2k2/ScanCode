package com.example.scancode.Scan;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scancode.History.listviewhistory.History;
import com.example.scancode.History.listviewhistory.HistoryRecycleViewAdapter;
import com.example.scancode.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ResultScan extends AppCompatActivity {
    ClipboardManager clipboardManager;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private String TO = "", SUB = "", BODY = "", T = "", S = "", P = "", H = "", TEL = "", SMS = "",
            Json = "", link = "", LATITUDE = "", LONGITUDE = "",
            FN = "", ORG = "", TITLE = "", ADR = "", WORK = "", CELL = "", FAX = "", EMAIL1 = "", EMAIL2 = "", URL = "",
            SUMMARY = "", DTSTART = "", DTEND = "", LOCATION = "", DESCRIPTION = "";
    private TextView txtResult, txtTitleResult, txtSearch, txtAddContact;
    private ImageView imgSearch,imgAddContact, qrCodeIV;
    private TextClock txtClock;
    private Intent intent;
    private LinearLayout llAddContact, button, lnShareImage, lnShareResult;
    private HistoryRecycleViewAdapter adapter;
    private List<History> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_icon)));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_result_scan);
        ORM();
        actionBar.setTitle(intent.getStringExtra("QRtitle"));
        XuLi();
        Copy();
        CopyResult();
        SaveImage();
        txtResult.setMovementMethod(new ScrollingMovementMethod());
    }

    private void SaveImage() {
        qrCodeIV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ResultScan.this);
                    builder.setMessage("Bạn có muốn lưu ảnh về máy không?")
                            .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String root = Environment.getExternalStoragePublicDirectory(
                                            Environment.DIRECTORY_PICTURES).toString();
                                    File myDir = new File(root + "/saved_images");
                                    myDir.mkdirs();

                                    Random generator = new Random();
                                    int n = 10000;
                                    n = generator.nextInt(n);
                                    String fname = "Image-"+ n +".jpg";
                                    File file = new File(myDir, fname);
                                    if (file.exists ()) file.delete ();

                                    try {
                                        FileOutputStream outputStream = new FileOutputStream(file);
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                                        outputStream.flush();
                                        outputStream.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    MediaScannerConnection.scanFile(ResultScan.this, new String[]{file.toString()}, null,
                                            new MediaScannerConnection.OnScanCompletedListener() {
                                                public void onScanCompleted(String path, Uri uri) {
                                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                                    Log.i("ExternalStorage", "-> uri=" + uri);
                                                }
                                            });
                                    Toast.makeText(ResultScan.this, "Save successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                return true;
            }
        });
    }

    private void CopyResult() {
        txtResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                copyTextToClipboard(txtResult.getText().toString());
                return true;
            }
        });
    }

    private void ORM() {
        txtSearch = findViewById(R.id.txt_search);
        imgSearch = findViewById(R.id.img_search);
        txtAddContact = findViewById(R.id.txt_add_contact);
        imgAddContact = findViewById(R.id.img_add_contact);
        llAddContact = findViewById(R.id.add_contact);
        txtResult = findViewById(R.id.txtResult);
        txtTitleResult = findViewById(R.id.txtTitleResult);
        button = findViewById(R.id.button);
        txtClock = findViewById(R.id.txtClock);
        lnShareImage = findViewById(R.id.shareImage);
        lnShareResult = findViewById(R.id.shareResult);
        qrCodeIV = findViewById(R.id.imageQR);
        String formatdate = "dd/MM/yyyy HH:mm";
        txtClock.setFormat24Hour(formatdate);
        intent = getIntent();
    }

    private void shareOtherApp() {
        lnShareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Image Description", null);
                    Uri screen = Uri.parse(path);
                    share.setType("image/jpeg");
                    share.putExtra(Intent.EXTRA_STREAM, screen);
                    startActivity(Intent.createChooser(share, "Share image"));
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ResultScan.this);
                    dialog.setMessage("Xin lỗi phiên bản android hiện tại của bạn không hổ trợ tính năng này :'(")
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
            }
        });

        lnShareResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, intent.getStringExtra("QRinfor"));
                startActivity(Intent.createChooser(share, "Share via"));

            }
        });
    }

    private void seeCode(String qrname, String result, boolean flag) {
        if(flag == true) {
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

            //setting this dimensions inside our qr code encoder to generate our qr code.
            qrgEncoder = new QRGEncoder(result, null, QRGContents.Type.TEXT, dimen);
            try {
                //getting our qrcode in the form of bitmap.
                bitmap = qrgEncoder.encodeAsBitmap();
                // the bitmap is set inside our image view using .setimagebitmap method.
                qrCodeIV.setImageBitmap(bitmap);
            } catch (WriterException e) {
                //this method is called for exception handling.
                Log.e("Tag", e.toString());
            }
        } else {
            try {
                Display display = getWindowManager().getDefaultDisplay();
                DisplayMetrics metrics = new DisplayMetrics();
                display.getMetrics(metrics);
                int widthImage = metrics.widthPixels;
                int heightImage = metrics.heightPixels;
                qrCodeIV.getLayoutParams().width = widthImage - 340;
                qrCodeIV.getLayoutParams().height = heightImage - 1930;
                qrCodeIV.setImageBitmap(CreateImage(result));
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

    }

    public Bitmap CreateImage(String message) throws WriterException
    {
        int size_width = 660;
        int size_height = 264;
        BitMatrix bitMatrix;
        // BitMatrix bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);
        String title = intent.getStringExtra("QRtitle");
        switch (title) {
            case "EAN_13":
                bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.EAN_13, size_width, size_height);
                break;
            case "EAN_8":
                bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.EAN_8, size_width, size_height);
                break;
            case "UPC_A":
                bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.UPC_A, size_width, size_height);
                break;
            case "UPC_E":
                bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.UPC_E, size_width, size_height);
                break;
            default:
                bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_128, size_width, size_height);
                break;
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int [] pixels = new int [width * height];
        for (int i = 0 ; i < height ; i++)
        {
            for (int j = 0 ; j < width ; j++)
            {
                if (bitMatrix.get(j, i))
                {
                    pixels[i * width + j] = 0xff000000;
                }
                else
                {
                    pixels[i * width + j] = 0xffffffff;
                }
            }
        }
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private void ClickLink(String s) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(s));
                startActivity(intent);
            }
        });
    }
    String title = "";
    public void XuLi() {
        String s, result;
        s = intent.getStringExtra("QRtitle");
        result = intent.getStringExtra("QRinfor");
//        String title = "";
        String ss = "";
        boolean flag = false;
        if( s.equals("QR_CODE")) {
            flag = true;
            int d = 0;
            for(int i = 0; i < result.length() ; i++) {
                if(result.charAt(i) == ':' || result.charAt(i) == '.') {
                    d = i;
                    break;
                }
                ss += result.charAt(i);
            }
            int count = 0;
            switch (ss.toUpperCase()) {
                case "HTTPS": count++;
                case "WWW": count++;
                case "HTTP": count++;
                    title = "URL";
                    txtTitleResult.setText(title);
                    for(int i = d ; i < result.length() ; i++) {
                        link += result.charAt(i);
                    }
                    txtSearch.setText("Truy cập liên kết");
                    imgSearch.setImageResource(R.drawable.ic_baseline_insert_link_36);
                    txtResult.setText(result);
                    String protocol = null;
                    if(count == 1)
                        protocol = "https:";
                    else if(count > 1)
                        protocol = "http:";
                    ClickLink(link.length() > 0 ? (protocol + link) : "");
                    break;
                case "MATMSG": title = "Email";
                    txtTitleResult.setText(title);
                    for(int i = d + 1 ; i < result.length() ; i++) {
                        if(result.charAt(i) == ':') {
                            for(int j = i + 1 ; j < result.length() ; j++) {
                                if(result.charAt(j) == ';') {
                                    d = j;
                                    count++;
                                    break;
                                }
                                if(count == 0)
                                    TO += result.charAt(j);
                                else if (count == 1)
                                    SUB += result.charAt(j);
                                else if (count == 2)
                                    BODY += result.charAt(j);
                            }
                        }else d = i;
                    }
                    txtSearch.setText("Gửi email từ " + TO);
                    imgSearch.setImageResource(R.drawable.ic_baseline_email_36);
                    txtResult.setText((TO.length() > 0 ? ("To: " + TO): "")
                            + (SUB.length() > 0 ? ("\nSubject: " + SUB): "")
                            + (BODY.length() > 0 ? ("\nMessage: " + BODY): ""));

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sendEmail();
                        }
                    });
                    break;
                case "WIFI": title = "Wifi";
                    txtTitleResult.setText(title);
                    for(int i = d + 1 ; i < result.length() ; i++) {
                        if(result.charAt(i) == ':') {
                            for(int j = i + 1 ; j < result.length() ; j++) {
                                if(result.charAt(j) == ';') {
                                    d = j;
                                    count++;
                                    break;
                                }
                                if(count == 0)
                                    T += result.charAt(j);
                                else if (count == 1)
                                    S += result.charAt(j);
                                else if (count == 2)
                                    P += result.charAt(j);
                                else if(count == 3)
                                    H += result.charAt(j);
                            }
                        }else d = i;
                    }
                    txtSearch.setText("Chuyển hướng tới mạng Wifi");
                    imgSearch.setImageResource(R.drawable.ic_baseline_wifi_36);
                    txtResult.setText((S.length() > 0 ? ("Network Name: " + S): "")
                            + (P.length() > 0 ? ("\nPassword: " + P): "")
                            + (T.length() > 0 ? ("\nEncrytion: " + T): "")
                            + "\nHidden: " + (H.equals("true")? H.toUpperCase() : "NO"));

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            accessToWifi();
                        }
                    });
                    break;
                case "TEL": title = "TELEPHONE";
                    txtTitleResult.setText(title);
                    for(int i = d + 1 ; i < result.length() ; i++) {
                        TEL += result.charAt(i);
                    }
                    txtSearch.setText("Quay số");
                    imgSearch.setImageResource(R.drawable.ic_baseline_local_phone_36);
                    txtAddContact.setText("Thêm liên hệ");
                    imgAddContact.setImageResource(R.drawable.ic_baseline_add_ic_call_36);
                    llAddContact.setVisibility(View.VISIBLE);
                    llAddContact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addContact(TEL);
                        }
                    });
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            accessToPhone();
                        }
                    });
                    txtResult.setText((TEL.length() > 0 ? ("Phone: " + TEL): ""));
                    break;
                case "SMSTO": title = "SMS";
                    txtTitleResult.setText(title);
                    for(int i = d + 1 ; i < result.length() ; i++) {
                        if(result.charAt(i) == ':') {
                            i++;
                            count++;
                        }
                        if(count == 0)
                            TEL += result.charAt(i);
                        else if (count == 1)
                            SMS += result.charAt(i);
                    }
                    txtSearch.setText("Gửi tin nhắn");
                    imgSearch.setImageResource(R.drawable.ic_baseline_sms_36);
                    txtAddContact.setText("Thêm liên hệ");
                    imgAddContact.setImageResource(R.drawable.ic_baseline_add_ic_call_36);
                    llAddContact.setVisibility(View.VISIBLE);
                    llAddContact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addContact(TEL);
                        }
                    });
                    txtResult.setText((TEL.length() > 0 ? ("Phone: " + TEL): "")
                            + (SMS.length() > 0 ? ("\nMessage: " + SMS): ""));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sendSMS();
                        }
                    });
                    break;
                case "GEO" : title = "GEO";
                    txtTitleResult.setText(title);
                    for(int i = d + 1 ; i < result.length() ; i++) {
                        if(result.charAt(i) == ',') {
                            i++;
                            count++;
                        }
                        if(count == 0)
                            LATITUDE += result.charAt(i);
                        else if (count == 1)
                            LONGITUDE += result.charAt(i);
                    }
                    txtSearch.setText("Truy cập vị trí");
                    imgSearch.setImageResource(R.drawable.ic_baseline_map_36);
                    txtResult.setText((LATITUDE.length() > 0 ? ("LATITUDE: " + LATITUDE): "")
                            + (LONGITUDE.length() > 0 ? ("\nLONGITUDE: " + LONGITUDE): ""));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            accessToMap();
                        }
                    });
                    break;
                case "BEGIN":
                    for(int i = d + 1; i < result.length(); i++) {
                        if(result.charAt(i) == '\n') {
                            d = i+1;
                            break;
                        }
                        Json += result.charAt(i);
                    }
                    if(Json.equals("VCARD")) {
                        Json = "";
                        title = "VCARD";
                        txtTitleResult.setText(title);
                        for(int i = 0; i < result.length(); i++) {
                            for(int j = i; j < result.length(); j++) {
                                if(result.charAt(j) == ':' || result.charAt(j) == '\n') {
                                    i = j;
                                    break;
                                }
                                Json += result.charAt(j);
                            }
                            if(Json.equals("FN") || Json.equals("FN;CHARSET=UTF-8")) {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                    FN += result.charAt(j);
                                }
                            } else if(Json.equals("ORG")) {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                    ORG += result.charAt(j);
                                }
                            } else if(Json.equals("TITLE")) {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                    TITLE += result.charAt(j);
                                }
                            } else if(Json.equals("ADR") || Json.equals("ADR;TYPE=HOME")) {
                                String string = "";
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j-1) == '\n') {
                                        i = j;
                                        count++;
                                        break;
                                    }
                                    if(result.charAt(j) == ';' || result.charAt(j) == '\n') {
                                        if(string.length() > 0)
                                            if(result.charAt(j) == '\n')
                                                ADR += string;
                                            else
                                                ADR += string + ", ";
                                        string = "";
                                        continue;
                                    }
                                    string += result.charAt(j);
                                }
                            } else if(Json.equals("TEL;WORK;VOICE") || Json.equals("TEL;TYPE=WORK,VOICE")) {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                    WORK += result.charAt(j);
                                }
                            } else if(Json.equals("TEL;CELL") || Json.equals("TEL;TYPE=HOME,VOICE") || Json.equals("TEL")) {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                    CELL += result.charAt(j);
                                }
                            } else if(Json.equals("TEL;FAX") || Json.equals("TEL;TYPE=FAX,WORK,VOICE") || Json.equals("FAX")) {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                    FAX += result.charAt(j);
                                }
                            } else if(Json.equals("EMAIL;TYPE=PREF,INTERNET") || Json.equals("EMAIL")) {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                    EMAIL1 += result.charAt(j);
                                }
                            } else if(Json.equals("EMAIL;WORK;INTERNET") || Json.equals("EMAIL;TYPE=WORK,INTERNET")) {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                    EMAIL2 += result.charAt(j);
                                }
                            } else if(Json.equals("URL")) {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                    URL += result.charAt(j);
                                }
                            } else {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                }
                            }
                            Json = "";
                        }
//                        (EMAIL1.length() > 0 ? ("\nEmail: " + EMAIL1): ""
                        txtResult.setText((FN.length() > 0 ? ("Full name: " + FN): "")
                                + (ORG.length() > 0 ? ("\nCompany: " + ORG): "")
                                + (TITLE.length() > 0 ? ("\nJob: " + TITLE): "")
                                + (ADR.length() > 0 ? ("\nAddress: " + ADR): "")
                                + (WORK.length() > 0 ? ("\nTelephone: " + WORK): "")
                                + (CELL.length() > 0 ? ("\nMobile number: " + CELL): "")
                                + (FAX.length() > 0 ? ("\nFax: " + FAX): "")
                                + (EMAIL1.length() > 0 ? ("\nEmail: " + EMAIL1): "")
                                + (EMAIL2.length() > 0 ? ("\nEmail: " + EMAIL2): "")
                                + (URL.length() > 0 ? ("\nLink: " + URL): "")) ;
                        txtAddContact.setText("Thêm liên hệ");
                        imgAddContact.setImageResource(R.drawable.ic_baseline_add_ic_call_36);
                        llAddContact.setVisibility(View.VISIBLE);
                        llAddContact.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                addContactVcard(FN, ORG, TITLE, ADR, WORK, CELL, FAX, EMAIL1, EMAIL2, URL);
                            }
                        });
                    } else if(Json.equals("VEVENT")) {
                        title = "EVENT";
                        txtTitleResult.setText(title);
                        Json = "";
                        for(int i = 0; i < result.length(); i++) {
                            for(int j = i; j < result.length(); j++) {
                                if(result.charAt(j) == ':') {
                                    i = j;
                                    break;
                                }
                                Json += result.charAt(j);
                            }
                            if (Json.equals("SUMMARY")) {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                    SUMMARY += result.charAt(j);
                                }
                            } else if (Json.equals("DTSTART")) {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                    DTSTART += result.charAt(j);
                                }
                            } else if (Json.equals("DTEND")) {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                    DTEND += result.charAt(j);
                                }
                            } else if (Json.equals("LOCATION")) {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                    LOCATION += result.charAt(j);
                                }
                            } else if (Json.equals("DESCRIPTION")) {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                    DESCRIPTION += result.charAt(j);
                                }
                            } else {
                                for(int j = i + 1; j < result.length(); j++) {
                                    if(result.charAt(j) == '\n') {
                                        i = j;
                                        break;
                                    }
                                }
                            }
                            Json = "";
                        }

                        txtResult.setText((SUMMARY.length() > 0 ? ("Tiêu đề: " + SUMMARY): "")
                                + (DTSTART.length() > 0 ? ("\nNgày bắt đầu: " + DTSTART): "")
                                + (DTEND.length() > 0 ? ("\nNgày kết thúc: " + DTEND): "")
                                + (LOCATION.length() > 0 ? ("\nĐịa điểm: " + LOCATION): "")
                                + (DESCRIPTION.length() > 0 ? ("\nMô tả: " + DESCRIPTION): ""));
                        txtAddContact.setText("Thêm lịch trình");
                        imgAddContact.setImageResource(R.drawable.ic_baseline_edit_calendar_36);
                        llAddContact.setVisibility(View.VISIBLE);
                        llAddContact.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Calendar calender = Calendar.getInstance();
                                Intent cal = new Intent(Intent.ACTION_EDIT);
                                cal.setType("vnd.android.cursor.item/event");
                                cal.putExtra("dtstart", DTSTART);
                                cal.putExtra("dtend", DTEND);
                                cal.putExtra("rrule", "FREQ=YEARLY");
                                cal.putExtra("allDay", 1);
                                cal.putExtra("title", SUMMARY);
                                cal.putExtra("description", DESCRIPTION);
                                cal.putExtra("eventLocation", LOCATION);
                                startActivity(cal);
                            }
                        });
                    }
                    button.setVisibility(View.GONE);
                    break;
                default: txtTitleResult.setText("Text");
                    title = "Text";
                    txtResult.setText(result);
                    button.setVisibility(View.GONE);
                    break;
            }
        } else {
            flag = false;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '_')
                    break;
                ss += s.charAt(i);
            }
            if (ss.equals("EAN") || ss.equals("UPC")) {
                txtTitleResult.setText("Product");
                ClickLink("https://www.google.com/search?q=" + result);
            } else {
                txtTitleResult.setText("Text");
                button.setVisibility(View.GONE);
            }
            txtResult.setText(result);
        }

        shareOtherApp();
        seeCode(title, result, flag);
    }

    private void accessToMap() {
        Intent map = new Intent(Intent.ACTION_VIEW);
        map.setData(Uri.parse("geo:" + LATITUDE + "," + LONGITUDE + "?q=" + LATITUDE + "+" + LONGITUDE));
        startActivity(map);
    }

    private void sendSMS() {
        Intent sms = new Intent(Intent.ACTION_SENDTO);
        sms.putExtra("sms_body", SMS);
        sms.setData(Uri.parse("sms:" + TEL));
        startActivity(sms);
    }

    private void accessToPhone() {
        Intent call = new Intent(Intent.ACTION_CALL);
        call.setData(Uri.parse("tel:" + TEL));
        startActivity(call);
    }

    private void accessToWifi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ResultScan.this);
        if(T.equals("WEP")) {
            builder.setMessage("Rất tiếc, phương pháp mã hoá WEP không còn được Google hỗ trợ kể từ phiên bản Android 10 :(")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
        } else {
            builder.setMessage("Chuyển hướng đến mạng Wifi: " + S
                    + ". \nMật khẩu của bạn đã được sao chép, ấn OK để chuyển hướng đến cài đặt.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent wifi = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivity(wifi);
                            copyTextToClipboard(P);
                            WifiConfiguration conf = new WifiConfiguration();
                            conf.SSID = "\"" + S + "\"";
                            conf.wepKeys[0] = "\"" + P + "\"";

                            WifiManager manager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                            int netWorkID = manager.addNetwork(conf);
                            if(netWorkID == -1) {
                                conf.wepKeys[0] = P;
                                netWorkID = manager.addNetwork(conf);
                            }
                            manager.disconnect();
                            manager.enableNetwork(netWorkID, true);
                            manager.reconnect();
                        }
                    })
                    .setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
        }
    }

    private void sendEmail() {
        Intent send = new Intent(Intent.ACTION_SENDTO);
        send.setData(Uri.parse("mailto:" + TO + "?subject=" + SUB + "&body=" + BODY));
        send.putExtra(Intent.EXTRA_SUBJECT,"Email subject" + SUB);
        send.putExtra(Intent.EXTRA_TEXT, "Email body" + BODY);
        startActivity(send);
    }

    private void copyTextToClipboard(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("label", text);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(ResultScan.this, "Copy success!!!", Toast.LENGTH_SHORT).show();
    }

    private void addContact(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber);
        startActivity(intent);
    }

    private void addContactVcard(String name, String company, String job,
                                 String adr, String Telephone, String mobileNumber,
                                 String fax, String mail1, String mail2, String url) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, mobileNumber);
        intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_EMAIL_TYPE, R.string.SECONDARY_EMAIL_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_EMAIL, mail1);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, mail2);
        intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE_TYPE , R.string.SECONDARY_EMAIL_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE , Telephone);
        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, adr);
        intent.putExtra(ContactsContract.Intents.Insert.TERTIARY_PHONE_TYPE, R.string.TERTIARY_PHONE_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.TERTIARY_PHONE, fax);
        intent.putExtra("website", url);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
    public  void doCopy(){
        this.clipboardManager= (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        Intent intent = getIntent();
        String copy = intent.getStringExtra("QRinfor");
        ClipData clipData = ClipData.newPlainText("copy",copy);
        clipboardManager.setPrimaryClip(clipData);
    }

    public void Copy(){
        SharedPreferences sharedPreferences = getSharedPreferences("copy",0);
        boolean check = sharedPreferences.getBoolean("copy",false);
        if(check==true){
            doCopy();
            Toast.makeText(this, "Copy success", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void finish() {
        super.finish();
    }
}
