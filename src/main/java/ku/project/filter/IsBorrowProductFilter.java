package ku.project.filter;

import ku.project.models.products.Product;

public class IsBorrowProductFilter implements ProductFilterer {
    private String condition;

    public IsBorrowProductFilter(String condition) {
        this.condition = condition;
    }

    @Override
    public boolean filter(Product product) {
        if (product.getIsBorrow().equals(condition)) return true;
        return false;

    }
}