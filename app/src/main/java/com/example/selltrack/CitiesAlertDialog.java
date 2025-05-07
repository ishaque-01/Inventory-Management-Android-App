package com.example.selltrack;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.selltrack.Model.CitiesModel;
import com.example.selltrack.Model.ItemModel;

public class CitiesAlertDialog extends Dialog {

    private CitiesModel cityModel;

    public CitiesAlertDialog(@NonNull Context context, CitiesModel cityModel) {
        super(context);
        this.cityModel = cityModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cities_alert_dialog);

        String message = "Do You Want To Edit \"" + cityModel.getCityName() + "\" & \"" + cityModel.getAreaName() + "\" In Database?";
        final TextView messageTxt = findViewById(R.id.textView);
        messageTxt.setText(message);

        Button edit = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditCity.class);
                intent.putExtra("city", cityModel);
                getContext().startActivity(intent);
                dismiss();
            }
        });
    }
}
