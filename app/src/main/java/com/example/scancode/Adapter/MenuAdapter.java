package com.example.scancode.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.scancode.setting.Setting;
import com.example.scancode.Create.Fragment_Create;
import com.example.scancode.History.Fragment_History;
import com.example.scancode.Scan.FragmentScanHome;

public class MenuAdapter extends FragmentStateAdapter {
    public MenuAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentScanHome();
            case 1:
                return new Fragment_Create();
            case 2:
                return new Fragment_History();
            case 3:
                return new Setting();
            default:
                return new FragmentScanHome();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
