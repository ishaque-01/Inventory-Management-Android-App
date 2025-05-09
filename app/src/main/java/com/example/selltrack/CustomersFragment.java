package com.example.selltrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.selltrack.Model.CitiesModel;
import com.example.selltrack.Model.CustomerModel;
import com.example.selltrack.adapter.CustomerAdapter;
import com.example.selltrack.data.DBHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CustomersFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<CustomerModel> list;

    private CustomerAdapter adapter;

    public CustomersFragment() {
        list = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (requireActivity() instanceof MainActivity) {
            list = ((MainActivity) requireActivity()).getCustomerList();
        } else {
            list = new ArrayList<>();
        }

        View view = inflater.inflate(R.layout.fragment_customers, container, false);
        FloatingActionButton fab = view.findViewById(R.id.addCustomer);
        fab.setOnClickListener((v) -> {
            Intent intent = new Intent(view.getContext(), AddCustomer.class);
            startActivity(intent);
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        adapter = new CustomerAdapter(getContext(), list);
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
        List<CustomerModel> updatedList = dbHandler.getAllCustomers();
        if (updatedList != null) {
            list.clear();
            list.addAll(updatedList);
            adapter.notifyDataSetChanged();
        }
    }
}