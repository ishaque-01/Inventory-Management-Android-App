package com.example.selltrack.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CitiesModel implements Parcelable {

    private int areaId;
    private String cityName, areaName;

    public CitiesModel() {}

    public CitiesModel(String cityName, String areaName) {
        this.cityName = cityName;
        this.areaName = areaName;
    }

    public CitiesModel(String cityName, String areaName, int areaId) {
        this.cityName = cityName;
        this.areaName = areaName;
        this.areaId = areaId;
    }

    public CitiesModel(int areaId, String cityName, String areaName) {
        this.areaId = areaId;
        this.cityName = cityName;
        this.areaName = areaName;
    }

    protected CitiesModel(Parcel in) {
        areaId = in.readInt();
        cityName = in.readString();
        areaName = in.readString();
    }

    public static final Creator<CitiesModel> CREATOR = new Creator<CitiesModel>() {
        @Override
        public CitiesModel createFromParcel(Parcel in) {
            return new CitiesModel(in);
        }

        @Override
        public CitiesModel[] newArray(int size) {
            return new CitiesModel[size];
        }
    };

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(areaId);
        dest.writeString(cityName);
        dest.writeString(areaName);
    }
}
