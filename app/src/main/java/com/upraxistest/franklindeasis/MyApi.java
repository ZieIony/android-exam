package com.upraxistest.franklindeasis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

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
                .build();

        service = retrofit.create(MyService.class);
    }

    public ArrayList<Person> getData() throws IOException {
        return service.getData().execute().body();
    }
}
