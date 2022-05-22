package com.example.scancode.Create.listviewcreate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scancode.Create.createactivity.ContactActivity;
import com.example.scancode.Create.createactivity.EmailActivity;
import com.example.scancode.Create.createactivity.LinkActivity;
import com.example.scancode.Create.createactivity.SMSActivity;
import com.example.scancode.Create.createactivity.TextActivity;
import com.example.scancode.Create.createactivity.WifiActivity;
import com.example.scancode.R;

import java.util.List;

public class CreateRecycleViewAdapter extends RecyclerView.Adapter<CreateRecycleViewAdapter.CreateViewHolder>{

    private List<Create> createList;
    private Context context;
    private Intent intent;
    public void setData(Context context, List<Create> list){
        this.context = context;
        this.createList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CreateRecycleViewAdapter.CreateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_item_recycleview, parent, false);
        return new CreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateViewHolder holder, int position) {
        Create create = createList.get(position);
        if (create == null)
            return;

        holder.tvTitle.setText(create.getNameItem());



        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.getAdapterPosition()){
                    case 0:
                        intent = new Intent(context, TextActivity.class);
                        break;
                    case 1:
                        intent = new Intent(context, WifiActivity.class);
                        break;
                    case 2:
                        intent = new Intent(context , ContactActivity.class);
                        break;
                    case 3:
                        intent = new Intent(context, SMSActivity.class);
                        break;
                    case 4:
                        intent = new Intent(context, EmailActivity.class);
                        break;
                    case 5:
                        intent = new Intent(context, LinkActivity.class);
                        break;
                }
                context.startActivity(intent);
            }
        });

        switch (holder.getAdapterPosition()) {
            case 0:
                holder.ivIcon.setImageResource(R.drawable.ic_document_24);
                break;
            case 1:
                holder.ivIcon.setImageResource(R.drawable.ic_wifi_24);
                break;
            case 2:
                holder.ivIcon.setImageResource(R.drawable.ic_contact_24);
                break;
            case 3:
                holder.ivIcon.setImageResource(R.drawable.ic_sms_24);
                break;
            case 4:
                holder.ivIcon.setImageResource(R.drawable.ic_mail_24);
                break;
            case 5:
                holder.ivIcon.setImageResource(R.drawable.ic_global_24);
                break;

        }
    }
    public void Release(){
        context = null;
    }
    @Override
    public int getItemCount() {
        if(createList != null)
            return createList.size();
        return 0;
    }

    public class CreateViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private ImageView ivIcon;
        private ConstraintLayout layoutItem;

        public CreateViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle= itemView.findViewById(R.id.createTitle);
            ivIcon= itemView.findViewById(R.id.createImage);
            layoutItem = itemView.findViewById(R.id.create_recycleView_item);
        }
    }
}
