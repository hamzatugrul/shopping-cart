package trendyol.shopping.delivery;

public interface IDeliveryCostCalculator {

    /**
     * Calculates delivery cost<br>
     * It applies dynamic cargo pricing rules based on given the number of deliveries and number of products
     *
     * @param numberOfDeliveries number of deliveries (categories)
     * @param numberOfProducts   number of products
     * @return double - Delivery Cost
     */
    double calculateFor(int numberOfDeliveries, int numberOfProducts);
}
