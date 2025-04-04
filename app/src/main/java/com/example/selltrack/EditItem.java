package com.example.selltrack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.data.DBHandler;

import java.util.Timer;
import java.util.TimerTask;

public class EditItem extends AppCompatActivity {

    private TextView itemName, itemPrice, itemQuantity;
    private Button editItem;
    private ItemModel item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        item = getIntent().getParcelableExtra("item");

        itemName = findViewById(R.id.name);
        itemPrice = findViewById(R.id.itemPrice);
        itemQuantity = findViewById(R.id.itemQuantity);

        itemName.setText(item.getItemName());
        itemPrice.setText(String.valueOf(item.getPrice()));
        itemQuantity.setText(String.valueOf(item.getQuantity()));

        editItem = findViewById(R.id.editItem);

        editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formValidation();
            }
        });
    }

    private void formValidation() {
        if (!itemName.getText().toString().isBlank() && !itemPrice.getText().toString().isBlank() && !itemQuantity.getText().toString().isBlank()) {
            float price = Float.parseFloat(itemPrice.getText().toString());
            int quantity = Integer.parseInt(itemQuantity.getText().toString());
            DBHandler dbHandler = DBHandler.getInstance(EditItem.this);
            ItemModel updatedItem = new ItemModel(item.getItemName(), quantity, price);
            dbHandler.updateItem(updatedItem);
            showMessage();
        } else {
            if (itemPrice.getText().toString().isBlank()) {
                itemPrice.setError("Enter Price");
            }
            if (itemQuantity.getText().toString().isBlank()) {
                itemQuantity.setError("Enter Quantity");
            }
        }
    }

    private void showMessage() {
        String message = "Item Updated";
        editItem.setText(message);
        TimerTask task = new TimerTask() {
            public void run() {
                String message = "Edit Item";
                editItem.setText(message);
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, 2000);
    }
}