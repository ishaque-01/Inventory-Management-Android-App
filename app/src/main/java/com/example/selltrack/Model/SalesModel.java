package com.example.selltrack.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SalesModel implements Parcelable {
    private int salesId, customerId;
    private float totalPrice;
    private String date;

    public SalesModel(int salesId, int customerId, float totalPrice, String date) {
        this.salesId = salesId;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public SalesModel(int customerId, float totalPrice, String date) {
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    protected SalesModel(Parcel in) {
        salesId = in.readInt();
        customerId = in.readInt();
        totalPrice = in.readFloat();
        date = in.readString();
    }

    public static final Creator<SalesModel> CREATOR = new Creator<SalesModel>() {
        @Override
        public SalesModel createFromParcel(Parcel in) {
            return new SalesModel(in);
        }

        @Override
        public SalesModel[] newArray(int size) {
            return new SalesModel[size];
        }
    };

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(salesId);
        dest.writeInt(customerId);
        dest.writeFloat(totalPrice);
        dest.writeString(date);
    }
}
