package com.upraxistest.franklindeasis;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Person> personArrayList;
    private OnItemClickListener<Person> onItemClickListener;

    public MyAdapter(ArrayList<Person> personArrayList, OnItemClickListener<Person> listener) {
        this.personArrayList = personArrayList;
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(personArrayList.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return personArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tv_name;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Person person, OnItemClickListener<Person> onItemClickListener) {
            String firstName = person.firstName;
            String lastName = person.lastName;
            tv_name.setText(itemView.getResources().getString(R.string.first_last_name, firstName, lastName));
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(person));
        }
    }
}
