package main.java.com.fawry.ecommerce.service;

import main.java.com.fawry.ecommerce.model.product.Shippable;
import java.util.List;

public interface ShippingService {
    double calculateFees(List<Shippable> shippableItems);
    void processShipping(List<Shippable> shippableItems);
}
