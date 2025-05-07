package com.example.selltrack;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selltrack.Model.CitiesModel;
import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.adapter.CitiesAdapter;
import com.example.selltrack.data.DBHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CitiesFragment extends Fragment {

    private List<CitiesModel> list;
    private RecyclerView recyclerView;
    private CitiesAdapter adapter;

    public CitiesFragment() {
        list = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (requireActivity() instanceof MainActivity) {
            list = ((MainActivity) requireActivity()).getCitiesList();
        } else {
            list = new ArrayList<>();
        }

        View view = inflater.inflate(R.layout.fragment_cities, container, false);
        FloatingActionButton fab = view.findViewById(R.id.addCities);
        fab.setOnClickListener((v) -> {
            Intent intent = new Intent(view.getContext(), AddCity.class);
            startActivity(intent);
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        adapter = new CitiesAdapter(requireContext(), list);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        DBHandler dbHandler = DBHandler.getInstance(getContext());
        List<CitiesModel> updatedList = dbHandler.getAllCities();
        if (updatedList != null) {
            list.clear();
            list.addAll(updatedList);
            adapter.notifyDataSetChanged();
        }
    }
}