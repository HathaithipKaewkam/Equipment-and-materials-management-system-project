package ku.project.filter;

import ku.project.models.products.Product;

public class ProductStatusFilter implements ProductFilterer{
    private String condition;

    public ProductStatusFilter(String condition) {
        this.condition = condition;
    }
    @Override
    public boolean filter(Product product) {
        if(product.getStatusProduct().equals(condition)) return true;
        return false;
    }

}
