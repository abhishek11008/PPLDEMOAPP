package com.phoneparloan.demoapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.phoneparloan.demoapp.Objects.PermissionPojo;
import com.phoneparloan.demoapp.R;

import java.util.ArrayList;

public class PermissionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<PermissionPojo> arrayListData;

    public PermissionsAdapter(Context context, ArrayList<PermissionPojo> arrayListData) {
        this.context = context;
        this.arrayListData = arrayListData;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.permissions_helper_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        for(int i=1;i<arrayListData.size();i++){
            Glide.with(context)
                    .load(arrayListData.get(position).getId())
                    .into(itemViewHolder.imageViewHolder);
            itemViewHolder.titleViewHolder.setText(arrayListData.get(position).getTitle());
            itemViewHolder.descriptionViewHolder.setText(arrayListData.get(position).getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return arrayListData.size();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewHolder;
        TextView titleViewHolder,descriptionViewHolder;

        ItemViewHolder(View viewHolder) {
            super(viewHolder);
            imageViewHolder = viewHolder.findViewById(R.id.imageViewHolder);
            titleViewHolder = viewHolder.findViewById(R.id.titleViewHolder);
            descriptionViewHolder = viewHolder.findViewById(R.id.descriptionViewHolder);

        }

    }
}
