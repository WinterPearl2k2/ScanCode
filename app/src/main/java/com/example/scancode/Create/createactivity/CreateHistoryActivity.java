package com.example.scancode.Create.createactivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.scancode.Create.listviewcreate.CreateHistoryAdapter;
import com.example.scancode.History.database.DataBase;
import com.example.scancode.History.listviewhistory.History;
import com.example.scancode.R;

import java.util.ArrayList;

public class CreateHistoryActivity extends AppCompatActivity {
    public static DataBase dataBase;
    public static CreateHistoryAdapter adapter;
    public static ListView lvHistory;
    Intent intent;
    public static ArrayList<History> arrayHistory;
    public static ArrayList<History> arraySelected = new ArrayList<>();
    public static boolean isActionMode = false;
    public static ActionMode actionmode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_history);
        InitActionBar();
        AnhXa();
        InitArray();

        //AnhXa
        lvHistory = findViewById(R.id.listviewCreateHistory);
        //Set Adapter
        adapter = new CreateHistoryAdapter(this, arrayHistory);
        lvHistory.setAdapter(adapter);
        lvHistory.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvHistory.setMultiChoiceModeListener(modeListener);
        Log.e("TAG", "10");

        dataBase = new DataBase(this, "ghichu.sqlite", null, 1);

        Log.e("TAG", "11");
//        dataBase.QueryData("DROP TABLE CreateHistory");

        Log.e("TAG", "12");// Tao bang cong viec nameItem, desItem, timeItem;
        dataBase.QueryData("CREATE TABLE IF NOT EXISTS CreateHistory(id INTEGER PRIMARY KEY AUTOINCREMENT, qrname VARCHAR, qrinfor VARCHAR,  qrtime VARCHAR)");

        Log.e("TAG", "10");
        GetData();
    }

    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
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
            switch (menuItem.getItemId()) {
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

    private void InitArray() {
        arrayHistory = new ArrayList<>();

//        arrayHistory.add(new History("Text", "Này bạn ơi", "10/11/2002", R.drawable.ic_document_48));
//        arrayHistory.add(new History("Wifi", "THANH LONG", "10/11/2002", R.drawable.ic_wifi_48));
//        arrayHistory.add(new History("Contact", "Long Bien", "10/11/2002", R.drawable.ic_contact_24));
//        arrayHistory.add(new History("SMS", "Tôi mượn", "10/11/2002", R.drawable.ic_sms_24));
//        arrayHistory.add(new History("Email", "nlbien@gmail.com", "10/11/2002", R.drawable.ic_mail_24));
//        arrayHistory.add(new History("Event", "Event", "15/11/2002", R.drawable.ic_event_48));
//        arrayHistory.add(new History("Link", "facebook.com", "10/11/2002", R.drawable.ic_global_48));
    }

    private void InitActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create History");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.to_right_1, R.anim.to_right_2);
    }

    private void AnhXa() {
    }
    public static void GetData(){
        Cursor data = dataBase.GetData("SELECT * FROM CreateHistory");
        arrayHistory.clear();
        while(data.moveToNext()){
            int id = data.getInt(0);
            String ten = data.getString(1);
            String infor = data.getString(2);
            String time = data.getString(3);
            arrayHistory.add(new History(ten, infor, time, R.drawable.ic_document_48));
        }
        adapter.notifyDataSetChanged();
    }
}