package trendyol.shopping.cart;

import trendyol.shopping.Product;
import trendyol.shopping.dicounts.campaign.ICampaign;
import trendyol.shopping.dicounts.coupon.ICoupon;

public interface ICart {

    /**
     * Add products into cart with given number of products
     *
     * @param product  product which will be added in cart
     * @param quantity quantity of products which will be added in cart
     */
    void addItem(Product product, int quantity);

    void applyDiscounts(ICampaign... campaigns);

    void applyCoupons(ICoupon... coupons);

    double getTotalAmountAfterDiscounts();

    double getCouponDiscount();

    double getCampaignDiscount();

    double getDeliveryCost();

    void print();
}
