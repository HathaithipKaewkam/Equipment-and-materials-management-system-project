package ku.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.project.models.account.Account;
import ku.project.models.account.AccountList;
import ku.project.models.material.Category;
import ku.project.models.material.CategoryList;
import ku.project.models.products.Lender;
import ku.project.models.products.Product;
import ku.project.models.products.ProductList;
import ku.project.services.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddProductController {

    @FXML
    private ImageView imageProduct;

    private String imagePath;

    @FXML
    private TextArea productDetailArea;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField timePurchased;

    @FXML
    private DatePicker datePurchased;
    @FXML
    private ComboBox categoryComboBox;

    public static String getUsername;

    private DataSource<ProductList> dataSource;

    private DataSource<AccountList> accountListDataSource;

    private DataSource<ProductList> productListDataSource;
    private ProductList productList;

    private AccountList accountList;

    private Account account = (Account) FXRouter.getData();

    private Product product;

    public Alert alert;

    private DataSource<CategoryList> categoryListDataSource = new CategoryDataSource();
    private CategoryList categoryList = categoryListDataSource.readData();



    public void initialize(){
        readData();
//        ProductDataSrc ProductDataSrc = new ProductDataSrc("database", "product.csv");
//        productList = productListDataSource.readData();
        alert = new Alert(Alert.AlertType.NONE);
        System.out.println(account);
        System.out.println(product);
        initializeComboBox();
        for (Product product1 : productList.getProductList()) {
            System.out.println(product1.toCsv());}
    }
    private void readData(){
        dataSource = new ProductDataSrc();
        productList = dataSource.readData();
        accountListDataSource = new AccountDataSource();
        accountList = accountListDataSource.readData();
    }
    @FXML
    void addProductButton(ActionEvent event) throws IOException {
        String productId = initialProductId();
        String productName = productNameTextField.getText();
        String productCategory = (String) categoryComboBox.getValue();
        String productDetails = productDetailArea.getText();
        String productTimePurchased = timePurchased.getText();
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTimeOwner = localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        if (productName.isEmpty()){
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("โปรดกรอกชื่อครุภัณฑ์");
            alert.show();
        }
        else if (categoryComboBox.getValue() == null)
        {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("โปรดกรอกประเภทของครุภัณฑ์");
            alert.show();
        }
        else if (productDetails.isEmpty()){
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("โปรดกรอกรายละเอียดของครุภัณฑ์");
            alert.show();
        } else if(datePurchased.getValue() == null){
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("โปรดกรอกวันที่ซื้อครุภัณฑ์");
            alert.show();

        }else if(productTimePurchased.isEmpty()){
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("โปรดกรอกเวลาที่ซื้อครุภัณฑ์");
            alert.show();
        }
        else {
            LocalDate Localdate = datePurchased.getValue();
            String productDatePurchased = Localdate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            productList.addProduct(new Product(account.getUsername(),productId,productName,productCategory,"ส่วนกลาง",
                    productDetails,productDatePurchased,productTimePurchased,"ปกติ",imagePath,"ว่าง",
                    account.getUsername(),dateTimeOwner));
            dataSource.writeData(productList);
            FXRouter.goTo("homeShowProduct",new Lender(account));

        }

    }
    private void initializeComboBox() {
        for (Category category : categoryList.getAllCategory()) {
            if(category.getType().equals("ครุภัณฑ์")) {
                String newItem = category.getCategory();
                categoryComboBox.getItems().add(newItem);
            }
        }
    }

    @FXML
    void backButton(ActionEvent event) {
        try {
            FXRouter.goTo("staffMain",account);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

//    @FXML
//    void productPicture(ActionEvent event) {
//        String directory = "image/products";
//        FileChooser chooser = new FileChooser();
//        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
//        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG JPG image", "*.png", "*.jpg", "*.jpeg"));
//        Node source = (Node) event.getSource();
//        fileImage = chooser.showOpenDialog(source.getScene().getWindow());
//        if (fileImage != null) {
//            Image image = FileService.handleUploadPicture(fileImage, product, directory);
//            imageProduct.setImage(image);
//            //addProductDataSource.writeData(productList);
//            imageProduct.setImage(setup(new Image(fileImage.getAbsolutePath())));
//
//        }
//    }

    @FXML
    void productPicture(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        // SET FILE CHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG JPG image", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILE CHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) actionEvent.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("image/products");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_" + System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath() + System.getProperty("file.separator") + filename
                );
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                // SET NEW FILE PATH TO IMAGE
                Image image = new Image(target.toUri().toString());
                imageProduct.setImage(image);
                imagePath = filename;
                //imageProduct.setImage(new Image(fileImage.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String initialProductId() {
        long time = System.currentTimeMillis();
        long num1 = time%1000000;
        long num2 = time/1000000;
        time = num2-num1;
        time *= time;
        return String.format("%06d", time%1000000);
    }

//    public WritableImage setup(Image image){
//        PixelReader reader = image.getPixelReader();
//        int newMeasure = (int)Math.min(image.getWidth(), image.getHeight());
//        int x = (int)(image.getWidth() - newMeasure) / 2;
//        int y = (int)(image.getHeight() - newMeasure) / 2;
//        WritableImage newImage = new WritableImage(reader, x, y, newMeasure, newMeasure);
//        return newImage ;
//    }

}