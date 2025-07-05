package main.java.com.fawry.ecommerce.model.cart;

import main.java.com.fawry.ecommerce.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<CartItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Cannot add null product to cart");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Cart Item quantity must be positive");
        }

        if (!product.isStockAvailable(quantity)) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }

        // Check if the product already exists in the cart
        for (CartItem existingItem : items) {
            if (existingItem.getProduct().getName().equals(product.getName())) {
                int totalQuantity = existingItem.getQuantity() + quantity;
                if (!product.isStockAvailable(totalQuantity)) {
                    throw new IllegalArgumentException("Insufficient stock for product: " + product.getName() + " after adding to cart");
                }
                existingItem.increaseQuantity(quantity);
                return;
            }
        }

        CartItem newItem = new CartItem(product, quantity);
        items.add(newItem);
    }

    public void removeItem(Product product) {
        if(product == null) {
            throw new IllegalArgumentException("Cannot remove null product from cart");
        }

        items.removeIf(item -> item.getProduct().getName().equals(product.getName()));
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}
