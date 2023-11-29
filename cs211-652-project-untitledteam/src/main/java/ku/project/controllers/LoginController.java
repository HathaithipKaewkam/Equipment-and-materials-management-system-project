package ku.project.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import ku.project.models.account.Account;
import ku.project.models.account.AccountList;
import ku.project.models.products.Lender;
import ku.project.services.AccountDataSource;
import ku.project.services.DataSource;
import ku.project.services.FXRouter;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {

    public static String getUsername;
    private DataSource<AccountList> dataSource = new AccountDataSource();
    private AccountList accountList = dataSource.readData();
    @FXML
    private JFXButton howApp;

    @FXML
    private PasswordField passwordLogin;

    @FXML
    private TextField userLogin;


    private Alert alert;
//    @FXML
//    private
    @FXML
    private ComboBox themeComboBox;
    @FXML
    public void initialize(){
        dataSource = new AccountDataSource("database", "account.csv");
        accountList = dataSource.readData();
        themeComboBox.getItems().add("Light Mode");
        themeComboBox.getItems().add("Dark Mode");




    }



    @FXML
    void selectThemeComboBox(ActionEvent event) {
        String categorySelect = themeComboBox.getSelectionModel().getSelectedItem().toString();
        if(categorySelect.equals("Dark Mode")){
            try{
                FXRouter.setPathCss("/ku/project/styles/darkMode.css");
                FXRouter.goTo("login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(categorySelect.equals("Light Mode")){
            try{
                FXRouter.setPathCss("/ku/project/styles/lightMode.css");
                FXRouter.goTo("login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void buttonHowApp() {
        try {
            FXRouter.goTo("howToUse");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void creditButton() {
        try {
            FXRouter.goTo("untitledTeam");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void loginButton(ActionEvent event) {
        String username = userLogin.getText();
        String password = passwordLogin.getText();
        Alert alert = new Alert(Alert.AlertType.NONE);
        if (userLogin.getText().isEmpty() || passwordLogin.getText().isEmpty()) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Please enter username and password");
            alert.show();
        } else if (!accountList.checkUsername(username)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Incorrect username");
            alert.show();
//            wrongLogin.setText("Incorrect username ");
//            wrongLogin.setStyle("-fx-text-fill: #ff0000");
        } else {
            Account account = accountList.findAccount(userLogin.getText());
            if (!account.isPassword(passwordLogin.getText())) {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("Incorrect password");
                alert.show();
//                wrongLogin.setText("Incorrect password ");
//                wrongLogin.setStyle("-fx-text-fill: #ff0000");
            } else {
                account.initialLoginTime();
                dataSource.writeData(accountList);
                if ("Admin".equals(account.getRole())) {
                    try {
                        FXRouter.goTo("adminMain", account.getUsername());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if ("Staff".equals(account.getRole())) {
                    try {
                        FXRouter.goTo("staffMain", account);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if ("User".equals(account.getRole())) {
                    try {
                        FXRouter.goTo("homeShowProduct", new Lender(accountList.findAccount(username)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }





    @FXML
    private ImageView userProfile;


    @FXML
    protected void registerButton() {
        try {
            FXRouter.goTo("register", accountList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void passwordForgotButton(){
        try {
            FXRouter.goTo("password", accountList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


