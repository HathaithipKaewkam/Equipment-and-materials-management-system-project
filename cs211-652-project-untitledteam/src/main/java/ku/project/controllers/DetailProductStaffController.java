package ku.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import ku.project.models.account.Account;
import ku.project.models.products.Lender;
import ku.project.models.products.Product;
import ku.project.models.products.ProductList;
import ku.project.services.DataSource;
import ku.project.services.FXRouter;
import ku.project.services.ProductDataSrc;

import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.*;

public class DetailProductStaffController {
    @FXML private TextArea detail;

    @FXML
    private Label holderName;
    @FXML
    private Label category;

    @FXML
    private Label dateTimeOwner;

    @FXML
    private Label dateTimePurchased;

    @FXML
    private Label idProduct;

    @FXML
    private ImageView imageProduct;

    @FXML
    private Label locationProduct;

    @FXML
    private Label nameProduct;

    @FXML
    private Label statusProduct;
    private Lender lender = (Lender) FXRouter.getData();
    private Product product = lender.getProduct();
    ProductList productList;

    private DataSource<ProductList> productListDataSource;
    public void initialize() {
        productListDataSource = new ProductDataSrc("database","product.csv");
        File desDir = new File("image" + System.getProperty("file.separator")
                + "products" + System.getProperty("file.separator") + product.getImagePath());
        Image image = new Image(String.valueOf(desDir.toURI()), 500, 0, true, true);
        imageProduct.setImage(image);
        holderName.setText(product.getHolder());
        idProduct.setText(product.getId());
        nameProduct.setText(product.getProductName());
        category.setText(product.getCategory());
        statusProduct.setText(product.getStatusProduct());
        locationProduct.setText(product.getLocation());
        dateTimePurchased.setText(product.getDatePurchased());
        dateTimeOwner.setText(product.getDateTimeOwner());
        detail.setText(product.getDetail());

    }
    @FXML
    void backToMain(ActionEvent event) {
        try {
            FXRouter.goTo("homeShowProduct",lender);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
