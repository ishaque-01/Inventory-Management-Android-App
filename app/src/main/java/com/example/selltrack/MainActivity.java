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

import com.example.selltrack.Model.ItemModel;
import com.example.selltrack.data.DBHandler;
import com.example.selltrack.databinding.ActivityMainBinding;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    DBHandler dbHandler;
    List<ItemModel> itemsList;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        } catch (Exception e ) {
            Log.d("navError", "Error: " + e);
        }
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
        //dbHandler.checkTables();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentView, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

//    public void checkInventory(View view) {
//        Fragment curr = getSupportFragmentManager().findFragmentById(R.id.fragmentView);
//        if(curr instanceof InventoryFragment)
//            return;
//
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(
//                R.anim.slide_in_left,
//                R.anim.slide_out_right,
//                R.anim.slide_in_right,
//                R.anim.slide_out_left
//        );
//        fragmentTransaction.replace(R.id.fragmentView, new InventoryFragment(), null);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }


    //    public void checkSells(View view) {
//        Fragment curr = getSupportFragmentManager().findFragmentById(R.id.fragmentView);
//        if(curr instanceof SellsFragment)
//            return;
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(
//                R.anim.slide_in_right,
//                R.anim.slide_out_left,
//                R.anim.slide_in_left,
//                R.anim.slide_out_right
//        );
//        fragmentTransaction.replace(R.id.fragmentView, new SellsFragment(), null);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
    public List<ItemModel> getItemsList() {
        if (itemsList == null)
            itemsList = dbHandler.getInventoryItems();
        return itemsList;
    }
}