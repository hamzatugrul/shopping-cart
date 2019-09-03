package trendyol.shopping;

public class Product {

    private String title;
    private double price;
    private Category category;

    public Product(String title, double price, Category category) {
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return String.format("Product Name: %s \nUnitPrice: %.2f TL", title, price);
    }
}
