package com.example.selltrack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.R;

import java.util.ArrayList;
import java.util.List;

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ItemViewHolder> {
    private final List<ItemModel> itemList;
    private List<ItemModel> list;
    private final Context context;

    public SelectAdapter(Context context, List<ItemModel> itemList) {
        this.context = context;
        this.itemList = itemList;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.select_items, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemModel item = itemList.get(position);

        holder.itemName.setText(item.getItemName().toUpperCase());
        holder.price.setText(String.valueOf(item.getPrice()));
        holder.button.setOnClickListener((v) -> {
            if(holder.button.getText().equals("Add")) {
                list.add(item);
                Toast.makeText(context, "Item added", Toast.LENGTH_SHORT).show();
                holder.button.setText("Remove");
            } else {
                list.remove(item);
                Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show();
                holder.button.setText("Add");
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public List<ItemModel>  getItemList() {
        return list;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName, price;
        private Button button;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.price);
            button = itemView.findViewById(R.id.actionButton);
        }
    }

}