package com.example.selltrack;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltrack.Model.CustomerModel;
import com.example.selltrack.Model.SalesItemModel;
import com.example.selltrack.Model.SalesModel;
import com.example.selltrack.adapter.SalesItemsAdapter;
import com.example.selltrack.data.DBHandler;

import java.util.List;

public class ShowSalesItems extends AppCompatActivity {

    private DBHandler dbHandler;
    private RecyclerView recyclerView;
    private SalesItemsAdapter adapter;
    private List<SalesItemModel> list;
    private TextView customerName, saleDateTime, saleId, totalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_sales_items);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHandler = DBHandler.getInstance(this);

        SalesModel sales = getIntent().getParcelableExtra("sales");
        CustomerModel customer = dbHandler.getCustomerDetails(sales.getCustomerId());

        customerName = findViewById(R.id.customerName);
        customerName.setText(customer.getCustomerName().toUpperCase() + " - Area ID: " + customer.getCustomerArea());

        saleDateTime = findViewById(R.id.saleDateTime);
        saleDateTime.setText(sales.getDate());

        saleId = findViewById(R.id.saleId);
        saleId.setText("Sales ID: " + sales.getSalesId());

        totalPrice = findViewById(R.id.totalPrice);
        totalPrice.setText(String.valueOf(sales.getTotalPrice()));

        list = dbHandler.getSalesItemList(sales.getSalesId());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        adapter = new SalesItemsAdapter(this, list);
        recyclerView.setAdapter(adapter);

    }
}