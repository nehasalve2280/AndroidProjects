package com.example.lenovo.nehatestproject.view.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.nehatestproject.R;

class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView tvName,tvId,tvEmail,tvAddress;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        tvId = itemView.findViewById(R.id.tv_id);
        tvName = itemView.findViewById(R.id.tv_name);
        tvEmail = itemView.findViewById(R.id.tv_email);
        tvAddress = itemView.findViewById(R.id.tv_address);
    }
}
