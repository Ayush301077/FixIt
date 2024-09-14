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
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {

    ArrayList<ChatModel> chatArray = new ArrayList<>();
    SearchView searchView;
    ChatAdapter adapter;
    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<ChatModel> filteredList = new ArrayList<>();
                for(ChatModel item : chatArray)
                {
                    if(item.getName().toLowerCase().contains(s.toLowerCase()))
                    {
                        filteredList.add(item);
                    }
                }

                if(filteredList.isEmpty())
                    Toast.makeText(getContext(), "No such data found", Toast.LENGTH_SHORT).show();
                else
                    adapter.setFilteredList(filteredList);

                return true;
            }
        });

        chatArray.add(new ChatModel("Vimal Soni", "How are you?", "5:05 PM"));
        chatArray.add(new ChatModel("Roshni Soni", "I am fine", "Yesterday"));
        chatArray.add(new ChatModel("Ayush Soni", "Hello", "09/04/05"));

        RecyclerView recyclerView = view.findViewById(R.id.chatsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

         adapter = new ChatAdapter( chatArray, getContext());
        recyclerView.setAdapter(adapter);
    }
}