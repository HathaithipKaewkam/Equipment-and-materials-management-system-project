package ku.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.project.models.account.Account;
import ku.project.models.account.AccountList;
import ku.project.models.products.Lender;
import ku.project.models.products.Product;
import ku.project.models.products.ProductList;
import ku.project.models.user.Borrow;
import ku.project.models.user.BorrowList;
import ku.project.services.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ItemController extends HomeShowProductController {
    private Product product;
    private Lender lender;

    @FXML private ImageView img;
    @FXML private AnchorPane pane;
    @FXML private Button buttonBorrow;

    @FXML private Label nameLabel;
    @FXML private Label idProduct;
    @FXML private Label status;
    @FXML private Label locationProduct;
    @FXML private Label ownerName;
    @FXML private Label categoryProduct;
    @FXML private Label datePurchased;

    BorrowList borrowList;
    ProductList productList;
    AccountList accountList;
    Account accountHolder;

    private DataSource<BorrowList> borrowListDataSource;
    private DataSource<ProductList> productListDataSource;
    private DataSource<AccountList> accountListDataSource;




    @Override
    public void initialize() {
        productListDataSource = new ProductDataSrc("database","product.csv");
        borrowListDataSource = new BorrowDataSource("database","borrowHistory.csv");
        accountListDataSource = new AccountDataSource();
        borrowList = borrowListDataSource.readData();
        productList = productListDataSource.readData();
        accountList = accountListDataSource.readData();


    }


    public void setData(Product item){
        this.product = item;
        pane.setCursor(Cursor.HAND);
        buttonBorrow.setVisible(false);
        nameLabel.setText(product.getProductName());
        status.setText(product.getStatusProduct());
        idProduct.setText(product.getId());
        locationProduct.setText(product.getLocation());
        datePurchased.setText(product.getYearPurchased());
        categoryProduct.setText(product.getCategory());
        accountHolder = accountList.findAccount(product.getHolder());
        ownerName.setText(accountHolder.getName());
        if(product.getStatusProduct().equals("ปกติ")){
            status.setStyle("-fx-background-color: #4ea2c4;");
        }
        if(product.getStatusProduct().equals("ชำรุด")){
            status.setStyle("-fx-background-color: #c45c6d;");
        }
        if(product.getStatusProduct().equals("จำหน่าย")){
            status.setStyle("-fx-background-color: #ecec5c;");
        }
        File desDir = new File("image" + System.getProperty("file.separator")
                + "products" + System.getProperty("file.separator") + item.getImagePath());
        Image image = new Image(String.valueOf(desDir.toURI()), 500, 0, true, true);
        img.setImage(image);
        if (account.getRole().equals("User")){
            pane.setCursor(Cursor.DEFAULT);
            buttonBorrow.setVisible(true);
            buttonBorrow.setCursor(Cursor.HAND);
            status.setVisible(false);
        }
    }
    @FXML void buttonBorrowClicked(MouseEvent event) {
        Stage popupStage = new Stage();
        Pane popupContent = new Pane();
        popupContent.setPrefSize(300, 150);

        // Create the scene with the popup content
        Scene popupScene = new Scene(popupContent);
        popupStage.setScene(popupScene);
        Label label = new Label("ระบุสถานที่เก็บ");
        label.setLayoutX(27);
        label.setLayoutY(42);
        popupContent.getChildren().add(label);
        TextField locationTextField = new TextField();
        locationTextField.setLayoutX(126);
        locationTextField.setLayoutY(37);
        popupContent.getChildren().add(locationTextField);
        Button confirmButton = new Button("ยืนยัน");
        confirmButton.setLayoutX(116);
        confirmButton.setLayoutY(104);
        confirmButton.setOnAction(e -> {
            String locationHold = locationTextField.getText();
            if (locationHold.isEmpty()) {
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION,"โปรดกรอกสถานที่เก็บ", ButtonType.OK);
                alert1.showAndWait();
                System.out.println(locationHold);
                if(alert1.getResult() == ButtonType.OK){
                    ((Stage)alert1.getDialogPane().getScene().getWindow()).close();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "แน่ใจว่าคุณต้องการจะยืมครุภัณฑ์?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    Borrow borrow = new Borrow(account.getUsername(), product.getId(), product.getProductName(), product.getCategory(), "รอยืนยันการยืม");
                    borrow.setUserName(account.getUsername());
                    borrowList.addBorrow(borrow);
                    borrowListDataSource.writeData(borrowList);
                    product = productList.searchProductById(borrow.getIdProduct());
                    product.setIsBorrow("มีคนยืมแล้ว");
                    product.setLocationProduct(locationHold);
                    productListDataSource.writeData(productList);
                    popupStage.close();
                    try {
                        Alert alertN = new Alert(Alert.AlertType.NONE);
                        alertN.setAlertType(Alert.AlertType.WARNING);
                        alertN.setContentText("ส่งคำร้องขอยืมแล้ว");
                        alertN.show();
                        Lender lender1 = new Lender(account);
                        FXRouter.goTo("homeShowProduct", lender1);
                    } catch (IOException f) {
                        System.err.println("ไปที่หน้า showProduct ไม่ได้");
                        System.err.println("ให้ตรวจสอบการกำหนด route");
                        f.printStackTrace();
                    }
                }
                if (alert.getResult() == ButtonType.NO) {
                    popupStage.close();
                }
            }
        });
        popupContent.getChildren().add(confirmButton);


        // Set the modality of the popup window
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // Show the popup window
        popupStage.show();
    }
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "แน่ใจว่าคุณต้องการจะยืมครุภัณฑ์?", ButtonType.YES, ButtonType.NO);
//            alert.showAndWait();
//            if (alert.getResult() == ButtonType.YES) {
//                Borrow borrow = new Borrow(account.getUsername(), product.getId(), product.getProductName(), product.getCategory(), "รอยืนยันการยืม");
//                borrowList.addBorrow(borrow);
//                borrowListDataSource.writeData(borrowList);
//                product = productList.searchProductById(borrow.getIdProduct());
//                product.setIsBorrow("มีคนยืมแล้ว");
//                productListDataSource.writeData(productList);
//
//                try {
//                    Alert alertN = new Alert(Alert.AlertType.NONE);
//                    alertN.setAlertType(Alert.AlertType.WARNING);
//                    alertN.setContentText("ส่งคำร้องขอยืมแล้ว");
//                    alertN.show();
//                    Lender lender1 = new Lender(account);
//                    FXRouter.goTo("homeShowProduct", lender1);
//                }
//                catch (IOException e) {
//                    System.err.println("ไปที่หน้า showProduct ไม่ได้");
//                    System.err.println("ให้ตรวจสอบการกำหนด route");
//                    e.printStackTrace();
//                }
//            }


    @FXML void switchToDetail(MouseEvent event) {
        lender = new Lender(account,product);
        if(account.getRole().equals("Staff")) {
            try {
                FXRouter.goTo("detailSelect", lender);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า detail ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
                e.printStackTrace();
            }
        }


    }

}
