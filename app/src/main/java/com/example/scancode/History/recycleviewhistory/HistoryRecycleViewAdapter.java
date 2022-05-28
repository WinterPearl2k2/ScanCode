package com.example.scancode.History.recycleviewhistory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scancode.R;
import com.example.scancode.Scan.ResultScan;

import java.util.List;

public class HistoryRecycleViewAdapter extends RecyclerView.Adapter<HistoryRecycleViewAdapter.CreateHistoryViewHolder>{

    private List<History> historyList;
    private Context context;
    private Intent intent;
    private InterfaceItemClick interfaceItemClick;

    public interface InterfaceItemClick{
        void deleteHistory(History history);
    }

    public HistoryRecycleViewAdapter(InterfaceItemClick interfaceItemClick) {
        this.interfaceItemClick = interfaceItemClick;
    }
    public void setData(Context context, List<History> list){
        this.context = context;
        this.historyList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CreateHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_recycleview, parent, false);
        return new CreateHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateHistoryViewHolder holder, int position) {
        History history = historyList.get(position);
        int i = historyList.size()-position;
        if (history == null)
            return;

        holder.tvQrinfor.setText(history.getDesItem());
        holder.tvQrtime.setText(history.getTimeItem());
        switch (history.getNameItem()) {
            case "Text":
                holder.imgQrtype.setImageResource(R.drawable.ic_document_24);
                break;
            case "Wifi":
                holder.imgQrtype.setImageResource(R.drawable.ic_wifi_24);
                break;
            case "Contact":
                holder.imgQrtype.setImageResource(R.drawable.ic_contact_24);
                break;
            case "VCARD":
                holder.imgQrtype.setImageResource(R.drawable.ic_contact_mail_24);
                break;
            case "SMS":
                holder.imgQrtype.setImageResource(R.drawable.ic_sms_24);
                break;
            case "Email":
                holder.imgQrtype.setImageResource(R.drawable.ic_mail_24);
                break;
            case "URL":
                holder.imgQrtype.setImageResource(R.drawable.ic_global_24);
                break;
            case "TELEPHONE":
                holder.imgQrtype.setImageResource(R.drawable.ic_call_24);
                break;
            case "GEO":
                holder.imgQrtype.setImageResource(R.drawable.ic_map_24);
                break;
            case "EVENT":
                holder.imgQrtype.setImageResource(R.drawable.ic_event_24);
                break;
            case "Product":
                holder.imgQrtype.setImageResource(R.drawable.icon_barcode_32);
                break;
        }
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResultScan.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("QRtitle", history.getFormat());
                mBundle.putString("QRinfor", history.getDesItem());
                intent.putExtras(mBundle);
                context.startActivity(intent);
//                Toast.makeText(context, history.getFormat(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.layoutItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                interfaceItemClick.deleteHistory(history);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(historyList != null)
            return historyList.size();
        return 0;
    }

    public class CreateHistoryViewHolder extends RecyclerView.ViewHolder{

        private TextView tvQrinfor, tvQrtime;
        private ImageView imgQrtype;
        private CheckBox cbQrselect;
        private ConstraintLayout layoutItem;

        public CreateHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQrinfor= itemView.findViewById(R.id.hisDesc);
            tvQrtime= itemView.findViewById(R.id.hisTime);
            imgQrtype = itemView.findViewById(R.id.hisIcon);
            cbQrselect = itemView.findViewById(R.id.checkboxHistory);
            layoutItem = itemView.findViewById(R.id.history_recycleView_item);
        }
    }
}
