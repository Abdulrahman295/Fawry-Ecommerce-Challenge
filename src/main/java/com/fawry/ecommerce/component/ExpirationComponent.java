package main.java.com.fawry.ecommerce.component;

import main.java.com.fawry.ecommerce.model.product.Expirable;

import java.time.LocalDate;

public class ExpirationComponent implements Component, Expirable{
    private final LocalDate expirationDate;

    public ExpirationComponent(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(this.expirationDate);
    }
}
