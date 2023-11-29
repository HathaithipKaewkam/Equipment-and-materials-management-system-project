package ku.project.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.project.models.account.Account;
import ku.project.models.account.AccountList;
import ku.project.models.material.PassTo;
import ku.project.models.products.Lender;
import ku.project.models.products.Product;
import ku.project.models.products.ProductList;
import ku.project.models.user.Borrow;
import ku.project.models.user.BorrowList;
import ku.project.services.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class StaffMainController {
    @FXML private TableView approveReturnedTable;
    @FXML private TableView approveBorrowTable;

    //test
    private DataSource<AccountList> accountListDataSource = new AccountDataSource();
    private DataSource<BorrowList> borrowListDataSource = new BorrowDataSource();
    private DataSource<ProductList> productListDataSource = new ProductDataSrc();



    private AccountList accountList = accountListDataSource.readData();

    private Account account = (Account) FXRouter.getData();

    @FXML
    private Label name;

    @FXML
    private ImageView staffProfile;

    @FXML
    private Label userName;
    private ProductList productList = productListDataSource.readData();

    @FXML
    public void initialize(){
        readData();
        accountListDataSource = new AccountDataSource("database", "account.csv");
        borrowListDataSource = new BorrowDataSource("database","borrowHistory.csv");
        productListDataSource = new ProductDataSrc("database","product.csv");

        BorrowList returnedApproveList = borrowListDataSource.readData();
        BorrowList borrowApproveList = borrowListDataSource.readData();
        showTableBorrow(borrowApproveList);
        showTableReturn(returnedApproveList);
        userName.setText(account.getUsername());
        name.setText(account.getName());
        File desDir = new File("image" + System.getProperty("file.separator")
                + "profiles" + System.getProperty("file.separator") + account.getFilePictureName());
        Image image = new Image(String.valueOf(desDir.toURI()), 500, 0, true, true);
        staffProfile.setImage(image);


    }
    private void readData() {
        account = accountList.searchAccountByUsername(account.getUsername());
    }

    @FXML
    void changePicButton(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null){
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("image/profiles");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_"+System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath()+System.getProperty("file.separator")+filename
                );
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
                // SET NEW FILE PATH TO IMAGE
                staffProfile.setImage(new Image(target.toUri().toString()));
                account.setImagePath(filename);
                accountListDataSource.writeData(accountList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showTableReturn(BorrowList borrowList) {
        TableColumn<Borrow, Integer> indexColumn = new TableColumn<>("NO.");
        indexColumn.setSortable(false);
        indexColumn.setCellValueFactory(column -> {
            return new ReadOnlyObjectWrapper<Integer>(approveReturnedTable.getItems().indexOf(column.getValue()) + 1);
        });
        TableColumn<Borrow, String> userNameColumn = new TableColumn<>("ชื่อผู้ยืม");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));


        TableColumn<Borrow, String> idProductColumn = new TableColumn<>("หมายเลขครุภัณฑ์");
        idProductColumn.setCellValueFactory(new PropertyValueFactory<>("idProduct"));

        TableColumn<Borrow, String> nameColumn = new TableColumn<>("ชื่อครุภัณฑ์");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));

        TableColumn<Borrow, String> dateColumn = new TableColumn<>("วันที่คืน");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));

        TableColumn<Borrow, String> timeColumn = new TableColumn<>("เวลาที่คืน");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("returnedTime"));

        TableColumn<Borrow, Void> returnButtonColumn = new TableColumn<>("กดยืนยันการคืน");
        returnButtonColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(null));

        returnButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button buttonApproveReturned = new Button("กดยืนยันการคืน");

            // override the updateItem method to set the button action
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonApproveReturned);
                    buttonApproveReturned.setOnAction(event -> {
                        Borrow borrow = getTableRow().getItem();
                        Product product = productList.searchProductById(borrow.getIdProduct());
                        if(borrow != null && borrow.getStatus().equals("รอยืนยันการคืน")){
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "คุณแน่ใจที่จะกดยืนยันการคืน?", ButtonType.YES, ButtonType.NO);
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.YES){
                                product.setIsBorrow("ว่าง");
                                borrow.setStatus("คืน");
                                product.setLocationProduct("ส่วนกลาง");
                                borrow.setStaffApproveReturned(account.getUsername());
                                product.setHolder(account.getName());
                                borrowListDataSource.writeData(borrowList);
                                productListDataSource.writeData(productList);
                                getTableView().refresh();
                                approveReturnedTable.getItems().remove(borrow);}
                        }

                    });
                }
            }
        });
        approveReturnedTable.getColumns().clear();
        approveReturnedTable.getColumns().add(userNameColumn);
        approveReturnedTable.getColumns().add(idProductColumn);
        approveReturnedTable.getColumns().add(nameColumn);
        approveReturnedTable.getColumns().add(dateColumn);
        approveReturnedTable.getColumns().add(timeColumn);
        approveReturnedTable.getColumns().add(returnButtonColumn);

        approveReturnedTable.getItems().clear();

        for (Borrow borrow : borrowList.getAllBorrow()) {
            if(borrow.getStatus().equals("รอยืนยันการคืน"))
                approveReturnedTable.getItems().add(borrow);
        }
    }
    private void showTableBorrow(BorrowList borrowList){
        TableColumn<Borrow, String> userNameColumn = new TableColumn<>("ชื่อผู้ยืม");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));


        TableColumn<Borrow, String> idProductColumn = new TableColumn<>("หมายเลขครุภัณฑ์");
        idProductColumn.setCellValueFactory(new PropertyValueFactory<>("idProduct"));

        TableColumn<Borrow, String> nameColumn = new TableColumn<>("ชื่อครุภัณฑ์");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));

        TableColumn<Borrow, String> dateColumn = new TableColumn<>("วันที่ยืม");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));

        TableColumn<Borrow, String> timeColumn = new TableColumn<>("เวลาที่ยืม");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("borrowTime"));

        TableColumn<Borrow, Void> borrowButtonColumn = new TableColumn<>("กดยืนยันการยืม");
        borrowButtonColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(null));

        borrowButtonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("กดยืนยันการยืม");

            // override the updateItem method to set the button action
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                    button.setOnAction(event -> {
                        Borrow borrow = getTableRow().getItem();
                        Account borrowAccount = accountList.findAccount(borrow.getUserName());
                        Product product = productList.searchProductById(borrow.getIdProduct());
                        if(borrow != null && borrow.getStatus().equals("รอยืนยันการยืม")){
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "คุณแน่ใจที่ให้ยืมครุภัณฑ์?", ButtonType.YES, ButtonType.NO);
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.YES){
                                borrow.setStatus("ยืม");
                                borrow.setStaffApproveBorrow(account.getUsername());
                                borrowListDataSource.writeData(borrowList);
                                product.setHolder(borrowAccount.getName());
                                productListDataSource.writeData(productList);
                                approveBorrowTable.refresh();
                                approveBorrowTable.getItems().remove(borrow);

                            }

                        }

                    });
                }
            }
        });
        approveBorrowTable.getColumns().clear();
        approveBorrowTable.getColumns().add(userNameColumn);
        approveBorrowTable.getColumns().add(idProductColumn);
        approveBorrowTable.getColumns().add(nameColumn);
        approveBorrowTable.getColumns().add(dateColumn);
        approveBorrowTable.getColumns().add(timeColumn);
        approveBorrowTable.getColumns().add(borrowButtonColumn);

        approveBorrowTable.getItems().clear();

        for (Borrow borrow : borrowList.getAllBorrow()) {
            if(borrow.getStatus().equals("รอยืนยันการยืม"))
                approveBorrowTable.getItems().add(borrow);
        }
    }

    @FXML
    protected void logoutButton() {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void materialButton(ActionEvent event) {
        try {
            FXRouter.goTo("materialList",new PassTo(account));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void productButton(ActionEvent event) {
        try {
            FXRouter.goTo("homeShowProduct",new Lender(account));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void changePassButton() {
        Stage popupStage = new Stage();
        Pane popupContent = new Pane();
        popupContent.setPrefSize(300, 200);
        PasswordField currentPassword = new PasswordField();
        currentPassword.setLayoutX(112);
        currentPassword.setLayoutY(27);
        Label curentLabel = new Label("รหัสปัจจุบัน");
        curentLabel.setLayoutX(23);
        curentLabel.setLayoutY(33);
        PasswordField newPassword = new PasswordField();
        newPassword.setLayoutX(112);
        newPassword.setLayoutY(70);
        Label newPassLabel = new Label("รหัสใหม่");
        newPassLabel.setLayoutX(23);
        newPassLabel.setLayoutY(73);
        PasswordField confirmNewPassword = new PasswordField();
        confirmNewPassword.setLayoutX(112);
        confirmNewPassword.setLayoutY(111);
        Label confirmNewLabel = new Label("ยืนยันรหัสใหม่");
        confirmNewLabel.setLayoutX(23);
        confirmNewLabel.setLayoutY(114);
        Button confirmButton = new Button("ยืนยัน");
        confirmButton.setLayoutX(118);
        confirmButton.setLayoutY(156);
        confirmButton.setOnAction(e -> {
            String current = currentPassword.getText();
            String changeNewPassword = newPassword.getText();
            String confirmPassword = confirmNewPassword.getText();
            if (current.isEmpty()||changeNewPassword.isEmpty()||confirmPassword.isEmpty()) {
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "โปรดกรอกข้อมูลให้ครบ", ButtonType.OK);
                alert1.showAndWait();
                if (alert1.getResult() == ButtonType.OK) {
                    ((Stage) alert1.getDialogPane().getScene().getWindow()).close();
                }
            }
            else if ((!accountList.canLogin(account.getUsername(), current))) {
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "รหัสผ่านไม่ถูกต้อง", ButtonType.OK);
                alert1.showAndWait();
                if (alert1.getResult() == ButtonType.OK) {
                    currentPassword.clear();
                    newPassword.clear();
                    confirmNewPassword.clear();
                    ((Stage) alert1.getDialogPane().getScene().getWindow()).close();
                }
            }
            else if (!(newPassword.getText().equals(confirmNewPassword.getText()))) {
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "รหัสผ่านไม่ตรงกัน", ButtonType.OK);
                alert1.showAndWait();
                if (alert1.getResult() == ButtonType.OK) {
                    newPassword.clear();
                    confirmNewPassword.clear();
                    ((Stage) alert1.getDialogPane().getScene().getWindow()).close();
                }
            }
            else if (current.equals(confirmPassword)) {
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "รหัสผ่านซ้ำ", ButtonType.OK);
                alert1.showAndWait();
                if (alert1.getResult() == ButtonType.OK) {
                    currentPassword.clear();
                    newPassword.clear();
                    confirmNewPassword.clear();
                    ((Stage) alert1.getDialogPane().getScene().getWindow()).close();
                }

            }
            else if ((!account.validPassword(changeNewPassword)) && (!account.validPassword(confirmPassword))) {
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "Password สามารถใส่ได้แค่ A-Z หรือ a-z ยาว 7-10 ตัวอักษร", ButtonType.OK);
                alert1.showAndWait();
                if (alert1.getResult() == ButtonType.OK) {
//                    currentPassword.clear();
                    newPassword.clear();
                    confirmNewPassword.clear();
                    ((Stage) alert1.getDialogPane().getScene().getWindow()).close();
                }
            }
            else {
                account.setPassword(changeNewPassword);
                accountListDataSource.writeData(accountList);
//                account = accountList.changePassword();
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "เปลี่ยนรหัสผ่านสำเร็จ", ButtonType.OK);
                alert1.showAndWait();
                if (alert1.getResult() == ButtonType.OK) {
                    ((Stage) alert1.getDialogPane().getScene().getWindow()).close();
                    popupStage.close();
                }
            }
            popupStage.close();
        });

        //add children
        popupContent.getChildren().add(curentLabel);
        popupContent.getChildren().add(newPassLabel);
        popupContent.getChildren().add(confirmNewLabel);
        popupContent.getChildren().add(currentPassword);
        popupContent.getChildren().add(newPassword);
        popupContent.getChildren().add(confirmNewPassword);
        popupContent.getChildren().add(confirmButton);


        // Create the scene with the popup content
        Scene popupScene = new Scene(popupContent);
        popupStage.setScene(popupScene);

        // Set the modality of the popup window
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // Show the popup window
        popupStage.show();

    }

    @FXML
    void createProductButton(ActionEvent event) {
        try {
            FXRouter.goTo("addProduct",account);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
