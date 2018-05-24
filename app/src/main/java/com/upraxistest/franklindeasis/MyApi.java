package com.upraxistest.franklindeasis;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MyApi {

    private final MyService service;

    public MyApi(Retrofit retrofit) {
        service = retrofit.create(MyService.class);
    }

    public Observable<ArrayList<Person>> getData() {
        return service.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
