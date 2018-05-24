package com.upraxistest.franklindeasis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private ArrayList<Person> personArrayList = new ArrayList<>();
    private String JSONData = null;
    File cacheFile;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    DownloadTask task = new DownloadTask();

    //I manually generated this JSON file from http://myjson.com/
    String url = "https://api.myjson.com/bins/142e2y";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.i("onCreate", "onCreate");

        cacheFile = new File(getCacheDir(), CACHE_FILE_NAME);

        swipeRefreshLayout.setOnRefreshListener(this::loadData);

        initData();
    }

    private void initData() {
        if (cacheFile.exists()) {

            Log.i("MainActivity", "Load from Cache");

            try {

                JSONData = retrieveJSONDataFromCache(cacheFile);
                setupData();

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
            JSONData = task.execute(url).get();
            //Save JSON to Cache
            saveJSONDataToCache(JSONData, cacheFile);

            swipeRefreshLayout.setRefreshing(false);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //If DownloadTask was successful, used .equals() because of String comparison
        if (!JSONData.equals("Failed")) {
            setupData();
        } else {
            Toast.makeText(this, "Failed while loading Person List", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupData() {
        try {

            JSONArray jsonArrayPersonList = new JSONArray(JSONData);

            //Loop in JSONArray and retrieve Person Objects
            for (int x = 0; x < jsonArrayPersonList.length(); x++) {

                JSONObject jsonPerson = jsonArrayPersonList.getJSONObject(x);
                personArrayList.add(convertJSONObjectToPerson(jsonPerson));

            }

            final MyAdapter adapter = new MyAdapter(personArrayList, (person) -> {
                Intent myIntent = new Intent(MainActivity.this, PersonDetailsActivity.class);
                myIntent.putExtra(EXTRA_PERSON, person);
                startActivity(myIntent);
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        } catch (JSONException e) {

            e.printStackTrace();
            Log.e("JSONException", e.toString());

        }
    }

    private void saveJSONDataToCache(String JSONData, File file) throws IOException {
        FileOutputStream fileOS = new FileOutputStream(file);
        ObjectOutput out = new ObjectOutputStream(fileOS);
        out.writeObject(JSONData);
        out.close();
    }

    private String retrieveJSONDataFromCache(File file) throws IOException, ClassNotFoundException {
        FileInputStream fileIS = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIS);
        String JSONData = (String) in.readObject();
        in.close();
        return JSONData;
    }

    //Converting JSONObject to Person
    private Person convertJSONObjectToPerson(JSONObject jsonPerson) throws JSONException {

        String firstName = jsonPerson.getString("firstname");
        String lastName = jsonPerson.getString("lastname");
        String birthDate = jsonPerson.getString("birthdate");
        String emailAddress = jsonPerson.getString("emailadd");
        String mobileNumber = jsonPerson.getString("mobilenumber");
        String address = jsonPerson.getString("address");
        String contactPerson = jsonPerson.getString("contactperson");
        String contactPersonMobileNumber = jsonPerson.getString("contactpersonmobilenumber");

        return new Person(firstName,
                lastName,
                birthDate,
                emailAddress,
                mobileNumber,
                address,
                contactPerson,
                contactPersonMobileNumber);
    }

}



