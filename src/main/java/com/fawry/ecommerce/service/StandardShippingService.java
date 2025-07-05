package main.java.com.fawry.ecommerce.service;

import main.java.com.fawry.ecommerce.model.product.Shippable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StandardShippingService implements ShippingService {
    private static final double BASE_FEE = 5.0;
    private static final double PER_KG_FEE = 27.0;

    @Override
    public double calculateFees(List<Shippable> shippableItems) {
        if (!isShipmentRequired(shippableItems)) {
            return 0.0;
        }

        double totalWeight = calculateTotalWeight(shippableItems);
        return BASE_FEE + (totalWeight * PER_KG_FEE);
    }

    @Override
    public void commitShipping(List<Shippable> shippableItems) {
        if (!isShipmentRequired(shippableItems)) {
            System.out.println("\nNo items to ship.");
            return;
        }

        printShipmentNotice(shippableItems);
    }

    private void printShipmentNotice(List<Shippable> shippableItems) {
        System.out.println("\n** Shipment notice **");

        Map<String, List<Shippable>> groupedItems = shippableItems.stream()
                .collect(Collectors.groupingBy(Shippable::getName));

        double totalPackageWeight = 0;

        for( Map.Entry<String, List<Shippable>> entry : groupedItems.entrySet()) {
            String productName = entry.getKey();
            List<Shippable> itemsInGroup  = entry.getValue();
            int groupCount = itemsInGroup.size();
            double groupWeight = calculateTotalWeight(itemsInGroup);
            totalPackageWeight += groupWeight;

            System.out.printf("%dx %-15s %dg\n",
                    groupCount,
                    productName,
                    (int) (groupWeight * 1000) // Convert kg to grams for display
            );
        }

        System.out.println("Total package weight " + String.format("%.1f", totalPackageWeight) + "kg");
    }

    private boolean isShipmentRequired(List<Shippable> shippableItems) {
        return shippableItems != null && !shippableItems.isEmpty();
    }

    private double calculateTotalWeight(List<Shippable> shippableItems) {
        return shippableItems.stream()
                .mapToDouble(Shippable::getWeight)
                .sum();
    }
}
