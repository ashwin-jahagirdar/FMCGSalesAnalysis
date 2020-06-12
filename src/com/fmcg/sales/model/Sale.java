package com.fmcg.sales.model;

import java.time.LocalDate;
import java.util.Optional;

public class Sale {

    private SalesPerson salesPerson;
    private Product product;
    private LocalDate date;
    private Optional<Discount> discount;

    public Sale(SalesPerson salesPerson, Product product, LocalDate date, Optional<Discount> discount) {
        this.salesPerson = salesPerson;
        this.product = product;
        this.date = date;
        this.discount = discount;
    }

    public SalesPerson getSalesPerson() {
        return salesPerson;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDate getDate() {
        return date;
    }

    public Optional<Discount> getDiscount() {
        return discount;
    }

    public ProductType getProductType() {
        return product.getProductType();
    }

    public int getSalesPrice() {
        return product.getPrice();
    }

    public String getSaleDetails() {
        return String.format("%s sold %s on %s with %s discount", salesPerson.getName(), product.getProductType(), date, discount.get());
    }
}
