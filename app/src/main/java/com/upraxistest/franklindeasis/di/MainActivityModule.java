package com.upraxistest.franklindeasis.di;

import com.upraxistest.franklindeasis.MainActivity;
import com.upraxistest.franklindeasis.MyApi;
import com.upraxistest.franklindeasis.mvp.MainContract;
import com.upraxistest.franklindeasis.mvp.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    MainContract.MainView provideMainView(MainActivity mainActivity){
        return mainActivity;
    }

    @Provides
    MainContract.MainPresenter provideMainPresenter(MainContract.MainView mainView, MyApi api){
        return new MainPresenterImpl(mainView, api);
    }
}