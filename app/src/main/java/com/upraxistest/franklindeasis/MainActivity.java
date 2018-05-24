package com.upraxistest.franklindeasis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
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
    private String JSONData;

    @BindView(R.id.listview)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.i("onCreate", "onCreate");


        DownloadTask task = new DownloadTask();
        JSONData = null;

        /*
            This program assumes that all data retrieved from the URL are correctly formatted and has no invalid data
         */

        //I manually generated this JSON file from http://myjson.com/
        String url = "https://api.myjson.com/bins/142e2y";

        File cacheFile = new File(getCacheDir(), CACHE_FILE_NAME);

        if (cacheFile.exists()) {

            Log.i("MainActivity", "Load from Cache");

            try {

                JSONData = retrieveJSONDataFromCache(cacheFile);

            } catch (IOException e) {

                e.printStackTrace();

            } catch (ClassNotFoundException e) {

                e.printStackTrace();

            }

        } else {

            Log.i("MainActivity", "Load from Internet");

            try {

                ProgressDialog loadingDialog = new ProgressDialog(this);
                loadingDialog.show();
                loadingDialog.setTitle("Loading Person list");

                //Retrieve data from URL provided
                JSONData = task.execute(url).get();
                //Save JSON to Cache
                saveJSONDataToCache(JSONData, cacheFile);

                loadingDialog.dismiss();

            } catch (InterruptedException e) {

                e.printStackTrace();

            } catch (ExecutionException e) {

                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //If DownloadTask was successful, used .equals() because of String comparison
        if (!JSONData.equals("Failed")) {

            try {

                JSONArray jsonArrayPersonList = new JSONArray(JSONData);

                //Loop in JSONArray and retrieve Person Objects
                for (int x = 0; x < jsonArrayPersonList.length(); x++) {

                    JSONObject jsonPerson = jsonArrayPersonList.getJSONObject(x);
                    personArrayList.add(convertJSONObjectToPerson(jsonPerson));

                }

                final MyAdapter adapter = new MyAdapter(personArrayList);
                listView.setAdapter(adapter);

                //OnClick of item in List View would navigate the user to a new Activity containing the specific details of the Person selected
                listView.setOnItemClickListener((parent, view, position, id) -> {
                    Intent myIntent = new Intent(MainActivity.this, PersonDetailsActivity.class);
                    myIntent.putExtra(EXTRA_PERSON, personArrayList.get(position));
                    startActivity(myIntent);
                });

            } catch (JSONException e) {

                e.printStackTrace();
                Log.e("JSONException", e.toString());

            }

        } else {

            Toast.makeText(this, "Failed while loading Person List", Toast.LENGTH_SHORT).show();

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



