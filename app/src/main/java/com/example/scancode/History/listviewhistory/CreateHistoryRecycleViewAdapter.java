package com.example.scancode.History.listviewhistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scancode.R;

import java.util.List;

public class CreateHistoryRecycleViewAdapter extends RecyclerView.Adapter<CreateHistoryRecycleViewAdapter.CreateHistoryViewHolder>{

    private List<History> historyList;
    public void setData(List<History> list){
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
            case "VCARD":
                holder.imgQrtype.setImageResource(R.drawable.ic_contact_24);
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

        }
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

        public CreateHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQrinfor= itemView.findViewById(R.id.hisDesc);
            tvQrtime= itemView.findViewById(R.id.hisTime);
            imgQrtype = itemView.findViewById(R.id.hisIcon);
            cbQrselect = itemView.findViewById(R.id.checkboxHistory);
        }
    }
}
