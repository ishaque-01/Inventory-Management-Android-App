package com.example.selltrack.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ItemModel implements Parcelable {

    private String itemName;
    private int quantity, id;
    private float price;

    public ItemModel(int id, String itemName, int quantity, float price) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public ItemModel(String itemName, int quantity, float price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
    public ItemModel(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }
    public ItemModel(float price, String itemName) {
        this.price = price;
        this.itemName = itemName;
    }
    public ItemModel(){}

    protected ItemModel(Parcel in) {
        itemName = in.readString();
        quantity = in.readInt();
        id = in.readInt();
        price = in.readFloat();
    }

    public static final Creator<ItemModel> CREATOR = new Creator<ItemModel>() {
        @Override
        public ItemModel createFromParcel(Parcel in) {
            return new ItemModel(in);
        }

        @Override
        public ItemModel[] newArray(int size) {
            return new ItemModel[size];
        }
    };

    public String getItemName() {
        return itemName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeInt(quantity);
        dest.writeInt(id);
        dest.writeFloat(price);
    }
}

