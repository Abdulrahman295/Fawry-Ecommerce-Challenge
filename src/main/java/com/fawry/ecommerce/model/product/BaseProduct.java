package main.java.com.fawry.ecommerce.model.product;

import main.java.com.fawry.ecommerce.enums.ProductType;

public class BaseProduct implements Product {
    private final String name;
    private final double price;
    private final ProductType type;
    private int quantity;

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
    public ProductType getType() {
        return type;
    }

    @Override
    public boolean isStockAvailable(int requestedQuantity) {
        return this.quantity >= requestedQuantity;
    }

    @Override
    public void increaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Stock increase amount must be positive");
        }
        this.quantity += amount;
    }

    @Override
    public void decreaseStock(int amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Stock decrease amount must be positive");
        }

        if(!isStockAvailable(amount)) {
            throw new IllegalArgumentException("Insufficient stock for product: " + name);
        }

        this.quantity -= amount;
    }
}
