package com.example.selltrack;

import android.os.Bundle;
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

public class AddCity extends AppCompatActivity {

    EditText cityName, areaName;
    Button addCity, resetFields;

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_city);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHandler = DBHandler.getInstance(this);

        cityName = findViewById(R.id.cityName);
        areaName = findViewById(R.id.areaName);

        addCity = findViewById(R.id.addCity);
        addCity.setOnClickListener((v) -> {
            addNewCity();
        });

        resetFields = findViewById(R.id.resetFields);
        resetFields.setOnClickListener((v) -> {
            cityName.setText("");
            areaName.setText("");
        });
    }

    private void addNewCity() {
        if (!cityName.getText().toString().isBlank() && !areaName.getText().toString().isBlank()) {
           try {
               String city = cityName.getText().toString().toLowerCase().trim();
               String area = areaName.getText().toString().toLowerCase().trim();
               CitiesModel c = new CitiesModel(city, area);

               if (!dbHandler.isCityExists(c)) {
                   dbHandler.addCity(c);
                   setMessage("City & Area Added!");
               } else {
                   setMessage("City & Area Already Available!");
               }
           } catch (Exception e) {
               setMessage("An Error Occurred! Try Again");
           }
        } else {
            if(cityName.getText().toString().isBlank()) {
                cityName.setError("Enter City Name");
            }
            if(areaName.getText().toString().isBlank()) {
                areaName.setError("Enter Area Name");
            }
        }
    }
    private void setMessage(String message) {
        addCity.setText(message);
        TimerTask task = new TimerTask() {
            public void run() {
                String message = "Add City";
                addCity.setText(message);
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, 2000);
    }
}