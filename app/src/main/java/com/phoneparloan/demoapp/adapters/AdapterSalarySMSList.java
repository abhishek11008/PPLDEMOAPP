package com.phoneparloan.demoapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.phoneparloan.demoapp.Objects.SmsParsedBankData;
import com.phoneparloan.demoapp.R;
import com.phoneparloan.demoapp.Utils.Log;

import java.util.ArrayList;

/**
 * Created by abhisheksingh on 11/17/17.
 */

public class AdapterSalarySMSList extends RecyclerView.Adapter<AdapterSalarySMSList.ViewHolder> {
    Context context;
    Activity activity;
    ArrayList<SmsParsedBankData> items;
    boolean selected =false;

    public AdapterSalarySMSList(Context context, Activity activity, ArrayList<SmsParsedBankData> items){
        this.context = context;
        this.activity = activity;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_banks_salary_sms,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pos) {
        SmsParsedBankData data = getItem(pos);

        holder.salarySMSField.setText(data.getSalarySMS());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selected) {
                    holder.thirdField.setImageResource(R.drawable.ic_check_filled);
                    selected=true;
                }else{
                    holder.thirdField.setImageResource(R.drawable.grey_circle);
                    selected=false;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public SmsParsedBankData getItem(int i){
        return items.get(i);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateField;
        TextView salarySMSField;
        ImageView thirdField;
        RelativeLayout mainLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            dateField =  itemView.findViewById(R.id.dateField);
            salarySMSField =  itemView.findViewById(R.id.salarySMSField);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            thirdField = itemView.findViewById(R.id.thirdField);
        }
    }
}
