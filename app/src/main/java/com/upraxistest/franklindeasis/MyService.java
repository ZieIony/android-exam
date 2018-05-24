package com.upraxistest.franklindeasis;

import retrofit2.Call;
import retrofit2.http.GET;

interface MyService {
    @GET("")
    Call<String> getData();
}
