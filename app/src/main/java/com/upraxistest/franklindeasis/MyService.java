package com.upraxistest.franklindeasis;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

interface MyService {
    @GET("#")
    Call<ArrayList<Person>> getData();
}
