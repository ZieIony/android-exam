package com.upraxistest.franklindeasis;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private final static String FAILED = "FAILED";
    private final static String CACHE_FILE_NAME = "JSONData";
    private ArrayList<PersonClass> personClassArrayList = new ArrayList<>();
    private ListView listView;
    private String JSONData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("onCreate", "onCreate");

        listView = (ListView) findViewById(R.id.listview);

        DownloadTask task = new DownloadTask();
        JSONData = null;

        /*
            This program assumes that all data retrieved from the URL are correctly formatted and has no invalid data
         */

        //I manually generated this JSON file from http://myjson.com/
        String url = "https://api.myjson.com/bins/142e2y";

        File cacheFile = new File(getCacheDir(), CACHE_FILE_NAME);

        if (cacheFile.exists()){

            Log.i("MainActivity", "Load from Cache");

            try {

                JSONData = retreiveJSONDatafromCache(cacheFile);

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
                saveJSONDatatoCache(JSONData, cacheFile);

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
        if(!JSONData.equals("Failed")){

            try {

                JSONArray jsonArrayPersonList = new JSONArray(JSONData);

                //Loop in JSONArray and retrieve Person Objects
                for(int x = 0; x < jsonArrayPersonList.length(); x++){

                    JSONObject jsonPerson = jsonArrayPersonList.getJSONObject(x);
                    personClassArrayList.add(convertJSONObjectToPerson(jsonPerson));

                }

                final MyAdapter adapter = new MyAdapter(this, personClassArrayList);
                listView.setAdapter(adapter);

                //OnClick of item in List View would navigate the user to a new Activity containing the specific details of the Person selected
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent myIntent = new Intent(MainActivity.this, PersonDetailsActivity.class);
                        myIntent.putExtra("person", personClassArrayList.get(position));
                        startActivity(myIntent);
                    }
                });

            } catch (JSONException e) {

                e.printStackTrace();
                Log.e("JSONException", e.toString());

            }

        } else {

            Toast.makeText(this, "Failed while loading Person List", Toast.LENGTH_SHORT).show();

        }
    }

    private void saveJSONDatatoCache(String JSONData, File file) throws IOException {
        FileOutputStream fileOS = new FileOutputStream(file);
        ObjectOutput out = new ObjectOutputStream(fileOS);
        out.writeObject(JSONData);
        out.close();
    }

    private String retreiveJSONDatafromCache(File file) throws IOException, ClassNotFoundException {
        FileInputStream fileIS = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIS);
        String JSONData = (String) in.readObject();
        in.close();
        return JSONData;
    }

    //Converting JSONObject to PersonClass
    private PersonClass convertJSONObjectToPerson(JSONObject jsonPerson) throws JSONException {

        String firstName = jsonPerson.getString("firstname");
        String lastName = jsonPerson.getString("lastname");
        String birthdate = jsonPerson.getString("birthdate");
        String emailAddress = jsonPerson.getString("emailadd");
        String mobileNumber = jsonPerson.getString("mobilenumber");
        String address = jsonPerson.getString("address");
        String contactPerson = jsonPerson.getString("contactperson");
        String contectPersonMobileNumber = jsonPerson.getString("contactpersonmobilenumber");

        PersonClass person = new PersonClass(firstName,
                lastName,
                birthdate,
                emailAddress,
                mobileNumber,
                address,
                contactPerson,
                contectPersonMobileNumber);

        return person;

    }

    private class MyAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<PersonClass> personClassArrayList;

        public MyAdapter(Context mContext, ArrayList<PersonClass> personClassArrayList) {
            this.mContext = mContext;
            this.personClassArrayList = personClassArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                convertView = getLayoutInflater().inflate(R.layout.item_person, parent, false);

                //Set the row to display the First and Last Name of the Person
                TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                String firstName = personClassArrayList.get(position).getFirstName();
                String lastName = personClassArrayList.get(position).getLastName();
                tv_name.setText(firstName + " " + lastName);

            }
            return convertView;

        }

        @Override
        public int getCount() {
            return personClassArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return personClassArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }


}



