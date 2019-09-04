package trendyol.shopping.dicounts.campaign;

import org.junit.Before;
import org.junit.Test;
import trendyol.shopping.Category;

import static org.junit.Assert.assertEquals;

public class RateCampaignTest {

    private final int minimumQuantityForApply = 2;
    private final double rate = 10;
    private RateCampaign sutRateCampaign;

    @Before
    public void setUp() {
        Category food = new Category("Food");
        // Apply 10% discount if bought more than 2 items for Food Category
        sutRateCampaign = new RateCampaign(food, rate, minimumQuantityForApply);

        //And most of the cases was covered in Amount Campaign test cases as both extended from BaseCampaign
    }

    @Test
    public void givenTotalPriceOfCartIsGreaterThanDiscountWhenGetDiscountThenShouldReturnDiscount() {
        // Act
        final double actualResult = sutRateCampaign.getDiscount(100);

        // Assert
        // Discount for 10% discount = 10 TL
        assertEquals(10 , actualResult, 0);
    }
}
