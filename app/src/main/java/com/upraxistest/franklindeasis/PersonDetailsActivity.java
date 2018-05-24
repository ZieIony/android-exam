package com.upraxistest.franklindeasis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Franklin on 5/22/2018.
 */

public class PersonDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tv_firstname)
    TextView tv_firstName;

    @BindView(R.id.tv_lastname)
    TextView tv_lastName;

    @BindView(R.id.tv_birthdate)
    TextView tv_birthDate;

    @BindView(R.id.tv_age)
    TextView tv_age;

    @BindView(R.id.tv_emailaddress)
    TextView tv_emailAddress;

    @BindView(R.id.tv_mobilenumber)
    TextView tv_mobileNumber;

    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_contactperson)
    TextView tv_contactPerson;

    @BindView(R.id.tv_contactpersonmobilenumber)
    TextView tv_contactPersonMobileNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_person_details);
        ButterKnife.bind(this);

        //Get Person Data from Source Activity
        Bundle data = getIntent().getExtras();
        PersonClass person = data.getParcelable("person");

        //For simplicity I used the same TextView and just appended the value
        tv_firstName.setText(tv_firstName.getText() + person.getFirstName());
        tv_lastName.setText(tv_lastName.getText() + person.getLastName());
        tv_age.setText(tv_age.getText() + String.valueOf(person.getAge()));
        tv_birthDate.setText(tv_birthDate.getText() + person.getBirthDate());
        tv_emailAddress.setText(tv_emailAddress.getText() + person.getEmailAddress());
        tv_mobileNumber.setText(tv_mobileNumber.getText() + person.getMobileNumber());
        tv_address.setText(tv_address.getText() + person.getAddress());
        tv_contactPerson.setText(tv_contactPerson.getText() + person.getContactPerson());
        tv_contactPersonMobileNumber.setText(tv_contactPersonMobileNumber.getText() + person.getContactPersonPhoneNumber());
    }
}
