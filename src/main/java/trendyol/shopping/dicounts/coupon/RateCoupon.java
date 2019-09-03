package trendyol.shopping.dicounts.coupon;

public class RateCoupon implements ICoupon {

    private double rate;
    private double minAmountConstraintForApply;

    public RateCoupon(double rate, double minAmountConstraintForApply) {
        this.rate = rate;
        this.minAmountConstraintForApply = minAmountConstraintForApply;
    }

    @Override
    public boolean isApplicable(double totalAmount) {
        return totalAmount >= this.minAmountConstraintForApply;
    }

    @Override
    public double getDiscount(double totalAmount) {
        return (rate / 100) * totalAmount;
    }
}
