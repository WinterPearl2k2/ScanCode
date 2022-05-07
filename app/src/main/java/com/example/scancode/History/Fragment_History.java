package com.example.scancode.History;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.scancode.History.listviewhistory.History;
import com.example.scancode.History.listviewhistory.ScanHistoryAdapter;
import com.example.scancode.R;

import java.util.ArrayList;

public class Fragment_History extends Fragment {
    static ScanHistoryAdapter adapter;
    ListView lvHistory;
    ArrayList<History> arrayHistory;

    public static ArrayList<History> arraySelected = new ArrayList<>();
    public static boolean isActionMode = false;
    public static ActionMode actionmode = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan_history, container, false);
        InitArray();

        //AnhXa
        lvHistory = view.findViewById(R.id.listviewScanHistory);
        //Set Adapter
        adapter = new ScanHistoryAdapter(getActivity(), arrayHistory);
        lvHistory.setAdapter(adapter);
        lvHistory.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvHistory.setMultiChoiceModeListener(modeListener);
        return view;
    }


    public static AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.history_menu, menu);
            isActionMode = true;
            actionmode = actionMode;
//            MainActivity.EnableBottomNav(View.GONE);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.delete_history:
                    adapter.RemoveItems(arraySelected);
                    actionMode.finish();

                    return true;
                case R.id.copy_history:
                    actionMode.finish();

                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            isActionMode = false;
            actionmode = null;
            arraySelected.clear();
//            MainActivity.EnableBottomNav(View.VISIBLE);
        }

    };

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.to_right_2,R.anim.to_left_1);
        fragmentTransaction.replace(R.id.viewPager2, fragment);
        fragmentTransaction.commit();
    }

    private void InitArray(){
        arrayHistory = new ArrayList<>();

        arrayHistory.add(new History("Text", "Này bạn ơi", "10/11/2002", R.drawable.ic_document_48));
        arrayHistory.add(new History("Wifi", "THANH LONG", "10/11/2002", R.drawable.ic_wifi_48));
        arrayHistory.add(new History("Contact", "Long Bien", "10/11/2002", R.drawable.ic_contact_24));
        arrayHistory.add(new History("SMS", "Tôi mượn", "10/11/2002", R.drawable.ic_sms_24));
        arrayHistory.add(new History("Email", "nlbien@gmail.com", "10/11/2002", R.drawable.ic_mail_24));
        arrayHistory.add(new History("Event", "Event","15/11/2002" , R.drawable.ic_event_48));
        arrayHistory.add(new History("Link", "facebook.com", "10/11/2002", R.drawable.ic_global_48));

    }
}