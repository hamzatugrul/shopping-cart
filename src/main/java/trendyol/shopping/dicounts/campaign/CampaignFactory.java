package trendyol.shopping.dicounts.campaign;

import trendyol.shopping.Category;
import trendyol.shopping.dicounts.DiscountType;

public class CampaignFactory {

    public static ICampaign getCampaign(Category categoryOfCampaign, double value, int quantity,
            DiscountType discountType)
    {
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
