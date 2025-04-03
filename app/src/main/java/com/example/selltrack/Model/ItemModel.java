package com.example.selltrack.Model;

public class ItemModel {

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
}

