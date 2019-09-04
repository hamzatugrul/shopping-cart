package trendyol.shopping.cart;

import trendyol.shopping.Product;
import trendyol.shopping.dicounts.campaign.ICampaign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingItem implements Comparable<ShoppingItem> {

    private Product product;
    private int quantity;
    private List<ICampaign> applicableCampaigns;

    public ShoppingItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.applicableCampaigns = Collections.EMPTY_LIST;
    }

    public Product getProduct() {
        return this.product;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void addApplicableCampaignForProduct(ICampaign campaign) {
        if (campaign.isApplicable(this.product.getCategory(), this.quantity)) {
            this.addCampaign(campaign);
        }
    }

    private void addCampaign(ICampaign campaign) {
        if (this.applicableCampaigns == Collections.EMPTY_LIST) {
            this.applicableCampaigns = new ArrayList<>();
            this.applicableCampaigns.add(campaign);
        } else if (!this.applicableCampaigns.contains(campaign)) {
            this.applicableCampaigns.add(campaign);
        }
    }

    public double getTotalPrice() {
        return this.product.getPrice() * this.quantity;
    }

    /**
     * Choose best campaign if more campaign tries to apply on one product
     * and return best applied campaign
     *
     * @return Calculated best campaign discount
     */
    public double getCampaignDiscountForProduct() {
        double bestDiscount = 0;

        for (ICampaign campaign : applicableCampaigns) {
            double currentDiscount = campaign.getDiscount(this.getTotalPrice());
            if (bestDiscount < currentDiscount) {
                bestDiscount = currentDiscount;
            }
        }
        return bestDiscount;
    }

    private String getAppliedCampaignText() {
        // This method was just added for giving an example while presentation
        StringBuilder text = new StringBuilder("==> Applied Campaigns: " + applicableCampaigns.size() + "\n");
        for (ICampaign campaign : applicableCampaigns) {
            text.append("=====> ").append(campaign.toString()).append("\n");
        }
        return text.toString();
    }

    @Override
    public String toString() {
        return String.format("Category Name: %s \n%s \nQuantity: %d \nTotal Price: %.2f TL \n%s \n",
                this.product.getCategory().toString(), this.product.toString(),
                this.quantity, this.getTotalPrice(), getAppliedCampaignText());
    }

    @Override
    public int compareTo(ShoppingItem shoppingItem) {
        int result = this.product.getCategory().toString().compareTo(shoppingItem.getProduct().getCategory().toString());

        return result == 0 ?
               this.product.getTitle().compareTo(shoppingItem.getProduct().getTitle()) :
               result;
    }
}
