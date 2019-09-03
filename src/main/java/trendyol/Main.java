package trendyol;

import trendyol.shopping.Category;
import trendyol.shopping.Product;
import trendyol.shopping.cart.ICart;
import trendyol.shopping.cart.ShoppingCart;
import trendyol.shopping.delivery.DeliveryCostCalculator;
import trendyol.shopping.delivery.IDeliveryCostCalculator;
import trendyol.shopping.dicounts.DiscountType;
import trendyol.shopping.dicounts.campaign.CampaignFactory;
import trendyol.shopping.dicounts.campaign.ICampaign;
import trendyol.shopping.dicounts.coupon.AmountCoupon;
import trendyol.shopping.dicounts.coupon.ICoupon;
import trendyol.shopping.dicounts.coupon.RateCoupon;

public class Main {

    public static void main(String[] args) {
        IDeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(1, 1);

        Category food = new Category("Food");
        Category fruit = new Category("Fruit", food);
        Category snack = new Category("Snack", food);
        Category freshFruit = new Category("Fresh Fruit", fruit);

        Product apple = new Product("Apple", 5.0, fruit);
        Product banana = new Product("Banana", 3.0, fruit);
        Product cookie = new Product("Cookie", 20.0, snack);
        Product watermelon = new Product("Watermelon", 10, freshFruit);

        ICart cart = new ShoppingCart(deliveryCostCalculator);
        cart.addItem(banana, 2);
        cart.addItem(apple, 3);
        cart.addItem(cookie, 5);
        cart.addItem(apple, 4);
        cart.addItem(watermelon, 3);

        ICampaign campaignForFood = CampaignFactory.getCampaign(food, 10.0, 2, DiscountType.Rate);
        ICampaign campaignForFruit = CampaignFactory.getCampaign(fruit, 5.0, 2, DiscountType.Amount);

        cart.applyDiscounts(campaignForFood, campaignForFruit);

        ICoupon rateCoupon = new RateCoupon(10, 125);
        ICoupon amountCoupon = new AmountCoupon(20, 100);

        cart.applyCoupons(rateCoupon, amountCoupon);

        cart.print();
    }
}
