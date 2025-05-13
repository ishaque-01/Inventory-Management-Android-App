package com.example.selltrack;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.Model.SalesModel;
import com.example.selltrack.adapter.InventoryAdaptor;
import com.example.selltrack.adapter.SalesAdapter;
import com.example.selltrack.data.DBHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SellsFragment extends Fragment {

    private List<SalesModel> list;
    private RecyclerView recyclerView;
    private SalesAdapter adapter;

    public SellsFragment() {list = new ArrayList<>();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (requireActivity() instanceof MainActivity) {
            list = ((MainActivity) requireActivity()).getSalesList();
        } else {
            list = new ArrayList<>();
        }

        View view = inflater.inflate(R.layout.fragment_sells, container, false);

        FloatingActionButton addInventory = view.findViewById(R.id.addSale);
        addInventory.setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), AddSale.class);
            startActivity(intent);
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        adapter = new SalesAdapter(requireContext(), list);
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
        List<SalesModel> updatedList = dbHandler.getSalesList();
        if (updatedList != null) {
            list.clear();
            list.addAll(updatedList);
            adapter.notifyDataSetChanged();
        }
    }
}