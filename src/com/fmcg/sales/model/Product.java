package com.fmcg.sales.model;

public class Product {

    private ProductType productType;
    private String make;
    private int price;

    public Product(ProductType productType, String make, int price) {
        this.productType = productType;
        this.make = make;
        this.price = price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public String getMake() {
        return make;
    }

    public int getPrice() {
        return price;
    }
}
