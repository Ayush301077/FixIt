package com.example.fixit;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private SwitchCompat statusSwitch;
    private TextView statusText;
    TextView newRequestsDropdown, acceptedRequestsDropdown;
    ArrayList<RequestModel> requestArray = new ArrayList<>();
    ArrayList<RequestModel> acceptedArray = new ArrayList<>();

    TextView textview;
    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        statusSwitch = view.findViewById(R.id.statusSwitch);
        statusText = view.findViewById(R.id.statusText);
        newRequestsDropdown = view.findViewById(R.id.newRequestsDropdown);
        acceptedRequestsDropdown = view.findViewById(R.id.acceptedRequestsDropdown);

        // Set an OnCheckedChangeListener to toggle between states
        statusSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Switch is ON, display "Available" with green colors
                statusText.setText("Available");
                statusSwitch.setThumbTintList(ColorStateList.valueOf(Color.GREEN));
                statusSwitch.setTrackTintList(ColorStateList.valueOf(Color.parseColor("#A5D6A7"))); // Light green
            } else {
                // Switch is OFF, display "Unavailable" with red colors
                statusText.setText("Unavailable");
                statusSwitch.setThumbTintList(ColorStateList.valueOf(Color.RED));
                statusSwitch.setTrackTintList(ColorStateList.valueOf(Color.parseColor("#EF9A9A"))); // Light red
            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        requestArray.add(new RequestModel("Soni Ayush V", "6353805504", "College road", "Nadiad", "Plumbing"));
        requestArray.add(new RequestModel("Soni Vimal N", "9574390091", "Kansa", "Visnagar", "Plumbing"));
        requestArray.add(new RequestModel("Soni Ayush V", "6353805504", "College road", "Nadiad", "Plumbing"));

        NewRequestsAdapter adapter = new NewRequestsAdapter(getContext(), requestArray);
        recyclerView.setAdapter(adapter);


        newRequestsDropdown.setOnClickListener(new View.OnClickListener() {
            boolean isVisible = true;
            @Override
            public void onClick(View view) {
                if(isVisible){
                    newRequestsDropdown.setText("New work requests                                                            ▼");
                    newRequestsDropdown.setBackgroundResource(R.drawable.roundcorners);
                    recyclerView.setVisibility(view.GONE);}
                else
                {
                    newRequestsDropdown.setText("New work requests                                                            ▲");
                    newRequestsDropdown.setBackgroundResource(R.drawable.roundcorners_onclick);
                    recyclerView.setVisibility(view.VISIBLE);}
                isVisible = !isVisible;
            }
        });



        RecyclerView recyclerView2 = view.findViewById(R.id.recyclerview2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        acceptedArray.add(new RequestModel("Soni Ayush V", "6353805504", "College road", "Nadiad", "Plumbing"));
        acceptedArray.add(new RequestModel("Soni Vimal N", "9574390091", "Kansa", "Visnagar", "Plumbing"));

        AcceptedRequestsAdapter adapter2 = new AcceptedRequestsAdapter(getContext(), acceptedArray);
        recyclerView2.setAdapter(adapter2);

        boolean isVisible2 = true;
        acceptedRequestsDropdown.setOnClickListener(new View.OnClickListener() {
            boolean isVisible2 = true;
            @Override
            public void onClick(View view) {

                if(isVisible2){
                    acceptedRequestsDropdown.setText("Accepted requests                                                            ▼");
                    acceptedRequestsDropdown.setBackgroundResource(R.drawable.roundcorners);
                    recyclerView2.setVisibility(view.GONE);
                }
                else
                {
                    acceptedRequestsDropdown.setText("Accepted requests                                                            ▲");
                    acceptedRequestsDropdown.setBackgroundResource(R.drawable.roundcorners_onclick);
                    recyclerView2.setVisibility(view.VISIBLE);}
                isVisible2 = !isVisible2;
            }
        });
    }
}