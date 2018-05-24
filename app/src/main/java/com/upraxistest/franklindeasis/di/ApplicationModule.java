package com.upraxistest.franklindeasis.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.upraxistest.franklindeasis.MyApi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private static final String APIURL_DEPENDENCY = "ApiUrl";

    @Provides
    @Singleton
    @Named(APIURL_DEPENDENCY)
    String provideApiUrl(){
        return "https://api.myjson.com/bins/142e2y/";
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingStrategy(field -> field.getName().toLowerCase())
                .create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, @Named(APIURL_DEPENDENCY) String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    @Provides
    @Singleton
    MyApi provideApi(Retrofit retrofit) {
        return new MyApi(retrofit);
    }
}
