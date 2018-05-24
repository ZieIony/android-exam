package com.upraxistest.franklindeasis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class MyAdapter extends BaseAdapter {

    private ArrayList<PersonClass> personClassArrayList;

    public MyAdapter(ArrayList<PersonClass> personClassArrayList) {
        this.personClassArrayList = personClassArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);

            //Set the row to display the First and Last Name of the Person
            TextView tv_name = convertView.findViewById(R.id.tv_name);
            String firstName = personClassArrayList.get(position).firstName;
            String lastName = personClassArrayList.get(position).lastName;
            tv_name.setText(parent.getResources().getString(R.string.first_last_name, firstName, lastName));

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
