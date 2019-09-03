package trendyol.shopping;

public class Category implements Comparable<Category> {

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

    public Category getParentCategory() {
        return parentCategory;
    }

    public boolean isInSameCategoryLine(Category category) {
        return category == this ||
                (parentCategory != null && parentCategory.isInSameCategoryLine(category));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.parentCategory != null) {
            sb.append(this.parentCategory.toString()).append(" >> ");
        }
        return sb.append(this.title).toString();
    }

    public int compareTo(Category o) {
        return this.toString().compareTo(o.toString());
    }
}
