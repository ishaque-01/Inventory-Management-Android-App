package com.example.selltrack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.Model.SalesItemModel;
import com.example.selltrack.R;
import com.example.selltrack.data.DBHandler;

import java.util.List;

public class SalesItemsAdapter extends RecyclerView.Adapter<SalesItemsAdapter.SalesItemViewHolder> {

    private Context context;
    private List<SalesItemModel> list;
    private DBHandler dbHandler;

    public SalesItemsAdapter(Context context, List<SalesItemModel> list) {
        this.context = context;
        this.list = list;
        dbHandler = DBHandler.getInstance(context);
    }

    @NonNull
    @Override
    public SalesItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sales_item_row, parent, false);
        return new SalesItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesItemViewHolder holder, int position) {
        SalesItemModel salesItem = list.get(position);
        ItemModel item = dbHandler.getItemDetails(salesItem.getProductId());

        holder.productName.setText(item.getItemName().toUpperCase());
        holder.productId.setText("Product Id: " + item.getId());
        holder.sellItemId.setText("Sales Item Id: " + salesItem.getSalesItemId());
        holder.soldQuantity.setText("Sold Quantity: " + salesItem.getSoldQuantity());
        holder.pricePerItem.setText("Item Price: " + item.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SalesItemViewHolder extends RecyclerView.ViewHolder {
        private TextView productName, productId, sellItemId, soldQuantity, pricePerItem;

        public SalesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productId = itemView.findViewById(R.id.productId);
            sellItemId = itemView.findViewById(R.id.sellItemId);
            soldQuantity = itemView.findViewById(R.id.soldQuantity);
            pricePerItem = itemView.findViewById(R.id.pricePerItem);
        }
    }
}
