package com.example.alexdnn10.pick4me;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView userName;
        TextView userTime;
        ImageView userPhoto;
        ImageView userPhoto_1;
        ImageView userPhoto_2;
        ImageView userPhoto_3;
        ImageView userPhoto_4;


        PersonViewHolder(View itemView) {
            super(itemView);
            userName = (TextView)itemView.findViewById(R.id.newsfeed_username);
            userTime = (TextView)itemView.findViewById(R.id.newsfeed_time);
            userPhoto = (ImageView)itemView.findViewById(R.id.newsfeed_user_photo);
            userPhoto_1 = (ImageView)itemView.findViewById(R.id.image_one);
            userPhoto_2 = (ImageView)itemView.findViewById(R.id.image_two);
            userPhoto_3 = (ImageView)itemView.findViewById(R.id.image_three);
            userPhoto_4 = (ImageView)itemView.findViewById(R.id.image_four);
        }
    }
    List<Person> persons;

    RVAdapter(List<Person> persons){
        this.persons = persons;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.userName.setText(persons.get(i).username);
        personViewHolder.userTime.setText(persons.get(i).time);
        personViewHolder.userPhoto.setImageResource(persons.get(i).userphoto);
        personViewHolder.userPhoto_1.setImageResource(persons.get(i).photoId_1);
        personViewHolder.userPhoto_2.setImageResource(persons.get(i).photoId_2);
        personViewHolder.userPhoto_3.setImageResource(persons.get(i).photoId_3);
        personViewHolder.userPhoto_4.setImageResource(persons.get(i).photoId_4);
    }
    @Override
    public int getItemCount() {
        return persons.size();
    }
}