package com.example.scancode.Create.createactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scancode.History.database.DataBase;
import com.example.scancode.History.listviewhistory.CreateHistoryDatabase;
import com.example.scancode.History.listviewhistory.CreateHistoryRecycleViewAdapter;
import com.example.scancode.History.listviewhistory.History;
import com.example.scancode.R;

import java.util.ArrayList;
import java.util.List;

public class CreateHistoryActivity extends AppCompatActivity {
    public static DataBase dataBase;
    public static CreateHistoryRecycleViewAdapter adapter;
    Intent intent;
    private List<History> historyList;
    RecyclerView recyclerView;
    public static ArrayList<History> arraySelected = new ArrayList<>();
    public static boolean isActionMode = false;
    public static ActionMode actionmode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_history);
        InitActionBar();
        AnhXa();

        recyclerView = findViewById(R.id.recycleview);
        adapter = new CreateHistoryRecycleViewAdapter();
        historyList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        historyList = CreateHistoryDatabase.getInstance(this).historyDAO().getListUser();
        adapter.setData(historyList );

    }

    private void InitArray() {
        historyList = new ArrayList<>();
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

}