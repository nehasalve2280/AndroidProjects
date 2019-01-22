package com.example.lenovo.nehatestproject.view.activity.controller;

import android.util.Log;

import com.example.lenovo.nehatestproject.api.ApiService;
import com.example.lenovo.nehatestproject.api.ApiServiceGenerator;
import com.example.lenovo.nehatestproject.api.response.UserResponse;
import com.example.lenovo.nehatestproject.database.User;
import com.example.lenovo.nehatestproject.view.activity.UsersActivity;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmResults;

public class UsersController {
    UsersActivity mActivity;
    CompositeDisposable mDisposable=new CompositeDisposable();

    public UsersController(UsersActivity mActivity) {
        this.mActivity = mActivity;
    }
    public void getdata() {
        /*Create handle for the RetrofitInstance interface*/

        ApiService service = ApiServiceGenerator.getRetrofitInstance().create(ApiService.class);
        try {

            mDisposable.add(service.getUserData().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<List<UserResponse>>() {
                        @Override
                        public void onSuccess(List<UserResponse> response) {
                            try {
                                mActivity.progressDoalog.dismiss();

                                generateDataList(response);

                                //apiCallback.onSuccess(celebrationListDto);
                                //TSLog.d(TAG, "getOrders onSuccess", "getOrders Successfully");
                            } catch (Exception e) {
                                mActivity.progressDoalog.dismiss();
                                Log.e("ERROR",  e.toString());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                                mActivity.progressDoalog.dismiss();
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
            mActivity.progressDoalog.dismiss();
            Log.e("ERROR3",  e.toString());
        }

    }

    private void generateDataList(List<UserResponse> body) {
        for (UserResponse response:body) {
            mActivity.realm.beginTransaction();
            User user =  mActivity.realm.createObject(User.class);
            user.setName(response.getName());
            user.setEmail(response.getEmail());
            user.setId(response.getId());
            user.setAddress(response.getAddress().getStreet()+","+response.getAddress().getCity());
            mActivity.realm.commitTransaction();
        }
        showData();

    }

    public void showData() {
        RealmResults<User> results =  mActivity.realm.where(User.class).findAll();
        mActivity.mAdapter.addAll(results);
        mActivity.mAdapter.notifyDataSetChanged();
    }
}
