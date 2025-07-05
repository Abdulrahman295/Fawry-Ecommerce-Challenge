package main.java.com.fawry.ecommerce.service;

import main.java.com.fawry.ecommerce.model.Customer;
import main.java.com.fawry.ecommerce.model.cart.CartItem;
import main.java.com.fawry.ecommerce.model.cart.ShoppingCart;
import main.java.com.fawry.ecommerce.model.product.Expirable;
import main.java.com.fawry.ecommerce.model.product.Product;
import main.java.com.fawry.ecommerce.model.product.Shippable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CheckoutService {
    private final ShippingService shippingService;

    public CheckoutService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public void processCheckout(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Cannot process checkout for null customer.");
        }

        List<Shippable> shippableItems = getShippableItems(customer.getShoppingCart());

        double shippingCost = shippingService.calculateFees(shippableItems);

        validateCheckout(customer, shippingCost);
        commitCheckout(customer, shippableItems, shippingCost);
    }

    private void validateCheckout(Customer customer, double shippingCost) {
        validateCartIsNotEmpty(customer.getShoppingCart());
        validateProductAvailability(customer.getShoppingCart());
        validateCustomerBalance(customer, shippingCost);
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
            if(product instanceof Expirable && ((Expirable) product).isExpired()) {
                throw new IllegalArgumentException("Product " + product.getName() + " is expired and cannot be purchased.");
            }
        });
    }

    private void validateCustomerBalance(Customer customer, double shippingCost) {
        double totalCost = customer.getShoppingCart().getTotalPrice() + shippingCost;

        if (!customer.hasSufficientBalance(totalCost)) {
            throw new IllegalArgumentException("Insufficient balance. Required: " + totalCost + ", Available: " + customer.getBalance());
        }
    }

    private void commitCheckout(Customer customer, List<Shippable> shippableItems, double shippingCost) {
        double totalCost = customer.getShoppingCart().getTotalPrice() + shippingCost;

        updateCustomerBalance(customer, totalCost);
        updateProductStock(customer.getShoppingCart());
        shippingService.processShipping(shippableItems);
        printCheckoutReceipt(customer, shippingCost);
    }

    private List<Shippable> getShippableItems(ShoppingCart shoppingCart) {
        return shoppingCart.getItems().stream()
                .filter(item -> item.getProduct() instanceof Shippable)
                .flatMap(item -> Collections.nCopies(item.getQuantity(), (Shippable) item.getProduct()).stream())
                .collect(Collectors.toList());
    }

    private void updateCustomerBalance(Customer customer, double totalCost) {
        customer.deductBalance(totalCost);
    }

    private void updateProductStock(ShoppingCart shoppingCart) {
        shoppingCart.getItems().forEach(item -> {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            product.decreaseStock(quantity);
        });
    }

    private void printCheckoutReceipt(Customer customer, double shippingCost) {
        System.out.println("\n** Checkout Receipt **");
        for (CartItem item : customer.getShoppingCart().getItems()) {
            System.out.printf("%d x %-20s $%6.2f\n", item.getQuantity(), item.getProduct().getName(), item.getTotalPrice());
        }
        System.out.println("---------------------------------");
        System.out.printf("%-22s $%6.2f\n", "Subtotal:", customer.getShoppingCart().getTotalPrice());
        if( shippingCost > 0) {
            System.out.printf("%-22s $%6.2f\n", "Shipping Cost:", shippingCost);
        } else {
            System.out.println("No shipping fees applied.");
        }
        System.out.printf("%-22s $%6.2f\n", "Paid Amount:", customer.getShoppingCart().getTotalPrice() + shippingCost);
        System.out.printf("%-22s $%6.2f\n", "New Customer Balance:", customer.getBalance());
    }
}
