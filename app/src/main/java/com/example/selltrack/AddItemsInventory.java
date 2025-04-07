package com.example.selltrack;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.adapter.RecyclerViewAdapter;
import com.example.selltrack.data.DBHandler;

import java.util.Timer;
import java.util.TimerTask;

public class AddItemsInventory extends AppCompatActivity {

    EditText name, itemPrice, itemQuantity;
    Button reset, add;

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_items_inventory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHandler = DBHandler.getInstance(this);

        name = findViewById(R.id.name);
        itemPrice = findViewById(R.id.itemPrice);
        itemQuantity = findViewById(R.id.itemQuantity);

        reset = findViewById(R.id.resetFields);
        add = findViewById(R.id.addItem);

        reset.setOnClickListener((v) -> {
            resetFields();
        });

        add.setOnClickListener((v) -> {
            addItemInInventory();
        });

     }

    public void addItemInInventory() {
        if (!name.getText().toString().isBlank() && !itemPrice.getText().toString().isBlank()
                && !itemQuantity.getText().toString().isBlank()) {
            try {
                String itemName = name.getText().toString().toLowerCase();
                float price = Float.parseFloat(itemPrice.getText().toString());
                int quantity = Integer.parseInt(itemQuantity.getText().toString());

                ItemModel item = new ItemModel(itemName, quantity, price);
                if (dbHandler.itemExists(item.getItemName())) {
                    setMessageAdd("Item Already Available");
                    return;
                }
                dbHandler.addItemInventory(item);
                setMessageAdd("Item Added Successfully!");

            } catch (Exception io) {
                setMessageAdd("Error Occurred! Try Again");
            }
        } else {
            if (name.getText().toString().isBlank()) {
                name.setError("Enter Product Name");
            }
            if (itemPrice.getText().toString().isBlank()) {
                itemPrice.setError("Enter Product Price");
            }
            if (itemQuantity.getText().toString().isBlank()) {
                itemQuantity.setError("Enter Product Quantity");
            }
        }
    }

    private void setMessageAdd(String message) {
        add.setText(message);
        TimerTask task = new TimerTask() {
            public void run() {
                String message = "Add Item";
                add.setText(message);
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, 2000);
    }

    private void resetFields() {
        name.setText("");
        itemPrice.setText("");
        itemQuantity.setText("");
    }
}