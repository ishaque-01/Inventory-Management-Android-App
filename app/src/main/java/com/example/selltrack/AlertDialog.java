package com.example.selltrack;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.selltrack.data.DBHandler;

public class AlertDialog extends Dialog {

    private final String itemName;
    private final OnDeleteListener deleteListener;

    public AlertDialog(@NonNull Context context, String itemName, OnDeleteListener deleteListener) {
        super(context);
        this.itemName = itemName;
        this.deleteListener = deleteListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.alert_dialog);

        String message = "Do You Want To Delete \"" + itemName + "\" From Database?";
        final TextView messageTxt = findViewById(R.id.textView);
        messageTxt.setText(message);

        final Button positive = findViewById(R.id.positive);

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = DBHandler.getInstance(getContext());
                dbHandler.deleteItem(itemName);
                if (deleteListener != null) {
                    deleteListener.onItemDeleted(itemName);
                }
                dismiss();
            }
        });
    }

    public interface OnDeleteListener {
        void onItemDeleted(String itemName);
    }

}
