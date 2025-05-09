package com.example.selltrack.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selltrack.CitiesAlertDialog;
import com.example.selltrack.Model.CitiesModel;
import com.example.selltrack.R;

import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityViewHolder>{
    private final Context context;
    private final List<CitiesModel> list;

    public CitiesAdapter(Context context, List<CitiesModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.cities_row, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        CitiesModel city = list.get(position);

        holder.areaId.setText(String.valueOf(city.getAreaId()));
        holder.cityName.setText(city.getCityName().toUpperCase());
        holder.areaName.setText(city.getAreaName().toUpperCase());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView areaId;
        private final TextView cityName;
        private final TextView areaName;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            areaId = itemView.findViewById(R.id.areaId);
            cityName = itemView.findViewById(R.id.cityName);
            areaName = itemView.findViewById(R.id.areaName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            CitiesModel c = list.get(position);
            CitiesAlertDialog citiesAlertDialog = new CitiesAlertDialog(context, c);
            citiesAlertDialog.show();
        }
    }
}
