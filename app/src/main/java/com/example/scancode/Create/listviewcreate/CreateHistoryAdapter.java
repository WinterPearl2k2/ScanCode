package com.example.scancode.Create.listviewcreate;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.scancode.Create.createactivity.CreateHistoryActivity;
import com.example.scancode.History.listviewhistory.History;
import com.example.scancode.R;

import java.util.ArrayList;
import java.util.List;

public class CreateHistoryAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<History> arrayhistory;

    public CreateHistoryAdapter(Activity activity, ArrayList<History> arrayhistory) {
        this.activity = activity;
        this.arrayhistory = arrayhistory;
    }

    @Override
    public int getCount() {
        return arrayhistory.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayhistory.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Tạo Inflate
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.history_item_listview, null);

        //Ánh Xạ
        TextView tvDesc = (TextView ) view.findViewById(R.id.hisDesc);
        TextView tvTime = (TextView ) view.findViewById(R.id.hisTime);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.hisIcon);
        CheckBox cbItem = view.findViewById(R.id.checkboxHistory);
        cbItem.setTag(i);
        //Gán
        History history = arrayhistory.get(i);

        tvDesc.setText(history.getDesItem());
        tvTime.setText(history.getTimeItem());
        ivIcon.setBackgroundResource(history.getIcon());
        if(CreateHistoryActivity.isActionMode){
            cbItem.setVisibility(View.VISIBLE);
        }
        else{
            cbItem.setVisibility(View.GONE);
        }

        cbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int position = (int) compoundButton.getTag();
                if(CreateHistoryActivity.arraySelected.contains(arrayhistory.get(position))) {
                    CreateHistoryActivity.arraySelected.remove(arrayhistory.get(position));
                }
                else{
                    CreateHistoryActivity.arraySelected.add(arrayhistory.get(position));
                }
                CreateHistoryActivity.actionmode.setTitle(
                        CreateHistoryActivity.arraySelected.size() == 0 ? "": CreateHistoryActivity.arraySelected.size() + " Selected");

            }
        });

        return view;
    }
    public void RemoveItems(List<History> arrayItems){
        for(History item : arrayItems){
            arrayhistory.remove(item);
        }
        arrayItems.clear();
        notifyDataSetChanged();
    }
}
