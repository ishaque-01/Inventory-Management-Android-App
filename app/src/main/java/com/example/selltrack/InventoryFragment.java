package com.example.selltrack;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.adapter.InventoryAdaptor;
import com.example.selltrack.data.DBHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class InventoryFragment extends Fragment {

    RecyclerView recyclerView;
    List<ItemModel> itemList;
    InventoryAdaptor recyclerViewAdapter;

    public InventoryFragment() {
        itemList = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (requireActivity() instanceof MainActivity) {
            itemList = ((MainActivity) requireActivity()).getItemsList();
        } else {
            itemList = new ArrayList<>();
        }

        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        FloatingActionButton addInventory = view.findViewById(R.id.addItems);
        addInventory.setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), AddItemsInventory.class);
            startActivity(intent);
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerViewAdapter = new InventoryAdaptor(requireContext(), itemList);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        DBHandler dbHandler = DBHandler.getInstance(getContext());
        List<ItemModel> updatedList = dbHandler.getInventoryItems();
        if (updatedList != null) {
            itemList.clear();
            itemList.addAll(updatedList);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

}