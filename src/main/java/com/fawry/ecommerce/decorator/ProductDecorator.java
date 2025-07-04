package main.java.com.fawry.ecommerce.decorator;

import main.java.com.fawry.ecommerce.enums.ProductType;
import main.java.com.fawry.ecommerce.model.IProduct;

public abstract class ProductDecorator implements IProduct {
    protected IProduct product;

    public ProductDecorator(IProduct product) {
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
    public void setQuantity(int newQuantity) {
        product.setQuantity(newQuantity);
    }

    @Override
    public ProductType getType() {
        return product.getType();
    }
}
