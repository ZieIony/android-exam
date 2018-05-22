package com.upraxistest.franklindeasis;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Franklin on 5/22/2018.
 */

public class PersonClass implements Parcelable{
    private String firstName;
    private String lastName;
    private String birthdate;
    private int age;
    private String emailAddress;
    private String mobileNumber;
    private String address;
    private String contactPerson;
    private String contactPersonPhoneNumber;

    public PersonClass(String firstName, String lastName, String birthday, String emailAddress, String mobileNumber, String address, String contactPerson, String contactPersonPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthday;
        this.emailAddress = emailAddress;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.contactPerson = contactPerson;
        this.contactPersonPhoneNumber = contactPersonPhoneNumber;
    }


    protected PersonClass(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        birthdate = in.readString();
        age = in.readInt();
        emailAddress = in.readString();
        mobileNumber = in.readString();
        address = in.readString();
        contactPerson = in.readString();
        contactPersonPhoneNumber = in.readString();
    }

    public static final Creator<PersonClass> CREATOR = new Creator<PersonClass>() {
        @Override
        public PersonClass createFromParcel(Parcel in) {
            return new PersonClass(in);
        }

        @Override
        public PersonClass[] newArray(int size) {
            return new PersonClass[size];
        }
    };

    public int getAge(){
        String birthdate = this.birthdate;
        String[] birthdateStringArray = birthdate.split("-");
        int month = Integer.parseInt(birthdateStringArray[0]);
        int day = Integer.parseInt(birthdateStringArray[1]);
        int year = Integer.parseInt(birthdateStringArray[2]);

        Calendar now = Calendar.getInstance();
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.set(Calendar.YEAR, year);
        //CALENDAR.JANUARY = 0 and CALENDAR.DECEMBER = 11, so deduct 1
        dateOfBirth.set(Calendar.MONTH, month-1);
        dateOfBirth.set(Calendar.DAY_OF_MONTH, day);

        if (dateOfBirth.after(now)) {
            throw new IllegalArgumentException("Can't be born in the future");
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(birthdate);
        dest.writeInt(age);
        dest.writeString(emailAddress);
        dest.writeString(mobileNumber);
        dest.writeString(address);
        dest.writeString(contactPerson);
        dest.writeString(contactPersonPhoneNumber);
    }

    @Override
    public String toString() {
        return firstName + " "
                + lastName + " "
                + birthdate + " "
                + emailAddress + " "
                + mobileNumber + " "
                + address + " "
                + contactPerson + " "
                + contactPersonPhoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getContactPersonPhoneNumber() {
        return contactPersonPhoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setContactPersonPhoneNumber(String contactPersonPhoneNumber) {
        this.contactPersonPhoneNumber = contactPersonPhoneNumber;
    }


}
