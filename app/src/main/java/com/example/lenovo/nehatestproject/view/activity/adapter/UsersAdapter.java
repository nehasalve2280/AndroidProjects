package com.example.lenovo.nehatestproject.view.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.lenovo.nehatestproject.R;
import com.example.lenovo.nehatestproject.database.User;

import java.util.ArrayList;

import io.realm.RealmResults;

public class UsersAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context mContext;
    ArrayList<User> mUserResponses;

    public UsersAdapter(Context mContext) {
        this.mContext = mContext;
        mUserResponses = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.content_adapter_user_list_item, viewGroup, false);
        final MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        User user = mUserResponses.get(i);
        myViewHolder.tvName.setText(user.getName());
        myViewHolder.tvId.setText(user.getId()+"");
        myViewHolder.tvEmail.setText(user.getEmail());
        myViewHolder.tvAddress.setText(user.getAddress());
    }

    @Override
    public int getItemCount() {
        return mUserResponses.size();
    }
    public void add(User r) {
        mUserResponses.add(r);
        notifyItemInserted(mUserResponses.size() - 1);
    }

    public void addAll(RealmResults<User> users) {
        for (User result : users) {
            add(result);
        }
    }
}
