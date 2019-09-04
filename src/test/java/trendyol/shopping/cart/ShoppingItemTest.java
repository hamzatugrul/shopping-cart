package trendyol.shopping.cart;

import org.junit.Before;
import org.junit.Test;
import trendyol.shopping.Category;
import trendyol.shopping.Product;
import trendyol.shopping.dicounts.campaign.AmountCampaign;
import trendyol.shopping.dicounts.campaign.ICampaign;
import trendyol.shopping.dicounts.campaign.RateCampaign;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShoppingItemTest {

    private Category categoryClothing;
    private Category categoryMen;
    private Category categoryWomen;
    private Category categoryShirt;
    private ICampaign campaignForManClothing;
    private ICampaign campaignForShirt;

    private Product productShirt;
    private ShoppingItem sutShoppingItem;

    @Before
    public void setUp() {
        categoryClothing = new Category("Clothing");
        categoryMen = new Category("Men's Clothing", categoryClothing);
        categoryShirt = new Category("Shirt", categoryMen);
        productShirt = new Product("White Shirt", 20, categoryShirt);
        categoryWomen = new Category("Women's Clothing", categoryClothing);

        sutShoppingItem = new ShoppingItem(productShirt, 5); // 100 TL

        campaignForManClothing = new AmountCampaign(categoryMen, 20, 2);
        campaignForShirt = new RateCampaign(categoryShirt, 10, 2);
    }

    @Test
    public void givenCampaignHasBothApplicableCategoryAndQuantityWhenAddingApplicableCampaignThenShouldAdd() {
        // Act
        sutShoppingItem.addApplicableCampaignForProduct(campaignForManClothing);

        // Assert
        assertTrue(sutShoppingItem.getApplicableCampaigns().size() > 0);
    }

    @Test
    public void givenCampaignHasApplicableCategoryAndNoApplicableQuantityWhenAddingApplicableCampaignThenShouldNotAdd() {
        // Act
        sutShoppingItem.addApplicableCampaignForProduct(new AmountCampaign(categoryMen, 20, 9));

        // Assert
        assertTrue(sutShoppingItem.getApplicableCampaigns().size() == 0);
    }

    @Test
    public void givenCampaignHasNotApplicableCategoryAndHasApplicableQuantityWhenAddingApplicableCampaignThenShouldNotAdd() {
        // Act
        sutShoppingItem.addApplicableCampaignForProduct(new AmountCampaign(categoryWomen, 20, 1));

        // Assert
        assertTrue(sutShoppingItem.getApplicableCampaigns().size() == 0);
    }

    @Test
    public void givenCampaignHasNotApplicableCategoryAndQuantityWhenAddingApplicableCampaignThenShouldNotAdd() {
        // Act
        sutShoppingItem.addApplicableCampaignForProduct(new AmountCampaign(categoryWomen, 20, 9));

        // Assert
        assertTrue(sutShoppingItem.getApplicableCampaigns().size() == 0);
    }

    @Test
    public void givenCampaignIsValidWhenAddingCampaignThenShouldBeAdded() {
        // Act
        sutShoppingItem.addCampaign(campaignForManClothing);

        // Assert
        assertTrue(sutShoppingItem.getApplicableCampaigns().size() > 0);
    }

    @Test
    public void whenGetBestCampaignDiscountForProductIfContainsOneCampaignThenReturnExistingCampaignDiscount() {
        //Arrange
        sutShoppingItem.addCampaign(campaignForShirt);//10 TL

        // Act
        final double actualResult = sutShoppingItem.getBestCampaignDiscountForProduct();

        // Assert
        assertEquals(10, actualResult, 0);
    }

    @Test
    public void whenGetBestCampaignDiscountForProductIfContainsMoreCampaignsThenReturnHighestCampaignDiscount() {
        //Arrange
        sutShoppingItem.addCampaign(campaignForManClothing); //20 TL
        sutShoppingItem.addCampaign(campaignForShirt);//10 TL

        // Act
        final double actualResult = sutShoppingItem.getBestCampaignDiscountForProduct();

        // Assert
        assertEquals(20, actualResult, 0);
    }

    @Test
    public void whenGetBestCampaignDiscountForProductIfDoesNotContainsAnyCampaignThenShouldReturnZero() {
        // Act
        final double actualResult = sutShoppingItem.getBestCampaignDiscountForProduct();

        // Assert
        assertEquals(0, actualResult, 0);
    }

    @Test
    public void whenGetBestCampaignDiscountForProductThenReturnHighestCampaignDiscount() {
        //Arrange
        sutShoppingItem.addCampaign(campaignForManClothing); //20 TL
        sutShoppingItem.addCampaign(campaignForShirt);//10 TL

        // Act
        final double actualResult = sutShoppingItem.getBestCampaignDiscountForProduct();

        // Assert
        assertEquals(20, actualResult, 0);
    }

    @Test
    public void whenGettingTotalPriceShouldReturnValue() {
        // Act
        final double actualResult = sutShoppingItem.getTotalPrice();

        // Verify the results
        assertEquals(100, actualResult, 0);
    }
}
