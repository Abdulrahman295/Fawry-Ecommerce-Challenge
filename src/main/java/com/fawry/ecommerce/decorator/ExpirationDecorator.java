package main.java.com.fawry.ecommerce.decorator;

import main.java.com.fawry.ecommerce.model.product.Expirable;
import main.java.com.fawry.ecommerce.model.product.Product;

import java.time.LocalDate;

public class ExpirationDecorator extends ProductDecorator implements Expirable {
    private final LocalDate expirationDate;

    public ExpirationDecorator(Product product, LocalDate expirationDate) {
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
