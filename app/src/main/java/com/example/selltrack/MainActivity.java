package com.example.selltrack;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.selltrack.Model.CitiesModel;
import com.example.selltrack.Model.CustomerModel;
import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.data.DBHandler;
import com.example.selltrack.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    DBHandler dbHandler;
    List<ItemModel> itemsList;
    List<CitiesModel> citiesList;
    List<CustomerModel> customersList;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.bottomNav.setBackground(null);
        binding.bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.inventory) {
                replaceFragment(new InventoryFragment());
            } else if (id == R.id.sales) {
                replaceFragment(new SellsFragment());
            } else if (id == R.id.customers) {
                replaceFragment(new CustomersFragment());
            } else if (id == R.id.cities) {
                replaceFragment(new CitiesFragment());
            }
            return true;
        });

        dbHandler = DBHandler.getInstance(MainActivity.this);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentView, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public List<ItemModel> getItemsList() {
        if (itemsList == null)
            itemsList = dbHandler.getInventoryItems();
        return itemsList;
    }

    public List<CitiesModel> getCitiesList() {
        if(citiesList == null)
            citiesList = dbHandler.getAllCities();
        return citiesList;
    }
    public List<CustomerModel> getCustomerList() {
        if(customersList == null)
            customersList = dbHandler.getAllCustomers();
        return customersList;
    }
}