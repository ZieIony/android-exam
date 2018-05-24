package com.upraxistest.franklindeasis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final static String FAILED = "FAILED";
    private final static String CACHE_FILE_NAME = "JSONData";
    public static final String EXTRA_PERSON = "person";

    File cacheFile;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    DownloadTask task;

    MyApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.i("onCreate", "onCreate");

        api = new MyApi();
        task = new DownloadTask(api);

        cacheFile = new File(getCacheDir(), CACHE_FILE_NAME);

        swipeRefreshLayout.setOnRefreshListener(this::loadData);

        initData();
    }

    private void initData() {
        if (cacheFile.exists()) {

            Log.i("MainActivity", "Load from Cache");

            try {

                ArrayList<Person> list = retrieveDataFromCache(cacheFile);
                setupData(list);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {

            loadData();
        }
    }

    private void loadData() {
        Log.i("MainActivity", "Load from Internet");

        try {

            swipeRefreshLayout.setRefreshing(true);

            //Retrieve data from URL provided
            ArrayList<Person> personArrayList = task.execute().get();

            swipeRefreshLayout.setRefreshing(false);

            if (personArrayList !=null) {
                //Save JSON to Cache
                saveDataToCache(personArrayList, cacheFile);
                setupData(personArrayList);
            } else {
                Toast.makeText(this, "Failed while loading Person List", Toast.LENGTH_SHORT).show();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupData(ArrayList<Person> personArrayList) {
        final MyAdapter adapter = new MyAdapter(personArrayList, (person) -> {
            Intent myIntent = new Intent(MainActivity.this, PersonDetailsActivity.class);
            myIntent.putExtra(EXTRA_PERSON, person);
            startActivity(myIntent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void saveDataToCache(ArrayList<Person> list, File file) throws IOException {
        FileOutputStream fileOS = new FileOutputStream(file);
        ObjectOutput out = new ObjectOutputStream(fileOS);
        out.writeObject(list);
        out.close();
    }

    private ArrayList<Person> retrieveDataFromCache(File file) throws IOException, ClassNotFoundException {
        FileInputStream fileIS = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIS);
        ArrayList<Person> list = (ArrayList<Person>) in.readObject();
        in.close();
        return list;
    }

}



