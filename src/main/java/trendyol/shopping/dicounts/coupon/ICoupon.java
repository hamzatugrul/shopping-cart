package trendyol.shopping.dicounts.coupon;

public interface ICoupon {
    /**
     * <b>Check whether coupon could be applied for given total amount of products in cart</b>
     * <br>
     * Coupons have minimum amount. If the cart total is less than minimum amount, coupon is not applicable.
     *
     * @param totalAmount Total amount of products in cart
     * @return Returns true if it is applicable otherwise returns false
     */
    boolean isApplicable(double totalAmount);

    /**
     * Get discount for given total amount of products in cart
     *
     * @param totalAmount Total amount of products in cart
     * @return Calculated discount for products in cart
     */
    double getDiscount(double totalAmount);
}
