package trendyol.shopping.dicounts.campaign;

import trendyol.shopping.Category;

public abstract class BaseCampaign implements ICampaign {

    protected int minQuantityForApply;
    protected Category campaignCategory;

    public BaseCampaign(Category campaignCategory, int minQuantityForApply) {
        this.campaignCategory = campaignCategory;
        this.minQuantityForApply = minQuantityForApply;
    }

    public boolean isApplicable(Category productCategory, int quantity) {
        return (quantity > this.minQuantityForApply)
                && productCategory.isUpperOfCurrentCategory(this.campaignCategory);
    }

    public abstract double getDiscount(double totalPrice);
}
