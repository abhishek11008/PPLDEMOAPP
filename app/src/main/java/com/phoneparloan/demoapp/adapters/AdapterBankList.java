package com.phoneparloan.demoapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.phoneparloan.demoapp.Objects.SmsParsedBankData;
import com.phoneparloan.demoapp.R;
import com.phoneparloan.demoapp.Utils.Log;

import java.util.ArrayList;

/**
 * Created by abhisheksingh on 11/17/17.
 */

public class AdapterBankList extends RecyclerView.Adapter<AdapterBankList.ViewHolder> {
    Context context;
    Activity activity;
    ArrayList<SmsParsedBankData> items;

    public AdapterBankList(Context context, Activity activity, ArrayList<SmsParsedBankData> items){
        this.context = context;
        this.activity = activity;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_banks_seggregation,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        SmsParsedBankData data = getItem(pos);
        holder.firstField.setText(data.getBankName());
        Log.d("abhi_check","getCrediterName->"+data.getBankName());
        Glide.with(context)
        .load(data.getBankImage())
        .into(holder.secondField);
        Log.d("abhi_check","getCrediterAmount->"+data.getBankImage());


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

        TextView firstField;
        ImageView secondField;

        public ViewHolder(View itemView) {
            super(itemView);
            firstField = (TextView) itemView.findViewById(R.id.firstField);
            secondField = (ImageView) itemView.findViewById(R.id.secondField);
        }
    }
}
