package com.example.selltrack.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.R;


import org.w3c.dom.Text;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {

    private Context context;
    private List<ItemModel> list;
    private OnQuantityChangeListener quantityChangeListener;

    public ItemsAdapter(Context context, List<ItemModel> list) {
        this.context = context;
        this.list = list;
    }

    public List<ItemModel> getItemList() {
        return list;
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
        holder.quantity.setText("1");
        item.setQuantity(1);

        holder.quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int qty = Integer.parseInt(s.toString());
                    item.setQuantity(qty);
                    if (quantityChangeListener != null) {
                        quantityChangeListener.onQuantityChanged();
                    }
                } catch (NumberFormatException e) {
                    item.setQuantity(1);
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        holder.plus.setOnClickListener((v) -> {
            int q = Integer.parseInt(holder.quantity.getText().toString()) + 1;
            item.setQuantity(q);
            holder.quantity.setText(String.valueOf(q));
            if (quantityChangeListener != null) {
                quantityChangeListener.onQuantityChanged();
            }
        });

        holder.minus.setOnClickListener((v) -> {
            int q = Integer.parseInt(holder.quantity.getText().toString());
            if(q > 1) {
                q--;
                item.setQuantity(q);
                holder.quantity.setText(String.valueOf(q));
                if (quantityChangeListener != null) {
                    quantityChangeListener.onQuantityChanged();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemsHolder extends RecyclerView.ViewHolder {
        private TextView itemName, price;
        private EditText quantity;
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

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }
    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.quantityChangeListener = listener;
    }


}
