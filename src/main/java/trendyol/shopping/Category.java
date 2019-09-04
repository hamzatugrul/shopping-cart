package trendyol.shopping;

public class Category implements Comparable<Category> {

    protected final static String SUBCATEGORY_ARROW = ">>";

    private String title;
    private Category parentCategory;

    public Category(String title) {
        this.title = title;
        this.parentCategory = null;
    }

    public Category(String title, Category parentCategory) {
        this.title = title;
        this.parentCategory = parentCategory;
    }

    public String getTitle() {
        return title;
    }

    public boolean isUpperOfCurrentCategory(Category category) {
        return category == this ||
                (this.parentCategory != null && this.parentCategory.isUpperOfCurrentCategory(category));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.parentCategory != null) {
            sb.append(this.parentCategory.toString()).append(SUBCATEGORY_ARROW);
        }
        return sb.append(this.title).toString();
    }

    public int compareTo(Category o) {
        return this.toString().compareTo(o.toString());
    }
}
