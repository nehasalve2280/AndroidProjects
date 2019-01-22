package com.example.lenovo.nehatestproject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService
{
    @GET("/users")
    Single<List<UserResponse>> getUserData();

}
