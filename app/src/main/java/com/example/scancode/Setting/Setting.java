package com.example.scancode.Setting;

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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.scancode.R;
import com.example.scancode.Setting.Help.Help;

import java.util.Locale;

public class Setting extends Fragment {
    Switch sw_ligth,sw_beep,sw_vibrate,sw_copy;
    TextView languague,help,introduc;
    static Locale locale;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        sw_ligth = view.findViewById(R.id.sw_light);
        languague = view.findViewById(R.id.tv);
        initUI(view);
        DarkMode();
        Language();

        return view;
    }
    public void initUI(View view){
        sw_ligth = view.findViewById(R.id.sw_light);
        languague = view.findViewById(R.id.tv);

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
    public void setCheck(SharedPreferences sharedPreferences,String name, Boolean value){
        boolean check = sharedPreferences.getBoolean(name,value);
        if(check==true)
            sw_ligth.setChecked(true);
        else
            sw_ligth.setChecked(false);
    }

    public void Beep(){
        SharedPreferences sp_beep = getContext().getSharedPreferences("beep",0);
        setCheck(sp_beep,"beep",false);
        sw_beep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(sw_beep.isChecked()==true){
                    Sharepreference(sp_beep,"beep",true);
                }
                else{
                    Sharepreference(sp_beep,"beep",false);
                }
            }
        });
    }

    public void Vibrate(){
        SharedPreferences sp_vibrate = getContext().getSharedPreferences("vibrate",0);
        setCheck(sp_vibrate,"vibrate",false);
        sw_vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(sw_vibrate.isChecked()==true){
                    Sharepreference(sp_vibrate,"vibrate",true);
                }
                else{
                    Sharepreference(sp_vibrate,"vibrate",false);
                }
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

    public void DarkMode(){
        SharedPreferences sp_ligth = getActivity().getSharedPreferences("darkmode",0);
        setCheck(sp_ligth,"darkmode",false);
       sw_ligth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(sw_ligth.isChecked()==true)
               {
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                   Sharepreference(sp_ligth,"darkmode",true);
               }

               else
               {
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                   Sharepreference(sp_ligth,"darkmode",false);
               }
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