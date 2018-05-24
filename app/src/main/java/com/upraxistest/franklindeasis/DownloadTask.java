package com.upraxistest.franklindeasis;

import android.os.AsyncTask;

/**
 * Created by Franklin on 5/22/2018.
 */

class DownloadTask extends AsyncTask<String, Void, String> {

    private MyApi api;

    public DownloadTask(MyApi api) {
        this.api = api;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return api.getData();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }
}