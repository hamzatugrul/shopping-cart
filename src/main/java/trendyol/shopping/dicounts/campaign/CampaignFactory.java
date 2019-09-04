package trendyol.shopping.dicounts.campaign;

import trendyol.shopping.Category;
import trendyol.shopping.dicounts.DiscountType;

public class CampaignFactory {

    public static ICampaign getCampaign(Category categoryOfCampaign, double value, int quantity,
            DiscountType discountType)
    {
        if (categoryOfCampaign == null) {
            throw new IllegalArgumentException("Category of Campaign should not be null!");
        } else if (discountType == null) {
            throw new IllegalArgumentException("Discount Type should not be null!");
        } else if ((value < 0) || (quantity < 0)) {
            throw new IllegalArgumentException("Campaign Parameters should not contain negative value!");
        }

        ICampaign campaign = null;
        switch (discountType) {
            case Amount:
                campaign = new AmountCampaign(categoryOfCampaign, value, quantity);
                break;
            case Rate:
                campaign = new RateCampaign(categoryOfCampaign, value, quantity);
                break;
        }
        return campaign;
    }
}
