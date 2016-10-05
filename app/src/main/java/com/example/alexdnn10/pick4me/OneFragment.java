package com.example.alexdnn10.pick4me;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexdnn10.pick4me.R;

import java.util.ArrayList;
import java.util.List;


public class OneFragment extends Fragment {
    private List<Person> persons;
    private RecyclerView rv;
    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_one, container,
                false);

        rv=(RecyclerView)rootView.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

        return rootView;
    }
    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 min", R.drawable.user_photo, R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4));
        persons.add(new Person("Lavery Maiss", "25 min", R.drawable.user_photo, R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4));
        persons.add(new Person("Lillie Watts", "35 min", R.drawable.user_photo, R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
    }
}
