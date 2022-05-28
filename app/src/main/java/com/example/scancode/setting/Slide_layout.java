package com.example.scancode.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.scancode.BuildConfig;
import com.example.scancode.R;

public class Slide_layout extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public  Slide_layout(Context context)
    {
        this.context=context;
    }
    public int[] slide_image={ R.drawable.img_phone_qr,R.drawable.img_protect,R.drawable.img_gift};

    public  String [] slide_text={
        "Scan QR code and Barcode.","Avoid common mistakes when scanning barcodes.","No advertising and free."
    };

    public String [] slideTitle = {"Scan QR App", "", ""};
    public String [] slideVersion = {"Version: " + BuildConfig.VERSION_NAME, "", ""};

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView slideimgae= view.findViewById(R.id.imageview1);
        TextView sildetext= view.findViewById(R.id.textview1);
        TextView txtTitleIntro = view.findViewById(R.id.txtTitleIntro);
        TextView txtVer = view.findViewById(R.id.txtVersion);
        slideimgae.setImageResource(slide_image[position]);
        sildetext.setText(slide_text[position]);
        txtTitleIntro.setText(slideTitle[position]);
        txtVer.setText(slideVersion[position]);

        container.addView(view);
        return view;
     }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
