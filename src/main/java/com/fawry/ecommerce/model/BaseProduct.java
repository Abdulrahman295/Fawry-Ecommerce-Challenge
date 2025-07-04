package main.java.com.fawry.ecommerce.model;

import main.java.com.fawry.ecommerce.enums.ProductType;

public class BaseProduct implements IProduct {
    private final String name;
    private final double price;
    private int quantity;
    private final ProductType type;

    public BaseProduct(String name, double price, int quantity, ProductType type) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    @Override
    public ProductType getType() {
        return type;
    }
}
