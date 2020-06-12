package com.fmcg.sales.service;

import com.fmcg.sales.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SalesDetails {

    public static List<Sale> getSalesDetails() {
        SalesPerson tom = new SalesPerson("1", "Tom");
        SalesPerson dick = new SalesPerson("2", "Dick");
        SalesPerson harry = new SalesPerson("3", "Harry");

        Product tv = new Product(ProductType.TELEVISION, "Samsung", 1000);
        Product washingMachineLG = new Product(ProductType.WASHING_MACHINE, "LG", 900);
        Product washingMachineSamsung = new Product(ProductType.WASHING_MACHINE, "Samsung", 900);
        Product fridge = new Product(ProductType.FRIDGE, "Samsung", 1000);

        Sale sale1 = new Sale(tom, tv, LocalDate.parse("2018-01-13"), Optional.empty());
        Sale sale2 = new Sale(dick, washingMachineLG, LocalDate.parse("2018-02-14"), Optional.empty());
        Sale sale3 = new Sale(harry, fridge, LocalDate.parse("2018-03-15"), Optional.empty());

        Sale sale4 = new Sale(tom, tv, LocalDate.parse("2019-01-13"), Optional.empty());
        Sale sale5 = new Sale(dick, washingMachineLG, LocalDate.parse("2019-01-14"), Optional.empty());
        Sale sale6 = new Sale(harry, fridge, LocalDate.parse("2019-01-15"), Optional.empty());

        Sale sale7 = new Sale(tom, washingMachineSamsung, LocalDate.parse("2018-02-14"), Optional.empty());
        Sale sale8 = new Sale(tom, fridge, LocalDate.parse("2018-03-15"), Optional.empty());

        Sale sale9 = new Sale(tom, washingMachineLG, LocalDate.parse("2019-03-19"), Optional.of(Discount.CLEARANCE_SALE));
        Sale sale10 = new Sale(tom, fridge, LocalDate.parse("2019-05-25"), Optional.of(Discount.CUSTOMER_RETENTATION_DISCOUNT));

        Sale sale11 = new Sale(tom, washingMachineLG, LocalDate.parse("2019-11-19"), Optional.of(Discount.FESTIVE_OFFER));
        Sale sale12 = new Sale(tom, fridge, LocalDate.parse("2019-12-25"), Optional.of(Discount.FESTIVE_OFFER));
        Sale sale13 = new Sale(tom, washingMachineSamsung, LocalDate.parse("2019-12-19"), Optional.of(Discount.FESTIVE_OFFER));

        return Arrays.asList(sale1, sale2, sale3,
                sale4, sale5, sale6, sale7, sale8,
                sale9, sale10, sale11, sale12, sale13);
    }
}
