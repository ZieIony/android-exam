package com.upraxistest.franklindeasis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Franklin on 5/22/2018.
 */

public class PersonDetailsActivity  extends AppCompatActivity{

    TextView tv_firstname, tv_lastname, tv_birthdate, tv_age, tv_emailaddress, tv_mobilenumber, tv_address, tv_contactperson, tv_contactpersonmobilenumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_person_details);

        tv_firstname = (TextView) findViewById(R.id.tv_firstname);
        tv_lastname = (TextView) findViewById(R.id.tv_lastname);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_birthdate= (TextView) findViewById(R.id.tv_birthdate);
        tv_emailaddress = (TextView) findViewById(R.id.tv_emailaddress);
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_mobilenumber= (TextView) findViewById(R.id.tv_mobilenumber);
        tv_contactperson = (TextView) findViewById(R.id.tv_contactperson);
        tv_contactpersonmobilenumber= (TextView) findViewById(R.id.tv_contactpersonmobilenumber);

        Bundle data = getIntent().getExtras();
        PersonClass person = (PersonClass) data.getParcelable("person");

        //For simplicity I used the same TextView and just appended the value
        tv_firstname.setText(tv_firstname.getText() + person.getFirstName());
        tv_lastname.setText(tv_lastname.getText() + person.getLastName());
        tv_age.setText(tv_age.getText() + String.valueOf(person.getAge()));
        tv_birthdate.setText(tv_birthdate.getText() + person.getBirthdate());
        tv_emailaddress.setText(tv_emailaddress.getText() + person.getEmailAddress());
        tv_mobilenumber.setText(tv_mobilenumber.getText() + person.getMobileNumber());
        tv_address.setText(tv_address.getText() + person.getEmailAddress());
        tv_contactperson.setText(tv_contactperson.getText() + person.getContactPerson());
        tv_contactpersonmobilenumber.setText(tv_contactpersonmobilenumber.getText() + person.getContactPersonPhoneNumber());

    }

    public void backPress(View v){
        this.onBackPressed();
    }


}
