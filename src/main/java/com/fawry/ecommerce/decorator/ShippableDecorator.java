package main.java.com.fawry.ecommerce.decorator;

import main.java.com.fawry.ecommerce.model.product.IProduct;
import main.java.com.fawry.ecommerce.model.product.Shippable;

public class ShippableDecorator extends ProductDecorator implements Shippable {
    private final double weight;

    public ShippableDecorator(IProduct product, double weight) {
        super(product);
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
