package ku.project.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.project.models.account.Account;
import ku.project.models.account.AccountList;
import ku.project.models.material.Category;
import ku.project.models.material.CategoryList;
import ku.project.models.products.Product;
import ku.project.services.*;

import java.io.File;
import java.io.IOException;

public class MainAdminController {

        @FXML
        private Label nameLabel;

        @FXML
        private Label timeLabel;

        @FXML
        private ListView<Account> userAccountListview;

        @FXML
        private Label userName;

        @FXML
        private Label dateLabel;

        @FXML
        private ImageView userProfile;

        @FXML
        private Label imagePath;

        private AccountList accountList;
        private Account selectedAccount;
        private DataSource<CategoryList> categoryListDataSource = new CategoryDataSource();
        ;
        private DataSource<AccountList> datasource;
        private Alert alert;

        private CategoryList categoryList = categoryListDataSource.readData();


        @FXML
        public void initialize() {
//        welcomeText.setText("Hello JavaFX");
                Image image = new Image(getClass().getResource("/images/newuser.png").toString());  // แบบที่ 1
                // Image image = new Image(getClass().getResourceAsStream("/images/fish.png"));  // แบบที่ 2
//                userProfile.setImage(image);
                clearAccountInfo();
                userProfile.setImage(image);
                datasource = new AccountDataSource("database", "account.csv");
                accountList = datasource.readData();
                showList(accountList);


                userAccountListview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Account>() {
                        @Override
                        public void changed(ObservableValue<? extends Account> observable, Account oldValue, Account newValue) {
                                if (newValue == null) {
                                        clearAccountInfo();
                                        selectedAccount = null;
                                } else {
                                        selectedAccount = newValue;
                                        showAccountInfo(newValue);


                                }
                        }

                });
        }

        private void showList(AccountList accountList) {
                userAccountListview.getItems().clear();
                accountList.sort(new AccountDateTimeComparator().reversed());
                for (Account account : accountList.getAllAccount()) {
                        if (!account.getRole().equals("Admin")){
                                userAccountListview.getItems().add(account);
                        }
                }
//                userAccountListview.getItems().addAll(accountList.getAllAccount());
        }
        private void clearAccountInfo() {
                nameLabel.setText("");
                userName.setText("");
                timeLabel.setText("");
                dateLabel.setText("");



        }

        private void showAccountInfo(Account account) {
                if (selectedAccount==null)
                        return;
                nameLabel.setText(account.getName());
                userName.setText(account.getUsername());
                timeLabel.setText(account.getLoginTime());
                dateLabel.setText(account.getLoginDate());
                File desDir = new File(System.getProperty("user.dir")+System.getProperty("file.separator") + "image" + System.getProperty("file.separator")
                        + "profiles" + System.getProperty("file.separator") + selectedAccount.getFilePictureName());

                Image image = new Image(String.valueOf(desDir.toURI()));
                userProfile.setImage(image);
//
        }

        @FXML
        protected void createAccountStaffButton() {
                try {
                        FXRouter.goTo("newstaff");
                } catch (IOException e) {
                        throw new RuntimeException(e);
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
        void newCategoryButton(ActionEvent event) {
// Create the popup window
                Stage popupStage = new Stage();

                // Create the content for the popup
                Pane popupContent = new Pane();
                popupContent.setPrefSize(300, 150);
                Label label = new Label("เพิ่มหมวดหมู่");
                popupContent.getChildren().add(label);
                label.setLayoutY(27);
                label.setLayoutX(26);
                Label label1 = new Label("เลือกประเภท");
                label1.setLayoutY(50);
                label1.setLayoutX(26);
                ComboBox comboBoxType = new ComboBox<>();
                comboBoxType.setLayoutX(134);
                comboBoxType.setLayoutY(50);
                comboBoxType.getItems().add("ครุภัณฑ์");
                comboBoxType.getItems().add("วัสดุ");
                TextField categoryTextField = new TextField();
                categoryTextField.setLayoutY(22);
                categoryTextField.setLayoutX(134);
                popupContent.getChildren().add(categoryTextField);
                Button confirmButton = new Button("กดเพิ่ม");
                confirmButton.setLayoutY(100);
                confirmButton.setLayoutX(120);
                alert = new Alert(Alert.AlertType.NONE);
                confirmButton.setOnAction(e -> {
                        String categoryAddNew = categoryTextField.getText();
                        String type = (String) comboBoxType.getValue();
                        if (categoryTextField.getText().isEmpty()||comboBoxType.getValue().equals(null)){
                                alert.setAlertType(Alert.AlertType.WARNING);
                                alert.setContentText("โปรดกรอกข้อมูลให้ครบถ้วน");
                                alert.show();


                        }
                        else if(!categoryList.ExistNameCategory(categoryAddNew)){
                                alert.setAlertType(Alert.AlertType.WARNING);
                                alert.setContentText("มีหมวดหมู่นี้แล้ว");
                                alert.show();
                                categoryTextField.clear();
                        }
                        else {
                                categoryList.addCategory(new Category(categoryAddNew,type));
                                categoryListDataSource.writeData(categoryList);
                                alert.setAlertType(Alert.AlertType.WARNING);
                                alert.setContentText("สร้างหมวดหมู่วัสดุสำเร็จแล้ว");
                                alert.show();
                                popupStage.close();

                        }


                });
                popupContent.getChildren().add(confirmButton);
                popupContent.getChildren().add(label1);
                popupContent.getChildren().add(comboBoxType);




                // Create the scene with the popup content
                Scene popupScene = new Scene(popupContent);
                popupStage.setScene(popupScene);

                // Set the modality of the popup window
                popupStage.initModality(Modality.APPLICATION_MODAL);

                // Show the popup window
                popupStage.show();
        }

        @FXML
        protected void resetPasswordButton() {
                try {
                        FXRouter.goTo("password");
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        }
}
