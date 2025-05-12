package com.example.selltrack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.R;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {

    private Context context;
    private List<ItemModel> list;

    public ItemsAdapter(Context context, List<ItemModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new ItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        ItemModel item = list.get(position);

        holder.itemName.setText(item.getItemName().toUpperCase());
        holder.price.setText(String.valueOf(item.getPrice()));
        holder.quantity.setText(String.valueOf(item.getQuantity()));

        holder.plus.setOnClickListener((v) -> {
            int q = Integer.parseInt(holder.quantity.getText().toString());
            holder.quantity.setText(String.valueOf(q+1));
        });

        holder.minus.setOnClickListener((v) -> {
            int q = Integer.parseInt(holder.quantity.getText().toString());
            if(q != 0)
                holder.quantity.setText(String.valueOf(q-1));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemsHolder extends RecyclerView.ViewHolder {
        private TextView itemName, price, quantity;
        private ImageButton plus, minus;

        public ItemsHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
        }
    }
}
