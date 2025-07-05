package main.java.com.fawry.ecommerce.component;

import main.java.com.fawry.ecommerce.model.product.Shippable;

public class ShippingComponent implements Component, Shippable {
    private final String name;
    private final double weight;

    public ShippingComponent(String name, double weight) {
        this.weight = weight;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }
}
