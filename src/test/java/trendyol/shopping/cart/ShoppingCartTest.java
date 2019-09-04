package trendyol.shopping.cart;

import org.junit.Before;
import org.junit.Test;
import trendyol.shopping.Category;
import trendyol.shopping.Product;
import trendyol.shopping.delivery.DeliveryCostCalculator;
import trendyol.shopping.delivery.IDeliveryCostCalculator;
import trendyol.shopping.dicounts.campaign.AmountCampaign;
import trendyol.shopping.dicounts.campaign.ICampaign;
import trendyol.shopping.dicounts.campaign.RateCampaign;
import trendyol.shopping.dicounts.coupon.AmountCoupon;
import trendyol.shopping.dicounts.coupon.ICoupon;
import trendyol.shopping.dicounts.coupon.RateCoupon;

import static org.junit.Assert.assertEquals;

public class ShoppingCartTest {

    private IDeliveryCostCalculator deliveryCostCalculator;

    private Category categoryClothing;
    private Category categoryMen;
    private Category categoryShirt;
    private Category categoryWomen;

    private Product productJacket;
    private Product productShirt;
    private Product productDress;

    private ICampaign campaignForManClothing;
    private ICampaign campaignForShirt;
    private ICampaign campaignForWomanClothing;

    private ICoupon rateCoupon;
    private ICoupon amountCoupon;


    private ShoppingCart sutShoppingCart;

    @Before
    public void setUp() {
        deliveryCostCalculator = new DeliveryCostCalculator(1, 1);
        sutShoppingCart = new ShoppingCart(deliveryCostCalculator);

        // Clothing >> Men's Clothing >> Shirt ---- White Shirt
        // Clothing >> Men's Clothing ---- Black Jacket
        // Clothing >> Women's Clothing ---- Blue Dress

        categoryClothing = new Category("Clothing");
        categoryMen = new Category("Men's Clothing", categoryClothing);
        categoryShirt = new Category("Shirt", categoryMen);
        categoryWomen = new Category("Women's Clothing", categoryClothing);

        productJacket = new Product("Black Jacket", 100, categoryMen);
        productShirt = new Product("White Shirt", 20, categoryShirt);
        productDress = new Product("Blue Dress", 200, categoryWomen);

        campaignForManClothing = new AmountCampaign(categoryMen, 20, 2);
        campaignForShirt = new RateCampaign(categoryShirt, 10, 2);
        campaignForWomanClothing = new AmountCampaign(categoryWomen, 10, 1);

        amountCoupon = new AmountCoupon(100, 300);
        rateCoupon = new RateCoupon(10, 200);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenProductIsNullWhenAddItemThenShouldThrowException() {
        // Arrange
        sutShoppingCart.addItem(null, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenQuantityIsNegativeWhenAddItemThenShouldThrowException() {
        // Arrange
        sutShoppingCart.addItem(productJacket, -3);
    }


    @Test
    public void givenDifferentProductAndQuantityWhenAddItemThenShouldBeAddedInCart() {
        // Arrange
        sutShoppingCart.addItem(productJacket, 3);
        sutShoppingCart.addItem(productDress, 2);

        // Act
        final int actual = sutShoppingCart.getShoppingItems().size();

        //Assert
        assertEquals(2, actual);
    }

    @Test
    public void givenSameProductAndWithDifferentQuantityWhenAddItemThenShouldBeIncreasedQuantity() {
        // Arrange
        sutShoppingCart.addItem(productJacket, 2);
        sutShoppingCart.addItem(productJacket, 1);

        // Act
        final int actualSize = sutShoppingCart.getShoppingItems().size();
        final double actualPrice = sutShoppingCart.getTotalPriceOfAllProducts();

        //Assert
        assertEquals(1, actualSize);
        assertEquals(300, actualPrice, 0);
    }

    @Test
    public void givenCampaignsWhenApplyDiscountThenShouldApplyDiscountToCart() {
        // Arrange
        sutShoppingCart.addItem(productDress, 2); // 2*200 - 400 TL - Discount for 10 TL
        sutShoppingCart.addItem(productJacket, 3); // 3*100 - 300 TL - Discount for 20 TL

        // Act
        sutShoppingCart.applyDiscounts(campaignForManClothing, campaignForWomanClothing);

        final double actualTotalPrice = sutShoppingCart.getTotalPriceOfAllProducts();
        final double actualTotalPriceAfterDiscount = sutShoppingCart.getTotalAmountAfterCampaignDiscount();
        final double actualDiscount = sutShoppingCart.getCampaignDiscount();

        // Assert
        assertEquals(700, actualTotalPrice, 0);
        assertEquals(670, actualTotalPriceAfterDiscount, 0);
        assertEquals(30, actualDiscount, 0);
    }

    @Test
    public void givenMoreCampaignsExistForParentAndSubCategoryWhenApplyDiscountThenShouldApplyBestDiscountToProductInCampaignCategory() {
        // Arrange
        sutShoppingCart.addItem(productShirt, 3); // 3*20 = 60 TL
        // Clothing >> Men's Clothing >> Shirt ---- White Shirt

        // Act
        sutShoppingCart.applyDiscounts(campaignForManClothing, campaignForShirt);
        // campaignForShirt -> %10 Rate Discount --> 6 TL
        // campaignForManClothing ->  20 TL Discount
        // choose best discount

        // Assert
        assertEquals(60, sutShoppingCart.getTotalPriceOfAllProducts(), 0);
        assertEquals(20, sutShoppingCart.getCampaignDiscount(), 0);
        assertEquals(40, sutShoppingCart.getTotalAmountAfterCampaignDiscount(), 0);
    }

    @Test
    public void givenCouponsTotalAmountIsLessThanMinPurchaseAfterApplyingCampaignWhenApplyingCouponThenCouponShouldNotBeApplied() {
        // Arrange
        sutShoppingCart.addItem(productJacket, 3); // 3*100 - 300 TL
        final ICoupon coupon = new AmountCoupon(100, 300);

        // Act
        sutShoppingCart.applyDiscounts(campaignForManClothing); // Campaign Discount for 20 TL
        sutShoppingCart.applyCoupons(coupon); // Need min 300 purchase

        // Assert
        assertEquals(300, sutShoppingCart.getTotalPriceOfAllProducts(), 0);
        assertEquals(280, sutShoppingCart.getTotalAmountAfterCampaignDiscount(), 0);
        assertEquals(0, sutShoppingCart.getCouponDiscount(), 0);
    }

    @Test
    public void givenCouponsTotalAmountIsGreaterThanMinPurchaseAfterApplyingCampaignWhenApplyingCouponThenCouponShouldBeApplied() {
        // Arrange
        sutShoppingCart.addItem(productJacket, 3); // 3*100 - 300 TL
        final ICoupon coupon = new AmountCoupon(100, 100);

        // Act
        sutShoppingCart.applyDiscounts(campaignForManClothing); // Campaign Discount for 20 TL
        sutShoppingCart.applyCoupons(coupon); // Need min 300 purchase

        // Assert
        assertEquals(300, sutShoppingCart.getTotalPriceOfAllProducts(), 0);
        assertEquals(280, sutShoppingCart.getTotalAmountAfterCampaignDiscount(), 0);
        assertEquals(100, sutShoppingCart.getCouponDiscount(), 0);
        assertEquals(180, sutShoppingCart.getTotalAmountAfterDiscounts(), 0);
    }

    @Test
    public void givenMoreCouponsWhenApplyingCouponThenCouponShouldBeApplied() {
        // Arrange
        sutShoppingCart.addItem(productJacket, 4); // 4*100 - 400 TL

        // Act
        sutShoppingCart.applyDiscounts(campaignForManClothing); // Campaign Discount for 20 TL

        // Need min 300 purchase for 100 TL discount
        // Need min 200 purchase for %10 discount
        sutShoppingCart.applyCoupons(amountCoupon, rateCoupon);

        // Assert
        assertEquals(380, sutShoppingCart.getTotalAmountAfterCampaignDiscount(), 0);
        assertEquals(100 + 28, sutShoppingCart.getCouponDiscount(), 0);
        assertEquals(252, sutShoppingCart.getTotalAmountAfterDiscounts(), 0);
    }

    private void ArrangeItemAndActToCampaignAndCoupon() {
        sutShoppingCart.addItem(productShirt, 5); // 100 TL
        sutShoppingCart.addItem(productJacket, 3); // 300 TL
        sutShoppingCart.addItem(productDress, 2); // 400 TL

        sutShoppingCart.applyDiscounts(campaignForManClothing, campaignForWomanClothing, campaignForShirt);// 20,10,20
        sutShoppingCart.applyCoupons(amountCoupon, rateCoupon); //100, 65
    }

    @Test
    public void testGetCampaignDiscount() {
        ArrangeItemAndActToCampaignAndCoupon();

        // Assert
        assertEquals(50, sutShoppingCart.getCampaignDiscount(), 0);
    }

    @Test
    public void testGetTotalAmountAfterCampaignDiscount() {
        ArrangeItemAndActToCampaignAndCoupon();

        // Assert
        assertEquals(750, sutShoppingCart.getTotalAmountAfterCampaignDiscount(), 0);
    }

    @Test
    public void testGetCouponDiscount() {
        ArrangeItemAndActToCampaignAndCoupon();

        // Assert
        assertEquals(165, sutShoppingCart.getCouponDiscount(), 0);
    }

    @Test
    public void testGetTotalAmountAfterDiscounts() {
        ArrangeItemAndActToCampaignAndCoupon();

        // Assert
        assertEquals(585, sutShoppingCart.getTotalAmountAfterDiscounts(), 0);
    }

    @Test
    public void testGetDeliveryCost() {
        ArrangeItemAndActToCampaignAndCoupon();

        // Assert
        assertEquals(8.99, sutShoppingCart.getDeliveryCost(), 0);
    }

    @Test
    public void testPrint() {
        ArrangeItemAndActToCampaignAndCoupon();

        // Print will sort shopping items and print it with Grouping Products by Category
        sutShoppingCart.print();

        final ShoppingItem firstItem = sutShoppingCart.getShoppingItems().get(0);
        final ShoppingItem lastItem = sutShoppingCart.getShoppingItems().get(2);

        assertEquals(productJacket, firstItem.getProduct());
        assertEquals(productDress, lastItem.getProduct());
    }
}
