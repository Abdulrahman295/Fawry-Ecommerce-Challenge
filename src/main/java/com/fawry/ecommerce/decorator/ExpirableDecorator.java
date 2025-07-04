package main.java.com.fawry.ecommerce.decorator;

import main.java.com.fawry.ecommerce.model.Expirable;
import main.java.com.fawry.ecommerce.model.IProduct;

import java.time.LocalDate;

public class ExpirableDecorator extends ProductDecorator implements Expirable {
    private final LocalDate expirationDate;

    public ExpirableDecorator(IProduct product, LocalDate expirationDate) {
        super(product);
        this.expirationDate = expirationDate;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }
}
