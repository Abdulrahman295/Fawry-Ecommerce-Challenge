package main.java.com.fawry.ecommerce.service;

import main.java.com.fawry.ecommerce.component.ExpirationComponent;
import main.java.com.fawry.ecommerce.model.Customer;
import main.java.com.fawry.ecommerce.model.cart.ShoppingCart;
import main.java.com.fawry.ecommerce.model.product.Product;

import java.util.Optional;

public class CheckoutValidator {
    public void validate(Customer customer, double totalCost) {
        validateCartIsNotEmpty(customer.getShoppingCart());
        validateProductAvailability(customer.getShoppingCart());
        validateCustomerBalance(customer, totalCost);
    }

    private void validateCartIsNotEmpty(ShoppingCart shoppingCart) {
        if (shoppingCart.isEmpty()) {
            throw new IllegalArgumentException("Shopping cart is empty. Please add items to your cart before checking out.");
        }
    }

    private void validateProductAvailability(ShoppingCart shoppingCart) {
        shoppingCart.getItems().forEach(item -> {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            // Check stock availability
            if (!product.isStockAvailable(quantity)) {
                throw new IllegalArgumentException("Insufficient stock for product: " + item.getProduct().getName());
            }

            // Check expiration
            Optional<ExpirationComponent> expirationOptional = product.getComponent(ExpirationComponent.class);
            if (expirationOptional.isPresent() && expirationOptional.get().isExpired()) {
                throw new IllegalArgumentException("Product is expired: " + product.getName());
            }
        });
    }

    private void validateCustomerBalance(Customer customer, double totalCost) {
        if (!customer.hasSufficientBalance(totalCost)) {
            throw new IllegalArgumentException("Insufficient balance. Required: " + totalCost + ", Available: " + customer.getBalance());
        }
    }
}
