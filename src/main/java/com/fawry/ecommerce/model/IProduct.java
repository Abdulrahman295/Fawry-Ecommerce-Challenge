package main.java.com.fawry.ecommerce.model;

import main.java.com.fawry.ecommerce.enums.ProductType;

public interface IProduct {
    String getName();
    double getPrice();
    int getQuantity();
    void setQuantity(int newQuantity);
    ProductType getType();
}
