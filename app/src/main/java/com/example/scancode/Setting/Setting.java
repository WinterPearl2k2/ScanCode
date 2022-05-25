package com.example.scancode.Setting;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.scancode.R;

import java.util.Locale;

public class Setting extends Fragment {
    Switch sw;
    TextView tv;
    static Locale locale;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        sw= view.findViewById(R.id.sw_light);
        tv= view.findViewById(R.id.tv);
        DarkMode();
        Language();

        return view;
    }
    public void DarkMode(){
        SharedPreferences sp = getActivity().getSharedPreferences("darkmode",0);
       sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(sw.isChecked())
               {    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                   SharedPreferences.Editor editor = sp.edit();
                   editor.putBoolean("darkmode",true);

               }

               else
               {
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                   SharedPreferences.Editor editor = sp.edit();
                   editor.putBoolean("darkmode",false);
               }

           }
       });
    }
    public void Language(){

        SharedPreferences st= getActivity().getSharedPreferences("language",0);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Language");
                final String [] listlanguage={"English","VietNam"};
                int check  = st.getInt("language",1);
                SharedPreferences.Editor editor = st.edit();
                builder.setSingleChoiceItems(listlanguage, check, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==1)
                        {
                            locale = new Locale("vi");ChangeLanguage(locale);
                            editor.putInt("language",1);

                        }
                        else
                        {
                            locale = new Locale("en");ChangeLanguage(locale);
                            editor.putInt("language",0);

                        }
                        editor.commit();


                    }
                });

                AlertDialog alert = builder.show();
            }
        });

    }
    public void ChangeLanguage(Locale locale) {
        Locale.setDefault(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        getActivity().recreate();

    }
}