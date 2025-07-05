package main.java.com.fawry.ecommerce.decorator;

import main.java.com.fawry.ecommerce.model.product.Product;
import main.java.com.fawry.ecommerce.model.product.Shippable;

public class ShippingDecorator extends ProductDecorator implements Shippable {
    private final double weight; // weight in kilograms

    public ShippingDecorator(Product product, double weight) {
        super(product);
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return super.getName();
    }
}
