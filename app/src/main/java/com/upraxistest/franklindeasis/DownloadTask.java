package com.upraxistest.franklindeasis;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by Franklin on 5/22/2018.
 */

class DownloadTask extends AsyncTask<String, Void, ArrayList<Person>> {

    private MyApi api;

    public DownloadTask(MyApi api) {
        this.api = api;
    }

    @Override
    protected ArrayList<Person> doInBackground(String... urls) {
        try {
            return api.getData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}