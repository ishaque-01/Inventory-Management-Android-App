package com.example.selltrack.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltrack.Model.CustomerModel;
import com.example.selltrack.Model.SalesModel;
import com.example.selltrack.R;
import com.example.selltrack.ShowSalesItems;
import com.example.selltrack.data.DBHandler;

import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SalesViewHolder> {

    private final Context context;
    private final List<SalesModel> list;
    private DBHandler dbHandler;

    public SalesAdapter(Context context, List<SalesModel> list) {
        this.context = context;
        this.list = list;
        dbHandler = DBHandler.getInstance(context);
    }

    @NonNull
    @Override
    public SalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sales_row, parent, false);
        return new SalesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesViewHolder holder, int position) {
        SalesModel sales = list.get(position);
        CustomerModel customer = dbHandler.getCustomerDetails(sales.getCustomerId());

        holder.salesId.setText(String.valueOf(sales.getSalesId()));
        holder.customerName.setText(customer.getCustomerName().toUpperCase());
        holder.dateTime.setText(sales.getDate());
        holder.totalPrice.setText("Price: " + sales.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class SalesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView salesId, customerName, dateTime, totalPrice;

        public SalesViewHolder(@NonNull View itemView) {
            super(itemView);
            salesId = itemView.findViewById(R.id.salesId);
            customerName = itemView.findViewById(R.id.customerName);
            dateTime = itemView.findViewById(R.id.dateTime);
            totalPrice = itemView.findViewById(R.id.totalPrice);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            SalesModel sales = list.get(position);
            Intent intent = new Intent(context, ShowSalesItems.class);
            intent.putExtra("sales", sales);
            context.startActivity(intent);
        }
    }
}
