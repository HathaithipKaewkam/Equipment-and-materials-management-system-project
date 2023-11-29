package ku.project.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import ku.project.models.account.Account;
import ku.project.models.account.AccountList;
import ku.project.services.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class RegisterController {

    private Account account;

    private AccountList accountList;

    private AccountDataSource userDataSource;

    private File fileImage;

    private String imagePath;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField nameUser;

    @FXML
    private TextField usernameUser;

    @FXML
    private PasswordField passwordUser;

    @FXML
    private PasswordField confirmPasswordUser;
    private Alert alert;

    @FXML
    public void initialize() {
        account = new Account();
        userDataSource = new AccountDataSource("database", "account.csv");
        accountList = userDataSource.readData();


    }

    @FXML
    public void editProfileButton(ActionEvent event) {
        String directory = "image/profiles";
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG JPG image", "*.png", "*.jpg", "*.jpeg"));
        Node source = (Node) event.getSource();
        fileImage = chooser.showOpenDialog(source.getScene().getWindow());
        if (fileImage != null) {
            Image image = FileService.handleUploadPicture(fileImage, account, directory);
            imageView.setImage(image);
            userDataSource.writeData(accountList);
            imageView.setImage(setup(new Image(fileImage.getAbsolutePath())));
        }
    }


    @FXML
    void SignUpButton(ActionEvent event) {
        String name = nameUser.getText();
        String username = usernameUser.getText();
        String password = passwordUser.getText();
        String confirmPassword = confirmPasswordUser.getText();
        alert = new Alert(Alert.AlertType.NONE);
        if (nameUser.getText().isEmpty() || usernameUser.getText().isEmpty() || passwordUser.getText().isEmpty() || confirmPasswordUser.getText().isEmpty()) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("โปรดกรอกข้อมูลให้ครบถ้วน");
            alert.show();
            clear();
        } else if  (!(account.validUsername(usernameUser.getText()))) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Username สามารถใส่ได้แค่ A-Z หรือ a-z ยาว 7-10 ตัวอักษร");
            alert.show();
            clear();
        } else if  ((!account.validPassword(passwordUser.getText()))) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Password สามารถใส่ได้แค่ A-Z หรือ a-z ยาว 6-10 ตัวอักษร");
            alert.show();
            clear();
        } else if (!(passwordUser.getText().equals(confirmPassword))) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("โปรดกรอกรหัสผ่านให้ตรงกัน");
            alert.show();
            clear();

        } else {
            if (accountList.ExistUsername(username)) {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("มีผู้ใช้งานชื่อนี้ในระบบแล้ว");
                alert.show();
            } else {
                userDataSource = new AccountDataSource("database", "account.csv");
                account = accountList.searchAccountByUsername(account.getUsername());
                if (fileImage != null) {
                    try {
                        File destDir = new File("image/profiles");
                        if (!destDir.exists()) destDir.mkdirs();
                        String[] fileSplit = fileImage.getName().split("\\.");
                        String filename = LocalDate.now() + "_" + System.currentTimeMillis() + "."
                                + fileSplit[fileSplit.length - 1];
                        Path target = FileSystems.getDefault().getPath(
                                destDir.getAbsolutePath() + System.getProperty("file.separator") + filename
                        );
                        Files.copy(fileImage.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                        imageView.setImage(new Image(target.toUri().toString()));
                        imagePath = filename;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (imagePath == null) {
                    Account account = new Account("User", name, username, password, "profile-user.png", "never", "never");
                    accountList.addAccount(account);
                } else {
                    File dest = new File("image/profiles" + imagePath);
                    accountList.addAccount(new Account("User", name, username, password, imagePath, "never", "never"));
                }
                try {
                    userDataSource.writeData(accountList);
                    FXRouter.goTo("login");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void clear(){
        nameUser.clear();
        usernameUser.clear();
        passwordUser.clear();
        confirmPasswordUser.clear();
    }



    @FXML
    void backLoginButton(ActionEvent event) {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public WritableImage setup(Image image){
        PixelReader reader = image.getPixelReader();
        int newMeasure = (int)Math.min(image.getWidth(), image.getHeight());
        int x = (int)(image.getWidth() - newMeasure) / 2;
        int y = (int)(image.getHeight() - newMeasure) / 2;
        WritableImage newImage = new WritableImage(reader, x, y, newMeasure, newMeasure);
        return newImage ;
    }
}
