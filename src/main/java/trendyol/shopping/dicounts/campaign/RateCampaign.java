package trendyol.shopping.dicounts.campaign;

import trendyol.shopping.Category;

public class RateCampaign extends BaseCampaign {

    private double rate;

    public RateCampaign(Category campaignCategory, double rate, int minQuantityForApply) {
        super(campaignCategory, minQuantityForApply);
        this.rate = rate;
    }

    public double getDiscount(double totalPrice) {
        return (rate / 100) * totalPrice;
    }

    @Override
    public String toString() {
        // This method was just added for giving an example while presentation
        return this.getClass().getSimpleName() + " || Rate: " + this.rate + " || Category: "
                + this.campaignCategory.getTitle() + "|| MinQuantity: " + this.minQuantityForApply;
    }
}
