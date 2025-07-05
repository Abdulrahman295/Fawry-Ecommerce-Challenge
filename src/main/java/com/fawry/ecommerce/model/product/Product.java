package main.java.com.fawry.ecommerce.model.product;

import main.java.com.fawry.ecommerce.component.Component;
import main.java.com.fawry.ecommerce.enums.ProductType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Product{
    private final String name;
    private final double price;
    private final ProductType type;
    private final Map<Class<? extends Component>, Component> components;
    private int quantity;

    public Product(String name, double price, int quantity, ProductType type) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.components = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductType getType() {
        return type;
    }

    public boolean isStockAvailable(int requestedQuantity) {
        return this.quantity >= requestedQuantity;
    }

    public void increaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Stock increase amount must be positive");
        }
        this.quantity += amount;
    }

    public void decreaseStock(int amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Stock decrease amount must be positive");
        }

        if(!isStockAvailable(amount)) {
            throw new IllegalArgumentException("Insufficient stock for product: " + name);
        }

        this.quantity -= amount;
    }

    public void addComponent(Component component) {
        if (component == null) {
            throw new IllegalArgumentException("Product component cannot be null");
        }
        components.put(component.getClass(), component);
    }

    public void removeComponent(Class<? extends Component> componentClass) {
        if (componentClass == null) {
            throw new IllegalArgumentException("Component class cannot be null");
        }
        components.remove(componentClass);
    }

    public <T extends Component> Optional<T> getComponent(Class<T> componentClass) {
        if (componentClass == null) {
            throw new IllegalArgumentException("Component class cannot be null");
        }
        return Optional.ofNullable(componentClass.cast(components.get(componentClass)));
    }

    public boolean hasComponent(Class<? extends Component> componentClass) {
        if (componentClass == null) {
            throw new IllegalArgumentException("Component class cannot be null");
        }
        return components.containsKey(componentClass);
    }
}
