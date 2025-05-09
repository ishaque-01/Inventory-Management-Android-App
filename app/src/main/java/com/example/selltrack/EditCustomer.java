package com.example.selltrack;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.selltrack.Model.CustomerModel;
import com.example.selltrack.data.DBHandler;

import java.util.Timer;
import java.util.TimerTask;

public class EditCustomer extends AppCompatActivity {

    private EditText customerName, areaId;
    private Button edit;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHandler = DBHandler.getInstance(EditCustomer.this);


        CustomerModel model = getIntent().getParcelableExtra("customer");
        int id = model.getCustomerId();
        customerName = findViewById(R.id.cName);
        customerName.setText(model.getCustomerName());
        areaId = findViewById(R.id.areaId);
        areaId.setText(String.valueOf(model.getCustomerArea()));
        edit = findViewById(R.id.edit);
        edit.setOnClickListener((v) -> {
            formValidation(id);
        });
    }

    private void formValidation(int cId) {
        if (!customerName.getText().toString().isBlank() && !areaId.getText().toString().isBlank()) {
            try {
                String n = customerName.getText().toString().toLowerCase();
                int id = Integer.parseInt(areaId.getText().toString());
                CustomerModel updated = new CustomerModel(cId, n, id);
                if(dbHandler.customerExists(updated)) {
                    showMessage("Add some changes before updating!");
                } else {
                    dbHandler.updateCustomer(updated);
                    showMessage("Customer Updated!");
                }
            } catch (Exception e) {
                showMessage("An Error Occurred! Try Again");
            }
        } else {
            if(customerName.getText().toString().isBlank()) {
                customerName.setError("Enter Customer Name");
            }
            if (areaId.getText().toString().isBlank()) {
                areaId.setError("Enter Area ID");
            }
        }
    }
    private void showMessage(String message) {
        edit.setText(message);
        TimerTask task = new TimerTask() {
            public void run() {
                String message = "Edit Customer";
                edit.setText(message);
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, 2000);
    }
}