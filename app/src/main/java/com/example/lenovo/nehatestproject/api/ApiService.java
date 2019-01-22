package com.example.lenovo.nehatestproject.api;

import com.example.lenovo.nehatestproject.api.response.UserResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService
{
    @GET("/users")
    Single<List<UserResponse>> getUserData();

}
