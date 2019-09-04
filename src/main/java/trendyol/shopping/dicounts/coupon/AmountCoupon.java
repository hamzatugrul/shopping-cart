package trendyol.shopping.dicounts.coupon;

public class AmountCoupon implements ICoupon {

    private double amount;
    private double minAmountConstraintForApply;

    public AmountCoupon(double amount, double minAmountConstraintForApply) {
        this.amount = amount;
        this.minAmountConstraintForApply = minAmountConstraintForApply;
    }

    @Override
    public boolean isApplicable(double totalAmount) {
        return totalAmount >= this.minAmountConstraintForApply;
    }

    @Override
    public double getDiscount(double totalAmount) {
        return totalAmount > this.amount ? this.amount : 0;
    }
}
