package com.example.scancode.History;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.scancode.Create.listviewcreate.CreateRecycleViewAdapter;
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan_history, container, false);

        //AnhXa
        recyclerView = view.findViewById(R.id.recycleviewScanHistory);

        adapter = new HistoryRecycleViewAdapter(new HistoryRecycleViewAdapter.InterfaceItemClick() {
            @Override
            public void deleteHistory(History history) {
                onLongClicktoDelete(history);
            }
        });
        historyList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
        loadData();
        return view;
    }

    private void onLongClicktoDelete(History history) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete History")
                .setMessage("Bạn có chắc chắn muốn xóa ?"). setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                CreateHistoryDatabase.getInstance(getActivity()).historyDAO().deleteHistory(history);
                loadData();
            }
        }).setNegativeButton("No", null).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        historyList = CreateHistoryDatabase.getInstance(getActivity()).historyDAO().getListHistory();
        adapter.setData(getActivity(), historyList);
    }

}