package trendyol.shopping.cart;

import trendyol.shopping.Category;
import trendyol.shopping.Product;
import trendyol.shopping.delivery.IDeliveryCostCalculator;
import trendyol.shopping.dicounts.campaign.ICampaign;
import trendyol.shopping.dicounts.coupon.ICoupon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ShoppingCart implements ICart {

    private List<ShoppingItem> shoppingItems;
    private List<ICoupon> couponList;
    private IDeliveryCostCalculator deliveryCostCalculator;

    public ShoppingCart(IDeliveryCostCalculator deliveryCostCalculator) {
        this.shoppingItems = new ArrayList<>();
        this.couponList = new ArrayList<>();
        this.deliveryCostCalculator = deliveryCostCalculator;
    }

    public void addItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product is null!");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Invalid quantity of product :" + quantity);
        }

        Optional<ShoppingItem> shoppingItem = shoppingItems.stream()
                .filter(item -> item.getProduct() == product)
                .findFirst();

        if (shoppingItem.isPresent()) {
            shoppingItem.get().addQuantity(quantity);
        } else {
            shoppingItems.add(new ShoppingItem(product, quantity));
        }
    }

    public void applyDiscounts(ICampaign... campaigns) {
        for (ICampaign campaign : campaigns) {
            shoppingItems.forEach(shoppingItem -> shoppingItem.addApplicableCampaignForProduct(campaign));
        }
    }

    public void applyCoupons(ICoupon... coupons) {
        double totalAmount = getTotalAmountAfterCampaignDiscount();
        for (ICoupon coupon : coupons) {
            if (coupon.isApplicable(totalAmount)) {
                couponList.add(coupon);
                totalAmount -= coupon.getDiscount(totalAmount);
            }
        }
    }

    public double getTotalAmountAfterDiscounts() {
        return getTotalAmountAfterCampaignDiscount() - getCouponDiscount();
    }

    public double getTotalAmountAfterCampaignDiscount() {
        return getTotalPriceOfAllProducts() - getCampaignDiscount();
    }

    private double getTotalPriceOfAllProducts() {
        return shoppingItems.stream()
                .mapToDouble(ShoppingItem::getTotalPrice)
                .sum();
    }

    public double getCampaignDiscount() {
        return shoppingItems.stream()
                .mapToDouble(ShoppingItem::getCampaignDiscountForProduct)
                .sum();
    }

    public double getCouponDiscount() {
        double totalAmount = getTotalAmountAfterCampaignDiscount();
        double totalCouponDiscount = 0;

        for (ICoupon coupon : couponList) {
            double discount = coupon.getDiscount(totalAmount);
            totalCouponDiscount += discount;
            totalAmount -= discount;
        }

        return totalCouponDiscount;
    }

    private Set<Category> getDistinctCategories() {
        Set<Category> categories = new HashSet<>();
        for (ShoppingItem shoppingItem : shoppingItems) {
            categories.add(shoppingItem.getProduct().getCategory());
        }
        return categories;
    }

    /**
     * Get number of deliveries <br>
     * Calculated by the number of distinct categories in the cart.
     *
     * @return int - number of deliveries
     */
    private int getNumberOfDeliveries() {
        return getDistinctCategories().size();
    }

    /**
     * Get number of different products in the cart.
     *
     * @return Return size of shopping items as a list of distinct products
     */
    private int getNumberOfProducts() {
        return shoppingItems.size();
    }

    public double getDeliveryCost() {
        return deliveryCostCalculator.calculateFor(getNumberOfDeliveries(), getNumberOfProducts());
    }

    public void print() {
        System.out.println("========================= SHOPPING CART ==============================\n");

        Collections.sort(shoppingItems);
        shoppingItems.forEach(shoppingItem -> System.out.println(shoppingItem.toString()));

        double totalDiscount = getCampaignDiscount() + getCouponDiscount();
        System.out.printf("Total Price of All Product: %s TL\n", getTotalPriceOfAllProducts());
        System.out.printf(String.format("Total Discount(Campaign and Coupon): %.2f TL (%.2f + %.2f)\n",
                totalDiscount, getCampaignDiscount(), getCouponDiscount()));

        //At the final line print total amount and the delivery cost.
        System.out.printf("====================================================================\n\n");
        System.out.printf(String.format("Total Amount: %.2f TL\n", getTotalAmountAfterDiscounts()));
        System.out.printf(String.format("Delivery Cost: %.2f TL\n", getDeliveryCost()));
    }
}
