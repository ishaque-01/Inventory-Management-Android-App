package com.example.selltrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.Model.SalesModel;
import com.example.selltrack.adapter.ItemsAdapter;
import com.example.selltrack.data.DBHandler;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddSale extends AppCompatActivity {

    private DBHandler dbHandler;
    private List<String> areas;
    private List<String> customers;
    private List<ItemModel> itemList, updatedList;
    private Spinner spinnerArea, spinnerCustomer;
    private ArrayAdapter<String> adapterArea, adapterCustomers;
    private ItemsAdapter itemsAdapter;
    private Button select, makeSale;
    private TextView totalItem, totalPrice, date, time;
    private RecyclerView itemRecycler;
    private ActivityResultLauncher<Intent> launcher;


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
        customers.add(0, "Select Customer");

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


        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        updatedList = data.getParcelableArrayListExtra("selectedList");
                        updateData(updatedList);
                    }
                }
        );
        select.setOnClickListener((v) -> {
            Intent intent = new Intent(AddSale.this, SelectItems.class);
            launcher.launch(intent);
        });

        totalItem = findViewById(R.id.totalItems);
        totalPrice = findViewById(R.id.totalPrice);

        makeSale = findViewById(R.id.makeSale);
        makeSale.setOnClickListener((v) -> {
            checkInputs();
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        String currentTime = timeFormat.format(new Date());

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        date.setText("Date: " + currentDate);
        time.setText("Time: " + currentTime);

        itemRecycler = findViewById(R.id.itemsRecycler);
        itemRecycler.setLayoutManager(new LinearLayoutManager(this));
        itemRecycler.setHasFixedSize(false);
        itemList = new ArrayList<>();

        itemsAdapter = new ItemsAdapter(this, itemList);
        itemsAdapter.setOnQuantityChangeListener(() -> {
            float total = 0;
            for (ItemModel item : itemsAdapter.getItemList()) {
                total += item.getQuantity() * item.getPrice();
            }
            totalPrice.setText("Total Price: " + total);
        });

        itemRecycler.setAdapter(itemsAdapter);
    }

    private void checkInputs() {
        List<ItemModel> myList = itemsAdapter.getItemList();
        String areaSelected = spinnerArea.getSelectedItem().toString();
        String customerSelected = spinnerCustomer.getSelectedItem().toString();
        if (areaSelected.equalsIgnoreCase("select area")) {
            Toast.makeText(this, "Please Select an Area", Toast.LENGTH_SHORT).show();
            return;
        }
        if (customerSelected.equalsIgnoreCase("select customer")) {
            Toast.makeText(this, "Please Select a Customer", Toast.LENGTH_SHORT).show();
            return;
        }
        if (myList.isEmpty()) {
            Toast.makeText(this, "Select Products", Toast.LENGTH_SHORT).show();
            return;
        }
        if (validateQuantity(myList)) {
            Toast.makeText(this, "Item Quantity Invalid!", Toast.LENGTH_SHORT).show();
            return;
        }

        int customerId = Integer.parseInt(customerSelected.substring(0, customerSelected.indexOf(".")));
        int salesId = sellItem(customerId);

        for (ItemModel item : myList) {
            int productId = item.getId();
            int soldQuantity = item.getQuantity();
            dbHandler.addSalesItem(salesId, productId, soldQuantity);
        }
    }

    private int sellItem(int customerId) {
        String p = totalPrice.getText().toString();
        p = p.substring(p.indexOf(": ") + 1);
        float price = Float.parseFloat(p);
        String myDate = date.getText().toString();
        myDate = myDate.substring(myDate.indexOf(" ") + 1);
        String myTime = time.getText().toString();
        myTime = myTime.substring(myTime.indexOf(" ") + 1);
        String dateTime = myDate + " " + myTime;
        SalesModel sales = new SalesModel(customerId, price, dateTime);
        return dbHandler.addSales(sales);
    }

    private boolean validateQuantity(List<ItemModel> myList) {
        for (ItemModel item : myList) {
            int selectedQuantity = item.getQuantity();
            int availQuantity = dbHandler.getItemDetails(item).getQuantity();
            if (selectedQuantity > availQuantity)
                return true;
        }
        return false;
    }

    private void updateData(List<ItemModel> updatedList) {
        itemList.clear();
        itemList.addAll(updatedList);
        itemsAdapter.notifyDataSetChanged();
        totalItem.setText("Items: " + updatedList.size());
        float price = 0;
        for (ItemModel item : updatedList) {
            price += item.getPrice();
        }
        totalPrice.setText("Total Price: " + String.valueOf(price));
    }

    private void setCustomers(int id) {
        customers.clear();
        customers.addAll(dbHandler.getCustomers(id));
        adapterCustomers.notifyDataSetChanged();
    }
}