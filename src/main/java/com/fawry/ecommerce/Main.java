package main.java.com.fawry.ecommerce;

import main.java.com.fawry.ecommerce.decorator.ExpirationDecorator;
import main.java.com.fawry.ecommerce.decorator.ShippingDecorator;
import main.java.com.fawry.ecommerce.enums.ProductType;
import main.java.com.fawry.ecommerce.model.Customer;
import main.java.com.fawry.ecommerce.model.product.BaseProduct;
import main.java.com.fawry.ecommerce.model.product.Product;
import main.java.com.fawry.ecommerce.service.CheckoutService;
import main.java.com.fawry.ecommerce.service.ShippingService;
import main.java.com.fawry.ecommerce.service.StandardShippingService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
       try {
           ShippingService shippingService = new StandardShippingService();
           CheckoutService checkoutService = new CheckoutService(shippingService);

           Customer customer = new Customer("John Doe", 500.00);


           Product baseCheese = new BaseProduct("Cheese", 100, 10, ProductType.CHEESE);
           Product expirableCheese = new ExpirationDecorator(baseCheese, LocalDate.now().plusDays(7));
           Product cheese = new ShippingDecorator(expirableCheese, 0.2);

           Product baseBiscuits = new BaseProduct("Biscuits", 150, 10, ProductType.BISCUITS);
           Product expirableBiscuits = new ExpirationDecorator(baseBiscuits, LocalDate.now().plusDays(5));
           Product biscuits = new ShippingDecorator(expirableBiscuits, 0.7); // 700g

           customer.addToCart(cheese, 2);
           customer.addToCart(biscuits, 1);

           checkoutService.processCheckout(customer);

       } catch (Exception e) {
           System.err.println("Checkout failed: " + e.getMessage());
       }
    }
}