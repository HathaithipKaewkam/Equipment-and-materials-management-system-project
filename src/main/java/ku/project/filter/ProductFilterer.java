package ku.project.filter;

import ku.project.models.products.Product;
import ku.project.models.user.Borrow;

public interface ProductFilterer {
    boolean filter(Product product);
}
