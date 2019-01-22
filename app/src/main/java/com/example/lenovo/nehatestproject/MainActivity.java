package com.example.lenovo.nehatestproject;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvUsers;
    RecyclerView.LayoutManager layoutManager;
    UsersAdapter mAdapter;
    private Realm realm;
    ProgressDialog progressDoalog;
    CompositeDisposable mDisposable=new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDoalog = new ProgressDialog(MainActivity.this);

        progressDoalog.setMessage("Loading....");

        progressDoalog.show();
        rvUsers = findViewById(R.id.rv_users);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvUsers.setLayoutManager(layoutManager);
        mAdapter = new UsersAdapter(this);
        rvUsers.setAdapter(mAdapter);
        this.realm = RealmController.with(this).getRealm();
        getdata();

    }

    private void getdata() {
        /*Create handle for the RetrofitInstance interface*/

        ApiService service = ApiServiceGenerator.getRetrofitInstance().create(ApiService.class);
        try {

            mDisposable.add(service.getUserData().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<List<UserResponse>>() {
                        @Override
                        public void onSuccess(List<UserResponse> response) {
                            try {
                                progressDoalog.dismiss();

                                generateDataList(response);

                                //apiCallback.onSuccess(celebrationListDto);
                                //TSLog.d(TAG, "getOrders onSuccess", "getOrders Successfully");
                            } catch (Exception e) {
                                progressDoalog.dismiss();
                                Log.e("ERROR",  e.toString());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                                progressDoalog.dismiss();
                                Log.e("ERROR2",  e.getMessage());
                                showData();
                              /*  ApiResponse apiResponse = ErrorUtils.parseThrowable(e);
                                apiCallback.onError(apiResponse);*/

                            } catch (Exception ex) {
                                Log.e("ERROR2",  ex.getMessage());
                            }
                        }
                    }));

        } catch (Exception e) {
            progressDoalog.dismiss();
            Log.e("ERROR3",  e.toString());
        }

    }

    private void generateDataList(List<UserResponse> body) {
        for (UserResponse response:body) {
            realm.beginTransaction();
            User user = realm.createObject(User.class);
            user.setName(response.getName());
            user.setEmail(response.getEmail());
            user.setId(response.getId());
            realm.commitTransaction();
        }
        showData();

    }

    private void showData() {
        RealmResults<User> results = realm.where(User.class).findAll();
        mAdapter.addAll(results);
        mAdapter.notifyDataSetChanged();
    }
}
