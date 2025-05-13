package com.example.selltrack.Model;

import com.example.selltrack.parameters.DbParams;

public class SalesItemModel {
    private int salesItemId, salesId, productId, soldQuantity;

    public SalesItemModel(int salesItemId, int salesId, int productId, int soldQuantity) {
        this.salesItemId = salesItemId;
        this.salesId = salesId;
        this.productId = productId;
        this.soldQuantity = soldQuantity;
    }

    public SalesItemModel(int salesId, int productId, int soldQuantity) {
        this.salesId = salesId;
        this.productId = productId;
        this.soldQuantity = soldQuantity;
    }

    public SalesItemModel(int productId, int soldQuantity) {
        this.productId = productId;
        this.soldQuantity = soldQuantity;
    }

    public SalesItemModel() {
    }

    public int getSalesItemId() {
        return salesItemId;
    }

    public void setSalesItemId(int salesItemId) {
        this.salesItemId = salesItemId;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }
}
