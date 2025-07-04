package main.java.com.fawry.ecommerce;

import main.java.com.fawry.ecommerce.decorator.ExpirableDecorator;
import main.java.com.fawry.ecommerce.decorator.ShippableDecorator;
import main.java.com.fawry.ecommerce.enums.ProductType;
import main.java.com.fawry.ecommerce.model.Customer;
import main.java.com.fawry.ecommerce.model.cart.CartItem;
import main.java.com.fawry.ecommerce.model.product.BaseProduct;
import main.java.com.fawry.ecommerce.model.product.IProduct;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer("Jane Doe", 200.00);
        System.out.println("Created customer: " + customer.getName() + " with balance: $" + customer.getBalance());
        System.out.println("------------------------------------");

        IProduct tv = new ShippableDecorator(
                new BaseProduct("4K TV", 150.00, 10, ProductType.TV),
                12.0 // weight
        );
        IProduct cheese = new ExpirableDecorator(
                new ShippableDecorator(
                        new BaseProduct("Cheddar Cheese", 5.00, 50, ProductType.CHEESE),
                        0.5 // weight
                ),
                LocalDate.now().plusWeeks(2) // expires in 2 weeks
        );
        System.out.println("Created products: TV and Cheese.");
        System.out.println("------------------------------------");


        System.out.println("Adding 1 TV and 2 Cheeses to the cart...");
        customer.addToCart(tv, 1);
        customer.addToCart(cheese, 2);


        System.out.println("\nCart contents for " + customer.getName() + ":");
        if (customer.getShoppingCart().isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            for (CartItem item : customer.getShoppingCart().getItems()) {
                System.out.println(
                        item.getQuantity() + " x " + item.getProduct().getName() +
                                " @ $" + String.format("%.2f", item.getProduct().getPrice()) + " each"
                );
            }
            System.out.println("Cart Total Price: $" + String.format("%.2f", customer.getShoppingCart().getTotalPrice()));
        }
        System.out.println("------------------------------------");


        System.out.println("\nRemoving TV from the cart...");
        customer.removeFromCart(tv);


        System.out.println("\nFinal cart contents:");
        if (customer.getShoppingCart().isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            for (CartItem item : customer.getShoppingCart().getItems()) {
                System.out.println(
                        item.getQuantity() + " x " + item.getProduct().getName() +
                                " @ $" + String.format("%.2f", item.getProduct().getPrice()) + " each"
                );
            }
            System.out.println("Final Cart Total Price: $" + String.format("%.2f", customer.getShoppingCart().getTotalPrice()));
        }
    }
}