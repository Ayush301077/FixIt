package com.example.fixit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class EarningsFragment extends Fragment {



    public EarningsFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_earnings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<RecentEarningsModel> recentEarningsArray = new ArrayList<>();

        recentEarningsArray.add(new RecentEarningsModel("30/10/24", "Vimal Soni", "Gardening", "9574390091", "2500" ));
        recentEarningsArray.add(new RecentEarningsModel("10/08/24", "Roshni Soni", "Painting", "9408741184", "3500" ));

        RecyclerView recyclerView = view.findViewById(R.id.recentEarningsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecentEarningsAdapter adapter =  new RecentEarningsAdapter(getContext(), recentEarningsArray);
        recyclerView.setAdapter(adapter);


    }
}