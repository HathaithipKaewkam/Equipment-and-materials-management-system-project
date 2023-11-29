package ku.project.filter;

import ku.project.models.products.Product;
import ku.project.models.user.Borrow;

public class CategoryProductFilter implements ProductFilterer{
    private String condition;

    public CategoryProductFilter(String condition) {
        this.condition = condition;
    }

    @Override
    public boolean filter(Product product) {
        if(product.getCategory().equals(condition)) return true;
        return false;
    }

}
