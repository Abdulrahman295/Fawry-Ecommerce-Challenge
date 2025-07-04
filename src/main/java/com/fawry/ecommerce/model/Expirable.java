package main.java.com.fawry.ecommerce.model;

import java.time.LocalDate;

public interface Expirable {
    LocalDate getExpirationDate();
    boolean isExpired();
}
