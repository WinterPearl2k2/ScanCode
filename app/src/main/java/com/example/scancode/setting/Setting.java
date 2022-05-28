package com.example.scancode.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.scancode.BuildConfig;
import com.example.scancode.R;
import com.example.scancode.setting.help.Help;

import java.util.Locale;

public class Setting extends Fragment {
    Switch sw_ligth,sw_beep,sw_vibrate,sw_copy;
    TableRow languague,help,introduc,feedback;
    TableRow btn_Verison,tb_dark,tb_sound,tb_vibrate,tb_copy;
    static Locale locale;static boolean touched=false;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        sw_ligth = view.findViewById(R.id.sw_light);
        languague = view.findViewById(R.id.tv);
        initUI(view);
        DarkMode();
        Language();
        Beep();
        Vibrate();
        Copy();
        Introduction();
        Feedback();
        Help();
        Version();
        return view;
    }

    private void Version() {
        btn_Verison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Versions " + BuildConfig.VERSION_NAME +
                        " " +BuildConfig.VERSION_CODE, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initUI(View view){
        tb_vibrate   = view.findViewById(R.id.tb_vibrate);
        tb_sound     = view.findViewById(R.id.tb_sound);
        tb_copy      = view.findViewById(R.id.tb_copy);
        tb_dark      = view.findViewById(R.id.tb_light);
        sw_ligth     = view.findViewById(R.id.sw_light);
        languague    = view.findViewById(R.id.tv);
        sw_vibrate   = view.findViewById(R.id.sw_vibrate);
        sw_beep      = view.findViewById(R.id.sw_sound);
        sw_copy      =view.findViewById(R.id.sw_copy);
        introduc     = view.findViewById(R.id.introduction);
        help         = view.findViewById(R.id.help);
        feedback     = view.findViewById(R.id.feedback);
        btn_Verison  = view.findViewById(R.id.version);
    }
    public void Sharepreference(SharedPreferences sharedPreferences,String name, Boolean value){
       // sharedPreferences = getActivity().getSharedPreferences(name,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name,value);
        editor.commit();
    }

    public void Sharepreference2(SharedPreferences sharedPreferences,String name, int value){
        // sharedPreferences = getActivity().getSharedPreferences(name,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name,value);
        editor.commit();
    }


    public void Beep(){
        SharedPreferences sp_sound = getActivity().getSharedPreferences("beep", 0);
        boolean check = sp_sound.getBoolean("beep",false);
        if(check==true)
        {
            sw_beep.setChecked(true);
        }
        else
        {
            sw_beep.setChecked(false);

        }
        tb_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCheck_switch(sw_beep,sp_sound,"beep");
            }
        });
    }
    public void Copy(){
        SharedPreferences sp_copy = getActivity().getSharedPreferences("copy", 0);
        boolean check = sp_copy.getBoolean("copy",false);
        if(check==true)
        {
            sw_copy.setChecked(true);
        }
        else
        {
            sw_copy.setChecked(false);

        }
        tb_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCheck_switch(sw_copy,sp_copy,"copy");
            }
        });

    }
    public void Vibrate(){
        SharedPreferences sp_vibrate = getActivity().getSharedPreferences("vibrate", 0);
        boolean check = sp_vibrate.getBoolean("vibrate",false);
        if(check==true)
        {
            sw_vibrate.setChecked(true);
        }
        else
        {
            sw_vibrate.setChecked(false);

        }
        tb_vibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCheck_switch(sw_vibrate,sp_vibrate,"vibrate");
            }
        });
    }
    public void Introduction(){
        introduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Main_introduction.class);
                startActivity(intent);
            }
        });
    }
    public void Help() {
       help.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getContext(), Help.class);
               startActivity(intent);
           }
       });
    }
    public  void Feedback(){
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Feebback.class);
                startActivity(intent);
            }
        });
    }
    public void setCheck_switch(Switch sw,SharedPreferences sharedPreferences,String name ){
        boolean check = sharedPreferences.getBoolean(name,false);
        if(check==false){
            sw.setChecked(true);
            Sharepreference(sharedPreferences,name,true);
        }
        else{
            sw.setChecked(false);
            Sharepreference(sharedPreferences,name,false);
        }
    }
    public void setCheck(SharedPreferences sharedPreferences){
        boolean check = sharedPreferences.getBoolean("darkmode",false);

            if(check==false)
            {
                sw_ligth.setChecked(true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Sharepreference(sharedPreferences,"darkmode",true);//true
            }
            else
            {
                sw_ligth.setChecked(false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Sharepreference(sharedPreferences,"darkmode",false);
            }
    }
    public void DarkMode() {
        SharedPreferences sp_ligth = getActivity().getSharedPreferences("darkmode", 0);
        boolean check = sp_ligth.getBoolean("darkmode",false);
        if(check==true)
        {
            sw_ligth.setChecked(true);
        }
        else
        {
            sw_ligth.setChecked(false);

        }
        tb_dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCheck(sp_ligth);
            }
        });
    }


    public void Language(){

        SharedPreferences sp_languague = getActivity().getSharedPreferences("language",0);
        languague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Language");
                final String [] listlanguage={"English","VietNam"};
                int check  = sp_languague.getInt("language",1);

                builder.setSingleChoiceItems(listlanguage, check, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==1)
                        {
                            locale = new Locale("vi");
                            ChangeLanguage(locale);
                            Sharepreference2(sp_languague,"language",1);
                        }
                        else
                        {
                            locale = new Locale("en");
                            ChangeLanguage(locale);
                            Sharepreference2(sp_languague,"language",0);
                        }
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