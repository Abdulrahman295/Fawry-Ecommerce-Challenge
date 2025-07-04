package main.java.com.fawry.ecommerce.model.product;

import java.time.LocalDate;

public interface Expirable {
    LocalDate getExpirationDate();
    boolean isExpired();
}
