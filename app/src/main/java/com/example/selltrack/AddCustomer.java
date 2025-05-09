package com.example.selltrack;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.selltrack.Model.CustomerModel;
import com.example.selltrack.data.DBHandler;

import java.util.Timer;
import java.util.TimerTask;

public class AddCustomer extends AppCompatActivity {

    private EditText customerName, areaId;
    private Button addCustomer, resetFields;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHandler = DBHandler.getInstance(this);

        customerName = findViewById(R.id.customerName);
        areaId = findViewById(R.id.areaId);

        addCustomer = findViewById(R.id.addCustomer);
        addCustomer.setOnClickListener((v) -> {
            formValidation();
        });
        resetFields = findViewById(R.id.resetFields);
        resetFields.setOnClickListener((v) -> {
            customerName.setText("");
            areaId.setText("");
        });
    }

    private void formValidation() {
        if(!customerName.getText().toString().isBlank() && !areaId.getText().toString().isBlank()) {
            try {
                String name = customerName.getText().toString();
                int id = Integer.parseInt(areaId.getText().toString());
                CustomerModel customer = new CustomerModel(name, id);

                if(!dbHandler.customerExists(customer)) {
                    dbHandler.addCustomer(customer);
                    setMessage("Customer Added");
                } else {
                    setMessage("Customer Already Added!");
                }
            } catch (Exception e) {
                setMessage("An Error Occurred! Try Again");
            }
        } else {
            if(customerName.getText().toString().isBlank()) {
                customerName.setError("Enter Customer Name");
            }
            if(areaId.getText().toString().isBlank()) {
                areaId.setError("Enter Area ID");
            }
        }
    }

    private void setMessage(String message) {
        addCustomer.setText(message);
        TimerTask task = new TimerTask() {
            public void run() {
                String message = "Add Customer";
                addCustomer.setText(message);
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, 2000);
    }
}