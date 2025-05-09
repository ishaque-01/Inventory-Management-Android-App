package com.example.selltrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.selltrack.Model.CustomerModel;

public class CustomerAlertDialog extends AlertDialog {

    private CustomerModel customer;

    public CustomerAlertDialog(@NonNull Context context, CustomerModel customer) {
        super(context);
        this.customer = customer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_alert_dialog);

        String message = "Do You Want To Edit \"" + customer.getCustomerName() + "\" In Database?";
        final TextView messageTxt = findViewById(R.id.textView);
        messageTxt.setText(message);

        Button edit = findViewById(R.id.edit);
        edit.setOnClickListener(v -> {
//            try {
//                Intent intent = new Intent(getContext(), EditCustomer.class);
//                intent.putExtra("customer", customer);
//                getContext().startActivity(intent);
//            } catch (Exception e) {
//                Log.d("checkDB", e.getMessage());
//            }
            dismiss();
        });
    }
}