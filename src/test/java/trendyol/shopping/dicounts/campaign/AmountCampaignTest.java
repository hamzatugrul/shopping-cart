package trendyol.shopping.dicounts.campaign;

import org.junit.Before;
import org.junit.Test;
import trendyol.shopping.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AmountCampaignTest {

    private final int minimumQuantityForApply = 2;
    private final double amount = 20;

    private Category wearCategory;
    private Category shirtCategory;
    private Category shoesCategory;

    private AmountCampaign sutAmountCampaignForWearCategory;
    private AmountCampaign sutAmountCampaignForShirtCategory;

    @Before
    public void setUp() {
        // Wear || Wear >> Shirt || Wear >> Shoes
        wearCategory = new Category("Wear");
        shirtCategory = new Category("Shirt", wearCategory);
        shoesCategory = new Category("Shoes", wearCategory);

        // Apply Campaign For Wear and SubLevel of Categories if exists. Wear || Wear >> Shirt || Wear >> Shoes
        // Apply 20 TL discount if bought 2 more items for Wear Category
        sutAmountCampaignForWearCategory = new AmountCampaign(wearCategory, amount, minimumQuantityForApply);
        // Apply 20 TL discount if bought 2 more items for Shirt Category
        sutAmountCampaignForShirtCategory = new AmountCampaign(shirtCategory, amount, minimumQuantityForApply);
    }

    @Test
    public void givenQuantityIsLessThanMinimumAndCategoryIsOKWhenIsApplicableThenReturnTrue() {
        // Act
        boolean actualResult = sutAmountCampaignForWearCategory.isApplicable(wearCategory, minimumQuantityForApply - 1);

        // Assert
        assertFalse(actualResult);
    }

    @Test
    public void givenQuantityIsEqualThanMinimumAndCategoryIsOKWhenIsApplicableThenReturnFalse() {
        // Act
        boolean actualResult = sutAmountCampaignForWearCategory.isApplicable(wearCategory, minimumQuantityForApply);

        // Assert
        assertFalse(actualResult);
    }


    @Test
    public void givenQuantityIsMoreThanMinimumAndGivenCategoryHasCampaignWhenIsApplicableThenReturnTrue() {
        // Act
        boolean actualResult = sutAmountCampaignForWearCategory.isApplicable(wearCategory, 3);

        // Assert
        assertTrue(actualResult);
    }

    @Test
    public void givenQuantityIsOkAndParentCategoryOfGivenCategoryHasCampaignWhenIsApplicableThenReturnTrue() {
        // Act
        // Wear >> Shirt
        boolean actualResult = sutAmountCampaignForWearCategory.isApplicable(shirtCategory, 3);

        // Assert
        assertTrue(actualResult);
    }

    @Test
    public void givenQuantityIsOkAndGivenCategoryNotHaveCampaignWhenIsApplicableThenReturnFalse() {
        // Act
        boolean actualResult = sutAmountCampaignForShirtCategory.isApplicable(shoesCategory, 3);

        // Assert
        assertFalse(actualResult);
    }

    @Test
    public void givenQuantityIsOkAndParentCategoryOfGivenCategoryHasCampaignWhenIsApplicableThenReturnFalse() {
        // Act
        // Wear >> Shirt
        boolean actualResult = sutAmountCampaignForShirtCategory.isApplicable(wearCategory, 3);

        // Assert
        assertFalse(actualResult);
    }

    @Test
    public void givenTotalPriceOfCartIsLessThanDiscountWhenGetDiscountThenShouldReturnZero() {
        // Act
        final double actualResult = sutAmountCampaignForWearCategory.getDiscount(amount - 10);

        // Assert
        assertEquals(0, actualResult, 0);
    }

    @Test
    public void givenTotalPriceOfCartIsEqualToDiscountWhenGetDiscountThenShouldReturnZero() {
        // Act
        final double actualResult = sutAmountCampaignForWearCategory.getDiscount(amount);

        // Assert
        assertEquals(0, actualResult, 0);
    }

    @Test
    public void givenTotalPriceOfCartIsGreaterThanDiscountWhenGetDiscountThenShouldReturnDiscount() {
        // Act
        final double actualResult = sutAmountCampaignForWearCategory.getDiscount(amount + 10);

        // Assert
        // Discount for 20 TL discount
        assertEquals(amount, actualResult, 0);
    }
}
