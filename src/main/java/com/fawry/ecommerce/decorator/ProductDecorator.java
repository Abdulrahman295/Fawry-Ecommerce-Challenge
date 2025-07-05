package main.java.com.fawry.ecommerce.decorator;

import main.java.com.fawry.ecommerce.enums.ProductType;
import main.java.com.fawry.ecommerce.model.product.Product;

public abstract class ProductDecorator implements Product {
    protected Product product;

    public ProductDecorator(Product product) {
        this.product = product;
    }

    @Override
    public String getName() {
        return product.getName();
    }

    @Override
    public double getPrice() {
        return product.getPrice();
    }

    @Override
    public int getQuantity() {
        return product.getQuantity();
    }

    @Override
    public ProductType getType() {
        return product.getType();
    }

    @Override
    public boolean isStockAvailable(int requestedQuantity) {
        return product.isStockAvailable(requestedQuantity);
    }

    @Override
    public void increaseStock(int amount) {
        product.increaseStock(amount);
    }

    @Override
    public void decreaseStock(int amount) {
        product.decreaseStock(amount);
    }
}
