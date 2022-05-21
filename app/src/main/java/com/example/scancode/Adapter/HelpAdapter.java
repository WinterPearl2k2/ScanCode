package com.example.scancode.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scancode.R;
import com.example.scancode.Setting.Help.HelpItem;

import java.util.ArrayList;
import java.util.List;

public class HelpAdapter extends BaseAdapter {
    private Context mContext;
    private int mLayout;
    private List<HelpItem> mHelpItemList;

    public HelpAdapter(Context mContext, int mLayout, ArrayList<HelpItem> mHelpItemList) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mHelpItemList = mHelpItemList;
    }

    @Override
    public int getCount() {
        return mHelpItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder {
        TextView content;
        ImageView icon;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mLayout, null);

            viewHolder = new ViewHolder();
            viewHolder.content = view.findViewById(R.id.txt_Help);
            viewHolder.icon = view.findViewById(R.id.img_Help);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

            viewHolder.content.setText(mHelpItemList.get(i).content);
            viewHolder.icon.setImageResource(mHelpItemList.get(i).image);

        return view;
    }
}
