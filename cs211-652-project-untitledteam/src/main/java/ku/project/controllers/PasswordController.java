package ku.project.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import ku.project.models.account.Account;
import ku.project.models.account.AccountList;
import ku.project.services.AccountDataSource;
import ku.project.services.DataSource;
import ku.project.services.FXRouter;

import java.io.File;
import java.io.IOException;

public class PasswordController {


    @FXML
    private PasswordField confirmPassword;

    @FXML
    private PasswordField currentPassword;

    @FXML
    private PasswordField newPassword;

    @FXML
    private ImageView passwordImage;

    @FXML
    private TextField usernameTextField;

    private Account account;

    private AccountList accountList;

    private AccountDataSource userDataSource;
    private Alert alert;


    @FXML
    public void initialize() {
        //Image image = new Image(getClass().getResource("/images/password.png").toString());  // แบบที่ 1
        //passwordImage.setImage(image);
        account = new Account();
        alert = new Alert(Alert.AlertType.NONE);
        userDataSource = new AccountDataSource("database", "account.csv");
        accountList = userDataSource.readData();

    }

    @FXML
    void backLoginButton(ActionEvent event) {
        try {
            FXRouter.goTo("login", accountList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updatePassWordButton(ActionEvent event) {
        String username = usernameTextField.getText();
        String accountCurrentPassword = currentPassword.getText();
        String accountNewPassword = newPassword.getText();
        String accountConfirmPassword = confirmPassword.getText();
        if ((!accountList.ExistUsername(username))){
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("ไม่มีชื่อผู้ใช้งานนี้อยู่ในระบบ");
            alert.show();
            clear();
        } else if (usernameTextField.getText().isEmpty() || currentPassword.getText().isEmpty() || newPassword.getText().isEmpty() || confirmPassword.getText().isEmpty()) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("โปรดกรอกข้อมูลให้ครบ");
            alert.show();
            clear();
        } else if ((!accountList.canLogin(username, accountCurrentPassword))) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("รหัสผ่านไม่ถูกต้อง");
            alert.show();
            clear();
        } else if (!(newPassword.getText().equals(confirmPassword.getText()))) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("รหัสผ่านไม่ตรงกัน");
            alert.show();
            clear();
        } else if (currentPassword.equals(confirmPassword)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("รหัสผ่านซ้ำ");
            alert.show();
            clear();
        } else if ((!account.validPassword(accountNewPassword)) && (!account.validPassword(accountConfirmPassword))) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Password สามารถใส่ได้แค่ A-Z หรือ a-z ยาว 6-10 ตัวอักษร");
            alert.show();
        } else {
            account = accountList.changePassword(username);
            account.setPassword(accountNewPassword);
            userDataSource.writeData(accountList);
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("เปลี่ยนรหัสผ่านสำเร็จ");
            alert.show();
            clear();
        }

    }


    public void clear(){
        confirmPassword.clear();
        newPassword.clear();
        confirmPassword.clear();
        usernameTextField.clear();
        currentPassword.clear();
    }


}
