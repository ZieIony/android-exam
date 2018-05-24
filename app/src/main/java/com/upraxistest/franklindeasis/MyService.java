package com.upraxistest.franklindeasis;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

interface MyService {
    @GET("#")
    Observable<ArrayList<Person>> getData();
}
