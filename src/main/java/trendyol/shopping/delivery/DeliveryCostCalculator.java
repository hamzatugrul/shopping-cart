package trendyol.shopping.delivery;

public class DeliveryCostCalculator implements IDeliveryCostCalculator {
    private final static double FIXED_COST = 2.99;

    private double costPerDelivery;
    private double costPerProduct;

    public DeliveryCostCalculator(double costPerDelivery, double costPerProduct) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
    }

    @Override
    public double calculateFor(int numberOfDeliveries, int numberOfProducts) {
        return (this.costPerDelivery * numberOfDeliveries) + (this.costPerProduct * numberOfProducts) + FIXED_COST;
    }
}
