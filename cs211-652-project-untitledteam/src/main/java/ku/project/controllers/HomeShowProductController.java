package ku.project.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import ku.project.filter.IsBorrowProductFilter;
import ku.project.filter.ProductStatusFilter;
import ku.project.filter.CategoryProductFilter;
import ku.project.filter.SearchProductFilter;
import ku.project.models.account.Account;
import ku.project.models.account.AccountList;
import ku.project.models.products.Lender;
import ku.project.models.products.Product;
import ku.project.models.products.ProductList;
import ku.project.models.user.BorrowList;
import ku.project.services.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;


public class HomeShowProductController {

    private String category;
    // เปิด/ปิด หมวดหมู่Tab
    @FXML
    private Label catalog;
    @FXML
    private TextField searchTextField;
    @FXML
    private Label header;
    @FXML
    private Label catalogBack;
    @FXML
    private VBox slides;
    @FXML
    private VBox productPane;

    @FXML
    private Pane noProductPane;
    @FXML
    private Label noProductLabel;
    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private GridPane productContainer;
    @FXML
    private JFXButton backButton;

    @FXML
    private ComboBox sortComboBox;
    private DataSource<ProductList> productListDataSource;
    private DataSource<AccountList> accountListDataSource;
    private DataSource<BorrowList> borrowListDataSource;
    private ProductList productList;
    private AccountList accountList;
    private BorrowList borrowList;
    private String search;
    private String statusProduct;
    private String isBorrowProduct;
    private Lender lender = (Lender) FXRouter.getData();
    protected Account account = lender.getBorrower();


    public void initialize() {
        readData();
        showProduct(productList);
        slideCategory();
        initializeListener();
        if(account.getRole().equals("User")){
        backButton.setVisible(false);}

    }

    private void readData() {
        productListDataSource = new ProductDataSrc();
        productList = productListDataSource.readData();
        accountListDataSource = new AccountDataSource();
        accountList = accountListDataSource.readData();
        borrowListDataSource = new BorrowDataSource();
        borrowList = borrowListDataSource.readData();

        category = "All";
        search = "";
        statusProduct = "ปกติ";
        isBorrowProduct = "ว่าง";

    }

    //คัดกรอง category
    public ProductList categoryFilter(ProductList productList, String category) {
        ProductList filtered = productList.filter(new CategoryProductFilter(category));
        return filtered;
    }

    public ProductList searchFilter(ProductList productList, String search) {
        ProductList filtered = productList.filter(new SearchProductFilter(search));
        return filtered;
    }

    public ProductList availableStatusFilter(ProductList productList, String status) {
        ProductList filtered = productList.filter(new ProductStatusFilter(status));
        return filtered;
    }

    public ProductList isBorrowProductFilter(ProductList productList, String isBorrow) {
        ProductList filtered = productList.filter(new IsBorrowProductFilter(isBorrow));
        return filtered;
    }


    @FXML
    void switchToProfile() {
        if (account.getRole().equals("User")) {
            try {
                FXRouter.goTo("userprofile", lender);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า profile ของ user ไม่ได้");
                e.printStackTrace();
            }
        }
        if (account.getRole().equals("Staff")) {
            try {
                FXRouter.goTo("staffMain", account);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า profile ของ staff ไม่ได้");
                e.printStackTrace();
            }
        }
    }

    @FXML
    void switchToBack() {
        if (account.getRole().equals("Staff")) {
            try {
                FXRouter.goTo("staffMain", account);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า staffMain ไม่ได้");
                e.printStackTrace();
            }
        }
//        if (account.getRole().equals("User")) {
//            try {
//                FXRouter.goTo("login");
//            } catch (IOException e) {
//                System.err.println("ไปที่หน้า login ไม่ได้");
//                e.printStackTrace();
//            }
//        }
    }

    @FXML
    void switchToLogout() {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            e.printStackTrace();
        }
    }

    @FXML
    public void refresh() {
        header.setText("สินค้าทั้งหมด");
        category = "All";
        search = "";
        showProduct(productList);
    }

    @FXML
    public void handleSearchIcon() {
        search = searchTextField.getText();
        showProduct(productList);
    }

    private void clear() {
        productContainer.getChildren().clear();
    }


    public void showProduct(ProductList productList) {
        clear();
        noProductLabel.setOpacity(0);
        noProductPane.setDisable(true);
        productList = searchFilter(productList, search);
        if (account.getRole().equals("User")) {
            productList = availableStatusFilter(productList, statusProduct);
            productList = isBorrowProductFilter(productList, isBorrowProduct);
        }
        if (!category.equals("All"))
            productList = categoryFilter(productList, category);
        ArrayList<Product> products = productList.getProductList();
        if (products.size() == 0) {
            noProductLabel.setOpacity(0.45);
            noProductPane.setDisable(false);
        }
        int column = 0;
        int row = 1;
        try {
            for (Product product : products) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/project/views/Item.fxml"));
                AnchorPane itemBox = fxmlLoader.load();
                ItemController productController = fxmlLoader.getController();
                productController.setData(product);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                productContainer.add(itemBox, column++, row);
                //set productContainer width
                productContainer.setMinWidth(Region.USE_COMPUTED_SIZE);
                productContainer.setPrefWidth(Region.USE_COMPUTED_SIZE);
                productContainer.setMaxWidth(Region.USE_PREF_SIZE);

                //set productContainer height
                productContainer.setMinHeight(Region.USE_COMPUTED_SIZE);
                productContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
                productContainer.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(itemBox, new Insets(7));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToRadio() {
        header.setText("ไฟฟ้าและวิทยุ");
        category = "ไฟฟ้าและวิทยุ";
        showProduct(productList);
    }

    @FXML
    void switchToBuild() {
        header.setText("ก่อสร้าง");
        category = "ก่อสร้าง";
        showProduct(productList);

    }

    @FXML
    void switchToComputer() {
        header.setText("คอมพิวเตอร์");
        category = "คอมพิวเตอร์";
        showProduct(productList);

    }

    @FXML
    void switchToOffice() {
        header.setText("สำนักงาน");
        category = "สำนักงาน";
        showProduct(productList);
    }

    @FXML
    void switchToPromote() {
        header.setText("โฆษณาและเผยแพร่");
        category = "โฆษณาและเผยแพร่";
        showProduct(productList);
    }

    @FXML
    void switchToAll() {
        header.setText("ครุภัณฑ์ทั้งหมด");
        category = "All";
        showProduct(productList);
    }

    private void slideCategory() {
        slides.setTranslateX(0);
        productPane.setTranslateX(0);
        catalog.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            TranslateTransition tranPro = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slides);
            tranPro.setNode(productPane);
            slide.setToX(0);
            slide.play();
            slides.setTranslateX(-170);
            slide.setOnFinished((ActionEvent e) -> {
                catalog.setVisible(false);
                catalogBack.setVisible(true);
            });
        });
        catalogBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setNode(slides);

            slide.setToX(-170);

            slide.play();

            slides.setTranslateX(0);


            slide.setOnFinished((ActionEvent e) -> {
                catalog.setVisible(true);
                catalogBack.setVisible(false);
            });
        });
    }

    private void initializeListener() {
        sortComboBox.getItems().addAll("ล่าสุด", "เก่าสุด");
        sortComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue,
                                String oldString, String newString) {
                if (newString.equals("ล่าสุด")) {
                    productList.sort(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            if(o1.getDatePurchased().compareTo(o2.getDatePurchased()) > 0) return -1;
                            if(o1.getDatePurchased().compareTo(o2.getDatePurchased()) < 0) return 1;
                            return 0;
                        }
                    });
                    showProduct(productList);
                } else if (newString.equals("เก่าสุด")) {
                    productList.sort(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            if(o1.getDatePurchased().compareTo(o2.getDatePurchased()) > 0) return 1;
                            if(o1.getDatePurchased().compareTo(o2.getDatePurchased()) < 0) return -1;
                            return 0;
                        }
                    });
                    showProduct(productList);

                }
            }
        });
    }
}


