package com.example.scancode.Setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.scancode.R;
import com.example.scancode.Setting.Help.Help;

import java.util.Locale;

public class Fragment_Setting extends PreferenceFragmentCompat {

      Locale locale;
    SwitchPreference sw_ligth,sw_beep,sw_copy,sw_vibrate;
    Preference p_language,p_introduction,p_help,p_feedback;
    SharedPreferences sp_ligth,sp_beep,sp_copy,sp_vibrate,sp_language;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.root_preference, rootKey);
        initUI();
        Beep();
        Vibrate();
        Copy();
        Language();
        DarkMode();
        Introduction();
        Feedback();
        Help();
    }
    public void initUI(){
        sw_beep     = findPreference("sound");
        sw_vibrate  = findPreference("vi");
        sw_copy     = findPreference("copy");
        sw_ligth    = findPreference("v");
        p_language  = findPreference("language");
        p_introduction= findPreference("introduc");
        p_help      = findPreference("help");
        p_feedback  = findPreference("feedback");
    }
    public void DarkMode(){
        sp_ligth = getActivity().getSharedPreferences("darkmode",0);
        boolean check = sp_ligth.getBoolean("darkmode",false);
        if(check==false)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        sw_ligth.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(sw_ligth.isChecked()==false)
                {
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SharedPreferences.Editor editor = sp_ligth.edit();
                    editor.putBoolean("darkmode",true);
                    editor.commit();

                }
                else
                {

                     AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPreferences.Editor editor = sp_ligth.edit();
                    editor.putBoolean("darkmode",false);
                    editor.commit();
                }

                return true;

            }
        });
    }
    public void Language(){
        sp_language = getActivity().getSharedPreferences("language",0);
        p_language.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Language");
                final String [] listlanguage={"English","VietNam"};
                int check  = sp_language.getInt("language",1);
                SharedPreferences.Editor editor = sp_language.edit();
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

                return true;
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
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        super.onConfigurationChanged(newConfig);
    }
    public void Copy(){
        sp_copy = getContext().getSharedPreferences("copy",0);
        sw_copy.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(sw_copy.isChecked()== false){
                    SharedPreferences.Editor editor = sp_copy.edit();
                    editor.putBoolean("copy",true);
                    editor.commit();
                }
                else{
                    SharedPreferences.Editor editor = sp_copy.edit();
                    editor.putBoolean("copy",false);
                    editor.commit();
                }
                return true;
            }
        });
    }
    public void Beep(){
        sp_beep = getContext().getSharedPreferences("beep",0);
        sw_beep.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(sw_beep.isChecked()== false){
                    SharedPreferences.Editor editor = sp_beep.edit();
                    editor.putBoolean("beep",true);
                    editor.commit();
                }
                else{
                    SharedPreferences.Editor editor = sp_beep.edit();
                    editor.putBoolean("beep",false);
                    editor.commit();
                }
                return true;
            }
        });
    }

    public void Help() {
        p_help.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Intent intent = new Intent(getContext(), Help.class);
                startActivity(intent);
                return true;
            }
        });
    }

    public void Vibrate(){
        sp_vibrate = getContext().getSharedPreferences("vibrate",0);
        sw_vibrate.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(sw_vibrate.isChecked()== false){
                    SharedPreferences.Editor editor = sp_vibrate.edit();
                    editor.putBoolean("vibrate",true);
                    editor.commit();
                }
                else{
                    SharedPreferences.Editor editor = sp_vibrate.edit();
                    editor.putBoolean("vibrate",false);
                    editor.commit();
                }
                return true;
            }
        });
    }

    public void Introduction(){
        p_introduction.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Intent intent = new Intent(getContext(),Main_introduction.class);
                startActivity(intent);
                return true;
            }
        });
    }
    public  void Feedback(){
        p_feedback.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Intent intent = new Intent(getContext(),Feebback.class);
                startActivity(intent);
                return true;
            }
        });
    }
}