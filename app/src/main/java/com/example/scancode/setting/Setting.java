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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
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
    TextView languague,help,introduc,feedback;
    TableRow btn_Verison;
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
        sw_ligth = view.findViewById(R.id.sw_light);
        languague = view.findViewById(R.id.tv);
        sw_vibrate = view.findViewById(R.id.sw_vibrate);
        sw_beep = view.findViewById(R.id.sw_sound);
        sw_copy =view.findViewById(R.id.sw_copy);
        introduc = view.findViewById(R.id.introduction);
        help = view.findViewById(R.id.help);
        feedback = view.findViewById(R.id.feedback);
        btn_Verison = view.findViewById(R.id.version);
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
       // setCheck(sp_beep,"beep",false);
        boolean check = sp_beep.getBoolean("beep",false);
        if(check==true)
            sw_beep.setChecked(true);
        else
            sw_beep.setChecked(false);
        sw_beep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Sharepreference(sp_beep,"beep",true);
                }
                else{
                    Sharepreference(sp_beep,"beep",false);
                }
            }
        });
    }
    public void Copy(){
        SharedPreferences sp_copy = getContext().getSharedPreferences("copy",0);
        boolean check = sp_copy.getBoolean("copy",false);
        if(check==true)
            sw_copy.setChecked(true);
        else
            sw_copy.setChecked(false);
        sw_copy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Sharepreference(sp_copy,"copy",true);
                }
                else
                    Sharepreference(sp_copy,"copy",false);
            }
        });

    }
    public void Vibrate(){
        SharedPreferences sp_vibrate = getContext().getSharedPreferences("vibrate",0);
        boolean check = sp_vibrate.getBoolean("vibrate",false);
        if(check==true)
            sw_vibrate.setChecked(true);
        else
            sw_vibrate.setChecked(false);
        sw_vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
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
    public  void Feedback(){
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Feebback.class);
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