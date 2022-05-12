package com.example.scancode.Create;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.scancode.Create.createactivity.ContactActivity;
import com.example.scancode.Create.createactivity.EmailActivity;
import com.example.scancode.Create.createactivity.LinkActivity;
import com.example.scancode.Create.createactivity.SMSActivity;
import com.example.scancode.Create.createactivity.TextActivity;
import com.example.scancode.Create.createactivity.WifiActivity;
import com.example.scancode.Create.listviewcreate.Create;
import com.example.scancode.Create.listviewcreate.CreateAdapter;
import com.example.scancode.R;

import java.util.ArrayList;


public class Fragment_Create extends Fragment {
    CreateAdapter adapter;
    ListView lvCreate;
    Intent intent;
    ArrayList<Create> arrayCreate;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        InitArray();

        //AnhXa
        lvCreate = view.findViewById(R.id.listviewCreate);

        //Set Adapter
        adapter = new CreateAdapter(getActivity(), arrayCreate);
        lvCreate.setAdapter(adapter);

        lvCreate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (arrayCreate.get(i).getNameItem()){
                    case "Contact":
                        Log.e("TAG", "1");
                        intent = new Intent(getActivity() , ContactActivity.class);
                        Log.e("TAG", "2");

                        break;
                    case "Text":
                        intent = new Intent(getActivity(), TextActivity.class);
                        break;
                    case "Wifi":
                        intent = new Intent(getActivity(), WifiActivity.class);
                        break;
                    case "URL":
                        intent = new Intent(getActivity(), LinkActivity.class);
                        break;
                    case "Email":
                        intent = new Intent(getActivity(), EmailActivity.class);
                        break;
                    case "SMS":
                        intent = new Intent(getActivity(), SMSActivity.class);
                        break;
                }
                startActivity(intent);
                Log.e("TAG", "3");

                getActivity().overridePendingTransition(R.anim.to_left_1, R.anim.to_left_2);

            }
        });

        return view;
    }
    private void AnhXa(){

    }

    private void InitArray(){
        arrayCreate = new ArrayList<>();

        arrayCreate.add(new Create("Text", R.drawable.ic_document_24));
        arrayCreate.add(new Create("Wifi", R.drawable.ic_wifi_48));
        arrayCreate.add(new Create("Contact", R.drawable.ic_contact_24));
        arrayCreate.add(new Create("SMS", R.drawable.ic_sms_24));
        arrayCreate.add(new Create("Email", R.drawable.ic_mail_24));
        arrayCreate.add(new Create("URL", R.drawable.ic_global_48));
    }


}