package ku.project.models.products;

import ku.project.filter.ProductFilterer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProductList {
    private ArrayList<Product> productList;
    public ProductList() {
        productList = new ArrayList<>();
    }
    public Product searchProductById(String id) {
        for (Product item: productList)
            if (item.checkId(id)) return item;
        return null;
    }
    public ProductList filter(ProductFilterer filterer) {
        ProductList filtered = new ProductList();
        for (Product product : productList)
            if (filterer.filter(product))
                filtered.addProduct(product);
        return filtered;
    }
    public String toCsv() {
        String result = "";
        for (Product product: productList) {
            result += product.toCsv() + "\n";
        }
        return result;
    }
    public String initialProductId() {
        long time = System.currentTimeMillis();
        long num1 = time%1000000;
        long num2 = time/1000000;
        time = num2-num1;
        time *= time;
        return String.format("%06d", time%1000000);
    }
    public void addProduct(Product product) {
        productList.add(product);
    }
    public ArrayList<Product> getProductList() { return productList; }

    public void addProduct(String username, String productName, String productCategory, String productLocation, String productDetails,
                           String productDatePurchased, String productTimePurchased, String productStatus,
                           String imagePath, String isBorrow, String holder, String dateTimeOwner) {
        String idProduct = initialProductId();
        //String imagePath = idProduct + "-" + "product.png";
        Product product = new Product(username,idProduct,productName,productCategory,productLocation,productDetails,
                productDatePurchased,productTimePurchased,productStatus,imagePath,isBorrow,holder,dateTimeOwner);
        productList.add(product);
        product.setImagePath();
    }

    public void sort(Comparator<Product> productComparator) {
        Collections.sort(this.productList, productComparator);
    }
}
