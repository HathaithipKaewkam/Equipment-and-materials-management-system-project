package ku.project.controllers;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import ku.project.models.account.Account;
import ku.project.models.account.AccountList;
import ku.project.services.AccountDataSource;
import ku.project.services.FXRouter;
import ku.project.services.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class NewstaffController {

    private Account account;

    private AccountList accountList;

    private AccountDataSource staffDataSource;

    private File fileImage;

    private Alert alert;

    private String imagePath;

    @FXML
    private PasswordField confirmPasswordStaff;

    @FXML
    private TextField nameStaff;

    @FXML
    private PasswordField passwordStaff;

    @FXML
    private ImageView profileStaff;

    @FXML
    private TextField usernameStaff;

    @FXML
    public void initialize() {
        account = new Account();
        staffDataSource = new AccountDataSource("database", "account.csv");
        accountList = staffDataSource.readData();
    }

    @FXML
    void backMainAdminButton(ActionEvent event) {
        try {
            FXRouter.goTo("adminMain");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void createNewAccountButton(ActionEvent event) {
        String name = nameStaff.getText();
        String username = usernameStaff.getText();
        String password = passwordStaff.getText();
        String confirmPassword = confirmPasswordStaff.getText();
        alert = new Alert(Alert.AlertType.NONE);
        if (nameStaff.getText().isEmpty() || usernameStaff.getText().isEmpty() || passwordStaff.getText().isEmpty() || confirmPasswordStaff.getText().isEmpty()) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("โปรดกรอกข้อมูลให้ครบถ้วน");
            alert.show();
            clear();
        } else if  ((!account.validUsername(usernameStaff.getText()))) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Username สามารถใส่ได้แค่ A-Z หรือ a-z ยาว 7-10 ตัวอักษร");
            alert.show();
            clear();
        } else if  ((!account.validPassword(passwordStaff.getText()))) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Password สามารถใส่ได้แค่ A-Z หรือ a-z ยาว 6-10 ตัวอักษร");
            alert.show();
            clear();
        } else if (!(passwordStaff.getText().equals(confirmPassword))) {
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
                staffDataSource = new AccountDataSource("database", "account.csv");
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
                        profileStaff.setImage(new Image(target.toUri().toString()));
                        imagePath = filename;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (imagePath == null) {
                    Account account = new Account("Staff", name, username, password, "profile-user.png", "never", "never");
                    accountList.addAccount(account);
                    staffDataSource.writeData(accountList);
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("สร้างบัญชีสำหรับเจ้าหน้าที่สำเร็จแล้ว");
                    alert.show();
                } else {
                    File dest = new File("image/profiles" + imagePath);
                    accountList.addAccount(new Account("Staff", name, username, password, imagePath, "never", "never"));
                    staffDataSource.writeData(accountList);
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("สร้างบัญชีสำหรับเจ้าหน้าที่สำเร็จแล้ว");
                    alert.show();

                }
            }
        }
    }

    public void clear(){
        nameStaff.clear();
        usernameStaff.clear();
        passwordStaff.clear();
        confirmPasswordStaff.clear();
    }

    @FXML
    void editProfileButton(ActionEvent event) {
        String directory = "image/profiles";
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG JPG image", "*.png", "*.jpg", "*.jpeg"));
        Node source = (Node) event.getSource();
        fileImage = chooser.showOpenDialog(source.getScene().getWindow());
        if (fileImage != null) {
            Image image = FileService.handleUploadPicture(fileImage, account, directory);
            profileStaff.setImage(image);
            staffDataSource.writeData(accountList);
            profileStaff.setImage(setup(new Image(fileImage.getAbsolutePath())));

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



