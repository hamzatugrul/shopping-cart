package trendyol.shopping.dicounts.campaign;

import org.junit.Test;
import trendyol.shopping.Category;
import trendyol.shopping.dicounts.DiscountType;

import static org.junit.Assert.assertTrue;

public class CampaignFactoryTest {

    private final Category categoryOfCampaign = new Category("Test");
    private final double value = 10;
    private final int quantity = 2;

    @Test(expected = IllegalArgumentException.class)
    public void givenCategoryIsNullWhenGettingCampaignThenShouldReturnException() {
        CampaignFactory.getCampaign(null, value, quantity, DiscountType.Amount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenDiscountTypeIsNullWhenGettingCampaignThenShouldReturnException() {
        CampaignFactory.getCampaign(categoryOfCampaign, value, quantity, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenValueIsNegativeWhenGettingCampaignThenShouldReturnException() {
        CampaignFactory.getCampaign(categoryOfCampaign, -1, quantity, DiscountType.Rate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenQuantityIsNegativeWhenGettingCampaignThenShouldReturnException() {
        CampaignFactory.getCampaign(categoryOfCampaign, value, -1, DiscountType.Rate);
    }

    @Test
    public void givenDiscountTypeIsRateWhenGettingCampaignThenShouldReturnRateCampaign() {
        // Act
        final ICampaign result = CampaignFactory.getCampaign(categoryOfCampaign, value, quantity, DiscountType.Rate);

        // Assert
        assertTrue(result instanceof RateCampaign);
    }

    @Test
    public void givenDiscountTypeIsAmountWhenGettingCampaignThenShouldReturnAmountCampaign() {
        // Act
        final ICampaign result = CampaignFactory.getCampaign(categoryOfCampaign, value, quantity, DiscountType.Amount);

        // Assert
        assertTrue(result instanceof AmountCampaign);
    }
}
