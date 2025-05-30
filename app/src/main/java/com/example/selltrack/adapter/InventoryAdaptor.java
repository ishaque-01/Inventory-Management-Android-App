package com.example.selltrack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltrack.InventoryAlertDialog;
import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.R;

import java.util.List;

public class InventoryAdaptor extends RecyclerView.Adapter<InventoryAdaptor.ItemViewHolder> {
    private final List<ItemModel> itemList;
    private final Context context;

    public InventoryAdaptor(Context context, List<ItemModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.inventory_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemModel item = itemList.get(position);

        holder.itemId.setText(String.valueOf(item.getId()));
        holder.itemName.setText(item.getItemName().toUpperCase());
        holder.quantity.setText(String.valueOf(item.getQuantity()));
        holder.price.setText(String.valueOf(item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemId, itemName, quantity, price;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            itemId = itemView.findViewById(R.id.itemId);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            ItemModel i = itemList.get(position);
            InventoryAlertDialog alertDialog = new InventoryAlertDialog(context, i);
            alertDialog.show();
        }
    }

}