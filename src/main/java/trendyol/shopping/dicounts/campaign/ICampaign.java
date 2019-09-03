package trendyol.shopping.dicounts.campaign;

import trendyol.shopping.Category;

public interface ICampaign {

    /**
     * Check whether campaign could be applied for given number of products and given category of product
     *
     * @param productCategory Category Of given product
     * @param quantity        Quantity of products
     * @return Returns true if it is applicable otherwise returns false
     */
    boolean isApplicable(Category productCategory, int quantity);

    /**
     * Get discount for given total price of products in cart
     *
     * @param totalPrice total price
     * @return Calculated discount
     */
    double getDiscount(double totalPrice);
}
