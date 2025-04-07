package com.example.selltrack;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.data.DBHandler;

public class AlertDialog extends Dialog {

    private ItemModel item;
    private final OnDeleteListener deleteListener;

    public AlertDialog(@NonNull Context context, ItemModel item, OnDeleteListener deleteListener) {
        super(context);
        this.item = item;
        this.deleteListener = deleteListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.alert_dialog);

        String message = "Do You Want To Delete \"" + item.getItemName() + "\" From Database?";
        final TextView messageTxt = findViewById(R.id.textView);
        messageTxt.setText(message);

        Button positive = findViewById(R.id.positive);
        Button negative = findViewById(R.id.negative);

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = DBHandler.getInstance(getContext());
                dbHandler.deleteItem(item.getItemName());
                if (deleteListener != null) {
                    deleteListener.onItemDeleted(item.getItemName());
                }
                dismiss();
            }
        });

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditItem.class);
                intent.putExtra("item", item);
                getContext().startActivity(intent);
                dismiss();
            }
        });
    }

    public interface OnDeleteListener {
        void onItemDeleted(String itemName);
    }

}
