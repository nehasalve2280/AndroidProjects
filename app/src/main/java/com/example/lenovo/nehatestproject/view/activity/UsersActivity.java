package com.example.lenovo.nehatestproject.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lenovo.nehatestproject.R;
import com.example.lenovo.nehatestproject.database.RealmController;
import com.example.lenovo.nehatestproject.database.User;
import com.example.lenovo.nehatestproject.view.activity.adapter.UsersAdapter;
import com.example.lenovo.nehatestproject.api.ApiService;
import com.example.lenovo.nehatestproject.api.ApiServiceGenerator;
import com.example.lenovo.nehatestproject.api.response.UserResponse;
import com.example.lenovo.nehatestproject.view.activity.controller.UsersController;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class UsersActivity extends AppCompatActivity {
    RecyclerView rvUsers;
    RecyclerView.LayoutManager layoutManager;
    public UsersAdapter mAdapter;
    public Realm realm;
    public ProgressDialog progressDoalog;
    UsersController mController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            initializeToolbar();


            rvUsers = findViewById(R.id.rv_users);
            progressDoalog = new ProgressDialog(UsersActivity.this);

            progressDoalog.setMessage("Retriving user information..");
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rvUsers.setLayoutManager(layoutManager);
            mAdapter = new UsersAdapter(this);
            rvUsers.setAdapter(mAdapter);
            mController = new UsersController(this);
            this.realm = RealmController.with(this).getRealm();
            int i = RealmController.with(this).getCount();
            if (i < 1) {
                if (connectionAvailable()) {


                    progressDoalog.show();
                    mController.getdata();
                }
                else
                    Toast.makeText(this, "Internet not available", Toast.LENGTH_SHORT).show();
            } else {
                progressDoalog.dismiss();
                mController.showData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initializeToolbar() throws Exception {
        ActionBar ab = getSupportActionBar();
        getSupportActionBar().setTitle(R.string.users);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean connectionAvailable() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        return connected;
    }


}
