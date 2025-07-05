package main.java.com.fawry.ecommerce.model;

import main.java.com.fawry.ecommerce.model.cart.ShoppingCart;
import main.java.com.fawry.ecommerce.model.product.Product;

public class Customer {
    private final String name;
    private double balance;
    private ShoppingCart shoppingCart;

    public Customer(String name, double initialBalance) {
        this.name = name;
        this.balance = initialBalance;
        this.shoppingCart = new ShoppingCart();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void addToCart(Product product, int quantity) {
        shoppingCart.addItem(product, quantity);
    }

    public void removeFromCart(Product product) {
        shoppingCart.removeItem(product);
    }

    public boolean hasSufficientBalance(double amount) {
        return this.balance >= amount;
    }

    public void deductBalance(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (!hasSufficientBalance(amount)) {
            throw new IllegalArgumentException("Insufficient customer balance. Required: " + amount + ", Available: " + balance);
        }

        balance -= amount;
    }

    public void addBalance(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to add to customer balance cannot be negative");
        }
        balance += amount;
    }
}
