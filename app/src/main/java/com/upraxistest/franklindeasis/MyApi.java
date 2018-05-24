package com.upraxistest.franklindeasis;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApi {

    //I manually generated this JSON file from http://myjson.com/
    private static final String url = "https://api.myjson.com/bins/142e2y/";

    private final MyService service;

    public MyApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(MyService.class);
    }

    public String getData() throws IOException {
        return service.getData().execute().body();
    }
}
