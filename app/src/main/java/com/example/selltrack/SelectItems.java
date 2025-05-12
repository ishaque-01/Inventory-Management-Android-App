package com.example.selltrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.adapter.SelectAdapter;
import com.example.selltrack.data.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class SelectItems extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SelectAdapter adapter;
    private Button finalize, cancel;
    private List<ItemModel> selectedItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_items);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DBHandler dbHandler = DBHandler.getInstance(SelectItems.this);
        List<ItemModel> list = dbHandler.getInventoryItems();

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new SelectAdapter(SelectItems.this, list);

        finalize = findViewById(R.id.finalize);
        finalize.setOnClickListener((v) -> {
            selectedItemList = adapter.getItemList();

        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener((v) -> {
            selectedItemList.clear();
            Toast.makeText(this, "No Item Selected", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(SelectItems.this));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);
    }


}