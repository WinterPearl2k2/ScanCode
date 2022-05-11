package com.example.scancode.Setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scancode.R;

import java.util.Locale;

public class Fragment_Setting extends PreferenceFragmentCompat {
    static  Locale locale;
    SwitchPreference sw_ligth,sw_beep,sw_copy,sw_vibrate;
    Preference p_language,p_introduction,p_help;
    SharedPreferences sp_ligth,sp_beep,sp_copy,sp_vibrate,sp_language;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preference, rootKey);
        initUI();
        DarkMode();
        Language();
        Copy();
        Vibrate();
        Beep();
    }
    public void initUI(){
        sw_beep     = findPreference("sound");
        sw_vibrate  = findPreference("vi");
        sw_copy     = findPreference("copy");
        sw_ligth    = findPreference("v");
        p_language  = findPreference("language");
        p_introduction= findPreference("introduc");
        p_help      = findPreference("help");
    }
    public void DarkMode(){
        sp_ligth = getContext().getSharedPreferences("darkmode",0);

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
        sp_language = getContext().getSharedPreferences("language",0);
        p_language.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Language");
                final String [] listlanguage={"English","VietNam"};
                int check  = sp_language.getInt("language",1);

                builder.setSingleChoiceItems(listlanguage, check, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==1)
                        {
                            locale = new Locale("vi","VN");
                            SharedPreferences.Editor editor = sp_language.edit();
                            editor.putInt("language",1);
                            editor.commit();
                        }
                        else
                        {
                            locale = new Locale("en","US");
                            SharedPreferences.Editor editor = sp_language.edit();
                            editor.putInt("language",0);
                            editor.commit();
                        }

                        ChangeLanguage(locale);

                    }
                });

                AlertDialog alert = builder.create();
                builder.show();
                return true;
            }
        });
    }
    public void ChangeLanguage(Locale locale) {
        Resources resources = this.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = new Configuration(resources.getConfiguration());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            configuration.setLocale(locale);
        else
            configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);

        Intent intent = new Intent(getContext(),getContext().getClass());
        startActivity(intent);
        getActivity().finish();

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

    public void Vibrate(){
        sp_vibrate = getContext().getSharedPreferences("beep",0);
        sw_vibrate.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(sw_vibrate.isChecked()== false){
                    SharedPreferences.Editor editor = sp_vibrate.edit();
                    editor.putBoolean("beep",true);
                    editor.commit();
                }
                else{
                    SharedPreferences.Editor editor = sp_vibrate.edit();
                    editor.putBoolean("beep",false);
                    editor.commit();
                }
                return true;
            }
        });
    }
}