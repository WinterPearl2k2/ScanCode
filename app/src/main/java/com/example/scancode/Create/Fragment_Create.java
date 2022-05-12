package com.example.scancode.Create;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.scancode.Create.listviewcreate.Create;
import com.example.scancode.Create.listviewcreate.CreateRecycleViewAdapter;
import com.example.scancode.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Create extends Fragment {
    CreateRecycleViewAdapter adapter;
    ListView lvCreate;
    RecyclerView recyclerView;
    Intent intent;
    List<Create> createList;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create, container, false);

        //AnhXa
        recyclerView = view.findViewById(R.id.recycleviewCreate);

        adapter = new CreateRecycleViewAdapter();
        createList = new ArrayList<>();
        InitArray();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
        adapter.setData(getActivity(), createList);
        return view;
    }
    private void AnhXa(){

    }

    private void InitArray(){
        createList = new ArrayList<>();

        createList.add(new Create("Text", R.drawable.ic_document_24));
        createList.add(new Create("Wifi", R.drawable.ic_wifi_48));
        createList.add(new Create("Contact", R.drawable.ic_contact_24));
        createList.add(new Create("SMS", R.drawable.ic_sms_24));
        createList.add(new Create("Email", R.drawable.ic_mail_24));
        createList.add(new Create("URL", R.drawable.ic_global_48));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(adapter != null)
            adapter.Release();
    }
}