package main.java.com.fawry.ecommerce.service;

import main.java.com.fawry.ecommerce.component.ShippingComponent;
import main.java.com.fawry.ecommerce.model.Customer;
import main.java.com.fawry.ecommerce.model.cart.CartItem;
import main.java.com.fawry.ecommerce.model.cart.ShoppingCart;
import main.java.com.fawry.ecommerce.model.product.Product;
import main.java.com.fawry.ecommerce.model.product.Shippable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CheckoutService {
    private final ShippingService shippingService;
    private final CheckoutValidator checkoutValidator;

    public CheckoutService(ShippingService shippingService, CheckoutValidator checkoutValidator) {
        this.shippingService = shippingService;
        this.checkoutValidator = checkoutValidator;
    }

    public void processCheckout(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Cannot process checkout for null customer.");
        }

        List<Shippable> shippableItems = getShippableItems(customer.getShoppingCart());
        double shippingCost = shippingService.calculateFees(shippableItems);
        double totalCost = customer.getShoppingCart().getTotalPrice() + shippingCost;

        checkoutValidator.validate(customer, totalCost);
        commitCheckout(customer, shippableItems, totalCost);
        printCheckoutReceipt(customer, shippingCost, totalCost);
    }

    private void commitCheckout(Customer customer, List<Shippable> shippableItems, double totalCost) {
        updateCustomerBalance(customer, totalCost);
        updateProductStock(customer.getShoppingCart());
        shippingService.commitShipping(shippableItems);
    }

    private List<Shippable> getShippableItems(ShoppingCart shoppingCart) {
        return shoppingCart.getItems().stream()
                .filter(item -> item.getProduct().getComponent(ShippingComponent.class).isPresent())
                .flatMap(item -> {
                    Shippable shippableComponent = item.getProduct().getComponent(ShippingComponent.class).get();
                    int quantity = item.getQuantity();
                    return Collections.nCopies(quantity, shippableComponent).stream();
                })
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

    private void printCheckoutReceipt(Customer customer, double shippingCost, double totalCost) {
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
        System.out.printf("%-22s $%6.2f\n", "Paid Amount:", totalCost);
        System.out.printf("%-22s $%6.2f\n", "New Customer Balance:", customer.getBalance());
    }
}
