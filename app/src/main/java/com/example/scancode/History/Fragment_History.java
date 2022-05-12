package com.example.scancode.History;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.scancode.History.listviewhistory.CreateHistoryDatabase;
import com.example.scancode.History.listviewhistory.HistoryRecycleViewAdapter;
import com.example.scancode.History.listviewhistory.History;
import com.example.scancode.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_History extends Fragment {
    ListView lvHistory;
    ArrayList<History> arrayHistory;

    public static HistoryRecycleViewAdapter adapter;
    Intent intent;
    private List<History> historyList;
    RecyclerView recyclerView;
    public static ArrayList<History> arraySelected = new ArrayList<>();
    public static boolean isActionMode = false;
    public static ActionMode actionmode = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan_history, container, false);

        //AnhXa
        recyclerView = view.findViewById(R.id.recycleviewScanHistory);

        adapter = new HistoryRecycleViewAdapter();
        historyList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
        historyList = CreateHistoryDatabase.getInstance(getActivity()).historyDAO().getListHistory();
        adapter.setData(getActivity(), historyList);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        historyList = CreateHistoryDatabase.getInstance(getActivity()).historyDAO().getListHistory();
        adapter.setData(getActivity(), historyList);
    }

}