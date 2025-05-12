package com.example.selltrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.adapter.ItemsAdapter;
import com.example.selltrack.data.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class AddSale extends AppCompatActivity {

    private DBHandler dbHandler;
    private List<String> areas;
    private List<String> customers;
    private List<ItemModel> itemList;
    private Spinner spinnerArea, spinnerCustomer;
    private ArrayAdapter<String> adapterArea, adapterCustomers;
    private ItemsAdapter itemsAdapter;
    private Button select;
    private RecyclerView itemRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_sale);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHandler = DBHandler.getInstance(AddSale.this);
        areas = dbHandler.getAreas();
        areas.add(0, "Select Area");
        customers = dbHandler.getCustomers();

        spinnerArea = findViewById(R.id.spinnerArea);
        spinnerCustomer = findViewById(R.id.spinnerCustomer);
        select = findViewById(R.id.select);

        // Adapter For Areas Spinner
        adapterArea = new ArrayAdapter<>(AddSale.this, android.R.layout.simple_spinner_item, areas);
        adapterArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(adapterArea);

        // Adapter For Customers Spinner
        adapterCustomers = new ArrayAdapter<>(AddSale.this, android.R.layout.simple_spinner_item, customers);
        adapterCustomers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCustomer.setAdapter(adapterCustomers);

        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long rowId) {
                String item = parent.getItemAtPosition(position).toString();
                int idx = item.indexOf(".");
                if (idx > -1) {
                    int id = Integer.parseInt(item.substring(0, idx));
                    setCustomers(id);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long rowId) {
                String item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        select.setOnClickListener((v) -> {
            Intent intent = new Intent(AddSale.this, SelectItems.class);
            startActivity(intent);
        });

        itemRecycler = findViewById(R.id.itemsRecycler);
        itemRecycler.setLayoutManager(new LinearLayoutManager(this));
        itemRecycler.setHasFixedSize(false);
        itemList = new ArrayList<>();
        itemsAdapter = new ItemsAdapter(this, itemList);
        itemRecycler.setAdapter(itemsAdapter);
    }

    private void setCustomers(int id) {
        customers.clear();
        customers.addAll(dbHandler.getCustomers(id));
        adapterCustomers.notifyDataSetChanged();
    }
}