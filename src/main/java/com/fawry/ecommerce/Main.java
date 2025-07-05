package main.java.com.fawry.ecommerce;

import main.java.com.fawry.ecommerce.component.ExpirationComponent;
import main.java.com.fawry.ecommerce.component.ShippingComponent;
import main.java.com.fawry.ecommerce.enums.ProductType;
import main.java.com.fawry.ecommerce.model.Customer;
import main.java.com.fawry.ecommerce.model.product.Product;
import main.java.com.fawry.ecommerce.service.CheckoutService;
import main.java.com.fawry.ecommerce.service.CheckoutValidator;
import main.java.com.fawry.ecommerce.service.ShippingService;
import main.java.com.fawry.ecommerce.service.StandardShippingService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
       try {
              ShippingService shippingService = new StandardShippingService();
              CheckoutValidator checkoutValidator = new CheckoutValidator();
              CheckoutService checkoutService = new CheckoutService(shippingService, checkoutValidator);

              Customer customer = new Customer("Jane Doe", 500.0);

              Product cheese = new Product("Cheese", 100, 10, ProductType.CHEESE);
              cheese.addComponent(new ExpirationComponent(LocalDate.now().plusDays(7)));
              cheese.addComponent(new ShippingComponent(cheese.getName(), 0.2));


              Product biscuits = new Product("Biscuits", 150, 10, ProductType.BISCUITS);
              biscuits.addComponent(new ExpirationComponent(LocalDate.now().plusDays(5)));
              biscuits.addComponent(new ShippingComponent(biscuits.getName(), 0.7));

              Product scratchCard = new Product("Scratch Card", 50, 5, ProductType.SCRATCH_CARD);

              customer.addToCart(cheese, 2);
              customer.addToCart(biscuits, 1);
              customer.addToCart(scratchCard, 1);

              checkoutService.processCheckout(customer);
       } catch (Exception e) {
           System.err.println("Checkout failed: " + e.getMessage());
       }
    }
}