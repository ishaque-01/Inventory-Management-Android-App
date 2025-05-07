package com.example.selltrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.selltrack.Model.CitiesModel;
import com.example.selltrack.data.DBHandler;

import java.util.Timer;
import java.util.TimerTask;

public class EditCity extends AppCompatActivity {

    private EditText cityName, areaName;
    private Button editCity;
    private CitiesModel cityModel;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_city);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityModel = getIntent().getParcelableExtra("city");
        id = cityModel.getAreaId();
        cityName = findViewById(R.id.cityName);
        cityName.setText(cityModel.getCityName());
        areaName = findViewById(R.id.areaName);
        areaName.setText(cityModel.getAreaName());

        editCity = findViewById(R.id.editCity);
        editCity.setOnClickListener((v) -> {
            formValidation();
        });
    }
    private void formValidation() {
        if (!cityName.getText().toString().isBlank() && !areaName.getText().toString().isBlank()) {
            try {
                DBHandler dbHandler = DBHandler.getInstance(EditCity.this);
                String city = cityName.getText().toString();
                String area = areaName.getText().toString();
                CitiesModel citiesModel = new CitiesModel(city, area, id);

                if (dbHandler.isCityExists(citiesModel)) {
                    showMessage("Add some changes before updating!");
                } else {
                    dbHandler.updateCity(citiesModel);
                    showMessage("City & Area Updated");
                }
            } catch (Exception e) {
                showMessage("Unknown Error Occurred! Try Again");
            }
        } else {
            if (cityName.getText().toString().isBlank()) {
                cityName.setError("Enter City Name");
            }
            if (areaName.getText().toString().isBlank()) {
                areaName.setError("Enter Area Name");
            }
        }
    }

    private void showMessage(String message) {
        editCity.setText(message);
        TimerTask task = new TimerTask() {
            public void run() {
                String message = "Edit City";
                editCity.setText(message);
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, 2000);
    }

}