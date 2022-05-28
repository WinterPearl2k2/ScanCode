package com.example.scancode.Scan;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.scancode.History.recycleviewhistory.CreateHistoryDatabase;
import com.example.scancode.History.recycleviewhistory.History;
import com.example.scancode.History.recycleviewhistory.HistoryRecycleViewAdapter;
import com.example.scancode.R;
import com.example.scancode.setting.Main_introduction;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.google.zxing.Result;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentScanHome} factory method to
 * create an instance of this fragment.
 */
public class FragmentScanHome extends Fragment {

    private final static int REQUEST_PER = 100;
    public final static String ALLOW_KEY = "ALLOWED";
    public final static String CAMERA_PREF = "camera_pref";
    private ConstraintLayout noScan;
    private FrameLayout scan;
    private boolean mCheck = false, mFlash = true, mSwitch = true;
    private View view;
    private CodeScanner mCodeScanner;
    private LinearLayout btn_Cam, btn_Gallery;
    private SeekBar zoom_Frame, zoom_Camera;
    private ImageButton btn_QRCode, btn_BarCode, btn_Increase, btn_Decrease,
            btn_Plus_Frame, btn_Minus_Frame, btn_Flash,
            btn_Rotate_Cam, btn_Scan_Gallery;
    private CodeScannerView scannerView;
    private HistoryRecycleViewAdapter adapter;
    private List<History> historyList;
    SharedPreferences preferences;
    boolean introduce = true;
    private static final String MY_PREF = "MyPrefsFile";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Language();
        view = inflater.inflate(R.layout.fragment_scan_home, container, false);
        Introduce();
        ORM();
        EventCam();
        EventGallery();
        Scan();
        ZoomFrame();
        ZoomCamera();
        FlashSwitch();
        RotateCamera();
        ScanGallery();
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            mCheck = true;
            openScan();
        }
        return view;
    }

    public void Language(){

        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("language",0);
        int item = sharedPreferences1.getInt("language",1);
        switch (item) {
            case 0:
                Locale localeEN = new Locale("en");
                setLocale(localeEN);
                break;
            case 1:
                Locale localeHU = new Locale("vi");
                setLocale(localeHU);
                break;

        }
    }

    public void setLocale(Locale locale) {
        Locale.setDefault(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        //recreate();
        //finish();
        //startActivity(getIntent());
        //if these are not commented, main activity wont show at start at all
    }

    private void Introduce() {
        preferences = getActivity().getSharedPreferences(MY_PREF, MODE_PRIVATE);
        introduce = preferences.getBoolean("noIntroduce", true);
        Intent intent = new Intent(getActivity(), Main_introduction.class);
        if(introduce) {
            startActivity(intent);
        }
    }
    private void ScanGallery() {
        btn_Scan_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanByGallery();
            }
        });
    }

    private void EventGallery() {
        btn_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanByGallery();
            }
        });
    }

    private void EventCam() {
        btn_Cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
    }

    private void ORM() {
        scan = view.findViewById(R.id.scan);
        noScan = view.findViewById(R.id.noScan);
        btn_Cam = view.findViewById(R.id.btn_cam);
        btn_Gallery = view.findViewById(R.id.btn_gallery);
        btn_QRCode = view.findViewById(R.id.btn_QRCODE);
        btn_BarCode = view.findViewById(R.id.btn_BarCode);
        btn_Increase = view.findViewById(R.id.increase);
        btn_Decrease = view.findViewById(R.id.decrease);
        zoom_Frame = view.findViewById(R.id.zoom_frame);
        zoom_Camera = view.findViewById(R.id.zoom_camera);
        btn_Minus_Frame = view.findViewById(R.id.minus_frame);
        btn_Plus_Frame = view.findViewById(R.id.plus_frame);
        scannerView = view.findViewById(R.id.scanner_view);
        btn_Flash = view.findViewById(R.id.btn_flash);
        btn_Rotate_Cam = view.findViewById(R.id.rotate_cam);
        btn_Scan_Gallery = view.findViewById(R.id.scan_gallery);
        mCodeScanner = new CodeScanner(getActivity(), scannerView);
    }

    private void ScanByGallery() {
        Intent photoItent = new Intent(Intent.ACTION_PICK);
        photoItent.setType("image/*");
        startActivityForResult(photoItent, 101);

    }

    private void Scan() {
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(result.getText().length() > 0) {
                            Vibrate();
                            Sound();
                            addHistory(result.getText(), result.getBarcodeFormat().toString());
                            Intent intent = new Intent(getActivity(), ResultScan.class);
                            intent.putExtra("QRinfor", result.getText());
                            intent.putExtra("QRtitle", result.getBarcodeFormat().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.notice_unable_to_scan), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(view -> {
            mCodeScanner.startPreview();
            mCodeScanner.setAutoFocusEnabled(true);
        });
    }

    private void addHistory(String result, String format) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
        String qrtime = df.format(Calendar.getInstance().getTime());
        adapter = new HistoryRecycleViewAdapter(new HistoryRecycleViewAdapter.InterfaceItemClick() {
            @Override
            public void deleteHistory(History history) {

            }
        });
        historyList = new ArrayList<>();
        adapter.setData(getActivity(), historyList);
        History history = new History(format, getTITLE(result, format), result, qrtime);
        CreateHistoryDatabase.getInstance(getActivity()).historyDAO().insertHistory(history);
    }

    private String getTITLE(String result, String format) {
        String title = "";
        String ss = "", Json = "";

        if( format.equals("QR_CODE")) {
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
                    break;
                case "MATMSG": title = "Email";
                    break;
                case "WIFI": title = "Wifi";
                    break;
                case "TEL": title = "TELEPHONE";
                    break;
                case "SMSTO": title = "SMS";
                    break;
                case "GEO" : title = "GEO";
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
                    } else if(Json.equals("VEVENT")) {
                        title = "EVENT";
                    }
                    break;
                default:
                    title = "Text";
                    break;
            }
        } else {
            for (int i = 0; i < format.length(); i++) {
                if (format.charAt(i) == '_')
                    break;
                ss += format.charAt(i);
            }
            if (ss.equals("EAN") || ss.equals("UPC")) {
                title = "Product";
            } else {
                title = "Text";
            }
        }

        return title;
    }

    //Sound and Beep
    public void Vibrate(){
        SharedPreferences vibrate;
        vibrate = getContext().getSharedPreferences("vibrate",0);
        boolean check2 = vibrate.getBoolean("vibrate",false);
        if(check2==true){
            Viber(getContext(),"on");
        }
        else
            Viber(getContext(),"off");

    }
    //Sound
    public void Sound(){
        SharedPreferences beep;
        beep = getContext().getSharedPreferences("beep",0);
        boolean check1 = beep.getBoolean("beep",false);
        if(check1 ==true){
            final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
            tg.startTone(ToneGenerator.TONE_PROP_BEEP);
        }

    }
    //Vibrate
    @JavascriptInterface
    public void Viber(Context cn, String value) {
        if (value.equals("on")) {
            // Get instance of Vibrator from current Context
            Vibrator v = (Vibrator) cn.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 300 milliseconds
            v.vibrate(300);
        }

    }

    private void RotateCamera() {
        btn_Rotate_Cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSwitch == true) {
                    mCodeScanner.setCamera(1);
                    mCodeScanner.setAutoFocusEnabled(true);
                    mSwitch = false;
                }else {
                    mCodeScanner.setCamera(0);
                    mCodeScanner.setAutoFocusEnabled(true);
                    mSwitch = true;
                }
            }
        });
    }

    private void FlashSwitch() {
        btn_Flash.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if(mFlash == true) {
                    mCodeScanner.setFlashEnabled(true);
                    mFlash = false;
                    btn_Flash.setImageResource(R.drawable.ic_flash_off);
                }else {
                    mCodeScanner.setFlashEnabled(false);
                    mFlash = true;
                    btn_Flash.setImageResource(R.drawable.ic_flash_on);
                }
            }
        });
    }

    private void ZoomCamera() {
        btn_Increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zoom_Camera.getProgress() >= zoom_Camera.getMax()) {
                    mCodeScanner.setZoom(zoom_Camera.getProgress());
                }else {
                    zoom_Camera.setProgress(zoom_Camera.getProgress() + 5);
                    mCodeScanner.setZoom(zoom_Camera.getProgress());
                }
            }
        });
        btn_Decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zoom_Camera.getProgress() <= 0) {
                    mCodeScanner.setZoom(zoom_Camera.getProgress());
                }else {
                    zoom_Camera.setProgress(zoom_Camera.getProgress() - 5);
                    mCodeScanner.setZoom(zoom_Camera.getProgress());
                }
            }
        });

        zoom_Camera.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mCodeScanner.setZoom(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void ZoomFrame() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            zoom_Frame.setMax(80);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                zoom_Frame.setMin(20);
            }
            zoom_Frame.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        zoom_Frame.setMin(20);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            btn_Plus_Frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (zoom_Frame.getProgress() >= 80) {
                        scannerView.setFrameSize(zoom_Frame.getProgress() / 100F);
                    } else {
                        zoom_Frame.setProgress(zoom_Frame.getProgress() + 6);
                        scannerView.setFrameSize(zoom_Frame.getProgress() / 100F);
                    }
                }
            });

            btn_Minus_Frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (zoom_Frame.getProgress() <= 20) {
                        scannerView.setFrameSize(zoom_Frame.getProgress() / 100F);
                    } else {
                        zoom_Frame.setProgress(zoom_Frame.getProgress() - 6);
                        scannerView.setFrameSize(zoom_Frame.getProgress() / 100F);
                    }
                }
            });

            zoom_Frame.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    scannerView.setFrameSize(i / 100F);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
//                scannerView.setFrameSize(zoom_Frame.getProgress()/100F);
                }
            });
        } else {
            zoom_Frame.setVisibility(View.GONE);
            btn_Plus_Frame.setVisibility(View.GONE);
            btn_Minus_Frame.setVisibility(View.GONE);
        }
        btn_QRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    zoom_Frame.setMin(20);
                }

                scannerView.setFrameAspectRatioWidth(1);
                btn_BarCode.setBackgroundResource(R.drawable.cus_btn_scanqr);
                btn_QRCode.setBackgroundResource(R.drawable.cus_btn_scanqr2);
            }
        });

        btn_BarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    zoom_Frame.setMin(40);
                }

                scannerView.setFrameAspectRatioWidth(3);
                btn_BarCode.setBackgroundResource(R.drawable.cus_btn_scanqr2);
                btn_QRCode.setBackgroundResource(R.drawable.cus_btn_scanqr);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.setAutoFocusEnabled(true);
        if (mCheck == true)
            mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        if(mCheck == true)
            mCodeScanner.releaseResources();
        super.onPause();
    }


    public static void saveToPreferences(Context context, String key, Boolean allowed) {
        SharedPreferences mPrefs = context.getSharedPreferences(CAMERA_PREF, MODE_PRIVATE);
        SharedPreferences.Editor prefersEditor = mPrefs.edit();
        prefersEditor.putBoolean(key, allowed);
        prefersEditor.commit();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences mPrefs = context.getSharedPreferences(CAMERA_PREF, MODE_PRIVATE);
        return (mPrefs.getBoolean(key, false));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            mCheck = true;
            openScan();
        }else if(requestCode == 101) {
            if(data == null || data.getData()==null) {
                Log.e("TAG", "The uri is null, probably the user cancelled the image selection process using the back button.");
                return;
            }
            Uri uri = data.getData();

            InputStream inputStream = null;
            try {
                inputStream = getActivity().getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            InputImage inputImage = InputImage.fromBitmap(bitmap, 0);

            scanBarcodes(inputImage);

        } else if(requestCode == 100 && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            mCheck = true;
            openScan();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void scanBarcodes(InputImage inputImage) {

        // [START get_detector]
        BarcodeScanner scanner = BarcodeScanning.getClient();
        // Or, to specify the formats to recognize:
        // BarcodeScanner scanner = BarcodeScanning.getClient(options);
        // [END get_detector]

        // [START run_detector]
        Task<List<Barcode>> result = scanner.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        // Task completed successfully
                        // [START_EXCLUDE]-
                        // [START get_barcodes]
                        String title = "";
                        String rawValue = "";
                        for (Barcode barcode: barcodes) {
                            rawValue = barcode.getRawValue();

                            // See API reference for complete list of supported types

                            switch (barcode.getFormat()) {
                                case 32:
                                    title = "EAN_13";
                                    break;
                                case 64:
                                    title = "EAN_8";
                                    break;
                                case 512:
                                    title = "UPC_A";
                                    break;
                                case 1024:
                                    title = "UPC_E";
                                    break;
                                case 1:
                                    title = "CODE_128";
                                    break;
                                case 256:
                                    title = "QR_CODE";
                                    break;
                                default:
                                    title = "CODE_128";
                                    break;
                            }
//                            String title = String.valueOf(barcode.getDisplayValue());
//
                            Vibrate();
                        }
                        if(rawValue.length() > 0) {
                            addHistory(rawValue, title);
                            Intent intent = new Intent(getActivity(), ResultScan.class);
                            intent.putExtra("QRinfor", rawValue);
                            intent.putExtra("QRtitle", title);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(),getString(R.string.notice_unable_to_scan_image), Toast.LENGTH_SHORT).show();
                        }
                        // [END get_barcodes]
                        // [END_EXCLUDE]
                    }
                });
        // [END run_detector]
    }

    private void checkPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                if(getFromPref(getActivity(), ALLOW_KEY)) {
                    showSetting();
                } else if(ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.CAMERA)) {
                        showPer();
                    } else {
                        requestPermissions(new String[] {Manifest.permission.CAMERA}, REQUEST_PER);
//                        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, requestPer);
                    }
                }
            } else {
                mCheck = true;
                openScan();
            }
        } else {
            mCheck = true;
            openScan();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_PER: {

                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCheck = true;
                    openScan();
                    break;
                }

                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (getActivity(), permission);
                        if (showRationale) {
                            showPer();
//                            saveToPreferences(getActivity(), ALLOW_KEY, true);
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(getActivity(), ALLOW_KEY, true);
                        }
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void showPer() {
        new AlertDialog.Builder(getActivity()).setCancelable(false).setTitle(R.string.showper_title)//hello
                .setMessage(R.string.showper_message)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, requestPer);
                        requestPermissions(new String[] {Manifest.permission.CAMERA}, REQUEST_PER);

                    }
                }).show();
    }

    private void showSetting() {
        new AlertDialog.Builder(getActivity()).setCancelable(false).setTitle(R.string.showper_title)
                .setMessage(R.string.showsetting_message)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton(getString(R.string.settings), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 200);
                    }
                }).show();
    }

    private void openScan() {
//        Fragment_Scan fragment2 = new Fragment_Scan();
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment1, fragment2);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
        scan.setVisibility(View.VISIBLE);
        noScan.setVisibility(View.GONE);
    }
}