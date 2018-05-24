package com.upraxistest.franklindeasis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApi {

    //I manually generated this JSON file from http://myjson.com/
    private static final String url = "https://api.myjson.com/bins/142e2y/";

    private final MyService service;

    public MyApi() {
        Gson gson = new GsonBuilder()
                .setFieldNamingStrategy(field -> field.getName().toLowerCase())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(MyService.class);
    }

    public Observable<ArrayList<Person>> getData() {
        return service.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
