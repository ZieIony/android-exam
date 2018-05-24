package com.upraxistest.franklindeasis;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Franklin on 5/22/2018.
 */

public class Person implements Serializable {
    public String firstName;
    public String lastName;
    public String birthDate;
    public int age;
    public String emailAddress;
    public String mobileNumber;
    public String address;
    public String contactPerson;
    public String contactPersonMobileNumber;

    public Person(String firstName, String lastName, String birthday, String emailAddress, String mobileNumber, String address, String contactPerson, String contactPersonMobileNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthday;
        this.emailAddress = emailAddress;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.contactPerson = contactPerson;
        this.contactPersonMobileNumber = contactPersonMobileNumber;
        age = initAge();
    }

    private int initAge() {
        Calendar now = Calendar.getInstance();
        Calendar dateOfBirth = Calendar.getInstance();

        try {
            Date date = new SimpleDateFormat("MM-dd-yyyy").parse(birthDate);
            dateOfBirth.setTime(date);
        } catch (ParseException e) {
            return -1;
        }

        if (dateOfBirth.after(now)) {
            return -1;
        }
        int year1 = now.get(Calendar.YEAR);
        int year2 = dateOfBirth.get(Calendar.YEAR);
        int age = year1 - year2;
        int month1 = now.get(Calendar.MONTH);
        int month2 = dateOfBirth.get(Calendar.MONTH);
        if (month2 > month1) {
            age--;
        } else if (month1 == month2) {
            int day1 = now.get(Calendar.DAY_OF_MONTH);
            int day2 = dateOfBirth.get(Calendar.DAY_OF_MONTH);
            if (day2 > day1) {
                age--;
            }
        }

        return age;
    }
}
