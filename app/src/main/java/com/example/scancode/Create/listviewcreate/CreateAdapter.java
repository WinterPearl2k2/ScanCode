package com.example.scancode.Create.listviewcreate;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scancode.R;

import java.util.ArrayList;

public class CreateAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Create> arraycreate;

    public CreateAdapter(Activity activity, ArrayList<Create> arraycreate) {
        this.activity = activity;
        this.arraycreate = arraycreate;
    }

    @Override
    public int getCount() {
        return arraycreate.size();
    }

    @Override
    public Object getItem(int i) {
        return arraycreate.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Tạo Inflate
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.create_item_listview, null);

        //Ánh Xạ
        TextView tvTitle = (TextView ) view.findViewById(R.id.createTitle);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.createImage);

        //Gán
        Create create = arraycreate.get(i);

        tvTitle.setText(create.getNameItem());
        ivIcon.setBackgroundResource(create.getIcon());

//        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.translate_list);
//        view.startAnimation(animation);

        return view;
    }
}
