package main.java.com.fawry.ecommerce.model.cart;

import main.java.com.fawry.ecommerce.model.product.IProduct;

public class CartItem {
    private final IProduct product;
    private int quantity;

    public CartItem(IProduct product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public IProduct getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Cart Item increase amount must be positive");
        }
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Cart Item decrease amount must be positive");
        }
        if (amount > quantity) {
            throw new IllegalArgumentException("Cannot decrease cart item quantity below zero");
        }
        this.quantity -= amount;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
