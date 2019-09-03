package trendyol.shopping.dicounts.campaign;

import trendyol.shopping.Category;

public class AmountCampaign extends BaseCampaign {

    private double amount;

    public AmountCampaign(Category category, double amount, int minQuantityForApply) {
        super(category, minQuantityForApply);
        this.amount = amount;
    }

    public double getDiscount(double totalPrice) {
        return totalPrice > this.amount ? this.amount : 0;
    }

    @Override
    public String toString() {
        // This method was just added for giving an example while presentation
        return this.getClass().getSimpleName() + " || Amount: " + this.amount + " || Category: "
                + this.campaignCategory.getTitle() + "|| MinQuantity: " + this.minQuantityForApply;
    }
}
