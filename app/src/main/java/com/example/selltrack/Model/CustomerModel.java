package com.example.selltrack.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CustomerModel implements Parcelable {

    private int customerId;
    private String customerName;
    private int customerArea;

    public CustomerModel() {
    }

    public CustomerModel(String customerName, int customerArea) {
        this.customerName = customerName;
        this.customerArea = customerArea;
    }

    public CustomerModel(int customerId, String customerName, int customerArea) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerArea = customerArea;
    }

    protected CustomerModel(Parcel in) {
        customerId = in.readInt();
        customerName = in.readString();
        customerArea = in.readInt();
    }

    public static final Creator<CustomerModel> CREATOR = new Creator<CustomerModel>() {
        @Override
        public CustomerModel createFromParcel(Parcel in) {
            return new CustomerModel(in);
        }

        @Override
        public CustomerModel[] newArray(int size) {
            return new CustomerModel[size];
        }
    };

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerArea() {
        return customerArea;
    }

    public void setCustomerArea(int customerArea) {
        this.customerArea = customerArea;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(customerId);
        dest.writeString(customerName);
        dest.writeInt(customerArea);
    }
}
