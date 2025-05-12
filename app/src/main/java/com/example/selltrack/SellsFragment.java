package com.example.selltrack;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SellsFragment extends Fragment {
    public SellsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sells, container, false);

        FloatingActionButton addInventory = view.findViewById(R.id.addSale);
        addInventory.setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), AddSale.class);
            startActivity(intent);
        });


        return view;
    }
}