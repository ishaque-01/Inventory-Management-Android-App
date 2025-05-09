package com.example.selltrack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltrack.CustomerAlertDialog;
import com.example.selltrack.Model.CustomerModel;
import com.example.selltrack.R;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private final Context context;
    private final List<CustomerModel> list;

    public CustomerAdapter(Context context, List<CustomerModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.customer_row, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        CustomerModel customer = list.get(position);

        holder.customerId.setText(String.valueOf(customer.getCustomerId()));
        holder.customerName.setText(customer.getCustomerName().toUpperCase());
        holder.areaId.setText(String.valueOf("Area ID: " + customer.getCustomerArea()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView customerId;
        private final TextView customerName;
        private final TextView areaId;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            customerId = itemView.findViewById(R.id.customerId);
            customerName = itemView.findViewById(R.id.customerName);
            areaId = itemView.findViewById(R.id.areaId);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            CustomerModel c = list.get(position);
            CustomerAlertDialog customerAlertDialog = new CustomerAlertDialog(context, c);
            customerAlertDialog.show();
        }
    }
}
