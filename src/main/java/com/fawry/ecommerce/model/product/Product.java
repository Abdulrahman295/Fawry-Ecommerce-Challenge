package main.java.com.fawry.ecommerce.model.product;

import main.java.com.fawry.ecommerce.enums.ProductType;

public interface Product {
    String getName();
    double getPrice();
    int getQuantity();
    ProductType getType();
    boolean isStockAvailable(int requestedQuantity);
    void increaseStock(int amount);
    void decreaseStock(int amount);
}
