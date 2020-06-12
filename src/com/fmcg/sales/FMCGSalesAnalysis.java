package com.fmcg.sales;

import com.fmcg.sales.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fmcg.sales.service.SalesDetails.getSalesDetails;

public class FMCGSalesAnalysis {

    public static void main(String[] args) {
        System.out.println("The total revenue - " + getTotalRevenue());
        System.out.println("The total revenue during the year 2018 - " + getTotalRevenueDuringYear(2018));
        System.out.println("The total revenue earned by Tom - " + getTotalRevenueBySalesPerson("1"));
        System.out.println("All types of products sold by Tom - " + getProductTypesSoldBySalesPerson("1"));
        getDiscountedSalesDetails();
        getDiscountedSalesDetailsForDiscountType(Discount.CUSTOMER_RETENTATION_DISCOUNT);
        getDiscountedSalesDetailsForDiscountType(Discount.CLEARANCE_SALE);
        System.out.println(getTotalRevenueDuringYearForProduct(2018, ProductType.WASHING_MACHINE));
        getProductWiseTotalRevenue();
        getSalesPersonWiseSalesCountForProductType(ProductType.FRIDGE);
        System.out.println(getSalesPersonWithMaxSalesCountForProductType(ProductType.FRIDGE));
        System.out.println(getMonthWithMaxFestiveDiscounts(2019));
        getSalesStatsForYear(2019);
        System.out.println(getMostPopularMake(2019, ProductType.WASHING_MACHINE));
    }

    //find total revenue
    public static long getTotalRevenue() {
        return getSalesDetails()
                .stream()
                .map(Sale::getProduct)
                .mapToInt(Product::getPrice)
                .sum();
    }

    //find total revenue during a given year
    public static long getTotalRevenueDuringYear(int year) {
        return getSalesDetails()
                .stream()
                .filter(sale -> sale.getDate().getYear() == year)
                .map(Sale::getProduct)
                .mapToInt(Product::getPrice)
                .sum();
    }

    //find total revenue year-wise, product-wise
    public static long getTotalRevenueDuringYearForProduct(int year, ProductType productType) {
        return getSalesDetails()
                .stream()
                .filter(sale -> sale.getDate().getYear() == year)
                .filter(sale -> sale.getProduct().getProductType().equals(productType))
                .map(Sale::getProduct)
                .mapToInt(Product::getPrice)
                .sum();
    }

    //find total revenue earned by a given sales person
    public static long getTotalRevenueBySalesPerson(String salesPersonId) {
        return getSalesDetails()
                .stream()
                .filter(sale -> sale.getSalesPerson().getId().equals(salesPersonId))
                .map(Sale::getProduct)
                .mapToInt(Product::getPrice)
                .sum();
    }

    //find all types of products sold by a sales person
    public static String getProductTypesSoldBySalesPerson(String salesPersonId) {
        return getSalesDetails()
                .stream()
                .filter(sale -> sale.getSalesPerson().getId().equals(salesPersonId))
                .map(Sale::getProductType)
                .distinct()
                .map(ProductType::name)
                .collect(Collectors.joining(",", "[", "]"));
    }

    //find details about sales with discount
    public static void getDiscountedSalesDetails() {
        getSalesDetails()
                .stream()
                .filter(sale -> sale.getDiscount().isPresent())
                .map(Sale::getSaleDetails)
                .forEach(System.out::println);
    }

    //find sales with particular discount type
    public static void getDiscountedSalesDetailsForDiscountType(Discount discount) {
        getSalesDetails()
                .stream()
                .filter(sale -> sale.getDiscount().isPresent())
                .filter(sale -> sale.getDiscount().get().equals(discount))
                .map(Sale::getSaleDetails)
                .forEach(System.out::println);
    }

    //salesPerson who sold max products of a particular type
    public static String getSalesPersonWithMaxSalesCountForProductType(ProductType productType) {
        Map<SalesPerson, Long> saleDetails = getSalesDetails()
                .stream()
                .filter(sale -> sale.getProductType() == productType)
                .collect(Collectors.groupingBy(Sale::getSalesPerson, Collectors.counting()));
        return Collections.max(saleDetails.entrySet(), Comparator.comparingLong(Map.Entry::getValue))
                .getKey()
                .getName();
    }

    //show salesPerson and count of product of a particular type sold by them
    public static void getSalesPersonWiseSalesCountForProductType(ProductType productType) {
        getSalesDetails()
                .stream()
                .filter(sale -> sale.getProductType() == productType)
                .collect(Collectors.groupingBy(Sale::getSalesPerson, Collectors.counting()))
                .forEach((salesPerson, salesCount) -> System.out.printf("%s : %d%n", salesPerson.getName(), salesCount));
    }

    //show all products and their total sales revenue
    public static void getProductWiseTotalRevenue() {
        getSalesDetails()
                .stream()
                .collect(Collectors.groupingBy(Sale::getProductType,
                        Collectors.summingInt(Sale::getSalesPrice)))
                .forEach((productType, totalRevenue) -> System.out.printf("%s : %d%n", productType, totalRevenue));
    }

    //find the month during which max festive offer discounts are offered
    public static Month getMonthWithMaxFestiveDiscounts(int year) {
        Map<Month, Long> monthWiseCount = getSalesDetails()
                .stream()
                .filter(sale -> sale.getDate().getYear() == year)
                .filter(sale -> sale.getDiscount().isPresent())
                .filter(sale -> sale.getDiscount().get() == Discount.FESTIVE_OFFER)
                .map(Sale::getDate)
                .collect(Collectors.groupingBy(LocalDate::getMonth, Collectors.counting()));
        return Collections.max(monthWiseCount.entrySet(), Comparator.comparingLong(Map.Entry::getValue)).getKey();
    }

    //show number of sales and total revenue of all product types during a given year
    public static void getSalesStatsForYear(int year) {
        getSalesDetails()
                .stream()
                .filter(sale -> sale.getDate().getYear() == year)
                .collect(Collectors.groupingBy(Sale::getProductType, Collectors.summarizingInt(Sale::getSalesPrice)))
                .forEach((productType, salesStats) -> System.out.printf("%s : Sales %d and Total Revenue %d during the year %d%n",
                        productType, salesStats.getCount(), salesStats.getSum(), year));
    }

    //find the most popular make for a given product type and year
    public static String getMostPopularMake(int year, ProductType productType) {
        Map<String, Long> makeWiseCount = getSalesDetails()
                .stream()
                .filter(sale -> sale.getDate().getYear() == year)
                .filter(sale -> sale.getProductType() == productType)
                .collect(Collectors.groupingBy(sale -> sale.getProduct().getMake(), Collectors.counting()));
        return Collections.max(makeWiseCount.entrySet(), Comparator.comparingLong(Map.Entry::getValue)).getKey();
    }
}
