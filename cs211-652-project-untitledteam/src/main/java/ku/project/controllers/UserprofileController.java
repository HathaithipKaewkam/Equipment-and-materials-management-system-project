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
import ku.project.models.material.History;
import ku.project.models.material.HistoryList;
import ku.project.models.products.Lender;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserprofileController {

    @FXML private TableView historyTable;
    @FXML private TableView borrowTable;
    @FXML private TableView historyRequisitionTable;


    private DataSource<AccountList> accountListDataSource;
    private DataSource<BorrowList> borrowListDataSource;
    private DataSource<HistoryList> historyListDataSource;
    private AccountList accountList;

    private Lender lender = (Lender) FXRouter.getData();
    protected Account account = lender.getBorrower();
    Alert alert;
    @FXML
    private Label userName;

    @FXML
    private ImageView userPicture;
    private BorrowList borrowHistoryList;
    private BorrowList borrowList;
    private HistoryList historyList;

    public void initialize() {
        readData();
        userName.setText(account.getUsername());
        File desDir = new File("image" + System.getProperty("file.separator")
                + "profiles" + System.getProperty("file.separator") + account.getFilePictureName());
        Image image = new Image(String.valueOf(desDir.toURI()), 500, 0, true, true);
        userPicture.setImage(image);
        showTableHistory(borrowHistoryList);
        showTableBorrow(borrowList);
        ShowTableHistoryRequisitionTable(historyList);
    }
    private void readData() {
        accountListDataSource = new AccountDataSource();
        borrowListDataSource = new BorrowDataSource();
        historyListDataSource = new HistoryDataSource("database","HistoryMaterial.csv");
        accountList = accountListDataSource.readData();
        account = accountList.searchAccountByUsername(account.getUsername());
        borrowHistoryList = borrowListDataSource.readData();
        borrowList = borrowListDataSource.readData();
        historyList = historyListDataSource.readData();
    }

    @FXML
    void changePassButton(ActionEvent event) {
//        try {
//            FXRouter.goTo("password", accountList);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
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
                popupStage.close();


            }
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
                userPicture.setImage(new Image(target.toUri().toString()));
                account.setImagePath(filename);
                accountListDataSource.writeData(accountList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void ShowTableHistoryRequisitionTable(HistoryList historyList){
        TableColumn<History, String> nameAddColumn = new TableColumn<>("เบิกโดย");
        nameAddColumn.setCellValueFactory(new PropertyValueFactory<>("nameAccount"));

        TableColumn<History, String> nameMaterialColumn = new TableColumn<>("ชื่อ");
        nameMaterialColumn.setCellValueFactory(new PropertyValueFactory<>("nameMaterial"));

        TableColumn<History, String> categoryColumn = new TableColumn<>("หมวดหมู่");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<History, Integer> amountColumn = new TableColumn<>("จำนวนที่เบิก");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<History, String> addDateColumn = new TableColumn<>("วันที่เบิก");
        addDateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));

        TableColumn<History, String> addTimeColumn = new TableColumn<>("เวลาที่เบิก");
        addTimeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));



        // ล้าง column เดิมทั้งหมดที่มีอยู่ใน table แล้วเพิ่ม column ใหม่
        historyRequisitionTable.getColumns().clear();
        historyRequisitionTable.getColumns().add(nameAddColumn);
        historyRequisitionTable.getColumns().add(nameMaterialColumn);
        historyRequisitionTable.getColumns().add(categoryColumn);
        historyRequisitionTable.getColumns().add(amountColumn);
        historyRequisitionTable.getColumns().add(addDateColumn);
        historyRequisitionTable.getColumns().add(addTimeColumn);



        historyRequisitionTable.getItems().clear();

        // ใส่ข้อมูล Material ทั้งหมดจาก MaterialList ไปแสดงใน TableView
        for (History requisition : historyList.getAllRequisition()) {
                if(requisition.getNameAccount().equals(account.getUsername()))
                    historyRequisitionTable.getItems().add(requisition);
            }
    }
    private void showTableHistory(BorrowList borrowList){
        TableColumn<Borrow, Integer> indexColumn = new TableColumn<>("NO.");
        indexColumn.setSortable(false);
        indexColumn.setCellValueFactory(column -> {
            return new ReadOnlyObjectWrapper<Integer>(historyTable.getItems().indexOf(column.getValue()) + 1);
        });
        TableColumn<Borrow, String> idProductColumn = new TableColumn<>("หมายเลขครุภัณฑ์");
        idProductColumn.setCellValueFactory(new PropertyValueFactory<>("idProduct"));

        TableColumn<Borrow, String> nameColumn = new TableColumn<>("ชื่อครุภัณฑ์");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));

        TableColumn<Borrow, String> categoryColumn = new TableColumn<>("หมวดหมู่");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Borrow, String> dateBorrowColumn = new TableColumn<>("วันที่ยืม");
        dateBorrowColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));

        TableColumn<Borrow, String> timeBorrowColumn = new TableColumn<>("เวลาที่ยืม");
        timeBorrowColumn.setCellValueFactory(new PropertyValueFactory<>("borrowTime"));

        TableColumn<Borrow, String> dateReturnedColumn = new TableColumn<>("วันที่คืน");
        dateReturnedColumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));

        TableColumn<Borrow, String> timeReturnedColumn = new TableColumn<>("เวลาที่คืน");
        timeReturnedColumn.setCellValueFactory(new PropertyValueFactory<>("returnedTime"));

        historyTable.getColumns().clear();
        historyTable.getColumns().add(0, indexColumn);
        historyTable.getColumns().add(idProductColumn);
        historyTable.getColumns().add(nameColumn);
        historyTable.getColumns().add(categoryColumn);
        historyTable.getColumns().add(dateBorrowColumn);
        historyTable.getColumns().add(timeBorrowColumn);
        historyTable.getColumns().add(dateReturnedColumn);
        historyTable.getColumns().add(timeReturnedColumn);


        historyTable.getItems().clear();

        for (Borrow borrow : borrowList.getAllBorrow()) {
            if(account.getUsername().equals(borrow.getUserName())) {
                if (borrow.getStatus().equals("คืน"))
                    historyTable.getItems().add(borrow);
            }
        }
    }
    private void showTableBorrow(BorrowList borrowList){
        TableColumn<Borrow, Integer> indexColumn = new TableColumn<>("NO.");
        indexColumn.setSortable(false);
        indexColumn.setCellValueFactory(column -> {
            return new ReadOnlyObjectWrapper<Integer>(borrowTable.getItems().indexOf(column.getValue()) + 1);
        });

        TableColumn<Borrow, String> idProductColumn = new TableColumn<>("หมายเลขครุภัณฑ์");
        idProductColumn.setCellValueFactory(new PropertyValueFactory<>("idProduct"));

        TableColumn<Borrow, String> nameColumn = new TableColumn<>("ชื่อครุภัณฑ์");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));

        TableColumn<Borrow, String> categoryColumn = new TableColumn<>("หมวดหมู่");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Borrow, String> dateColumn = new TableColumn<>("วันที่ยืม");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));

        TableColumn<Borrow, String> timeColumn = new TableColumn<>("เวลาที่ยืม");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("borrowTime"));

        TableColumn<Borrow, Void> buttonReturnedColumn = new TableColumn<>("กดคืน");
        buttonReturnedColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(null));

        TableColumn<Borrow, String> statusColumn = new TableColumn<>("สถานะ");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

// create the cell factory that contains the button
        buttonReturnedColumn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("ปุ่มกดคืน");

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
                            if(borrow != null && borrow.getStatus().equals("ยืม")){
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "แน่ใจว่าคุณต้องการจะคืนครุภัณฑ์?", ButtonType.YES, ButtonType.NO);
                                alert.showAndWait();
                                if (alert.getResult() == ButtonType.YES){
                                    borrow.setStatus("รอยืนยันการคืน");
                                    LocalDateTime localDateTime = LocalDateTime.now();
                                    String borrowDateTime = localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
                                    String[] time = borrowDateTime.split(" ");
                                    String Date= time[0];
                                    String Time = time[1];
                                    borrow.setReturnedDate(Date);
                                    borrow.setReturnedTime(Time);
                                    borrowListDataSource.writeData(borrowList);
                                    getTableView().refresh();
                                }

                            }


                    });
                }
            }
        });



        borrowTable.getColumns().clear();
        borrowTable.getColumns().add(0, indexColumn);
        borrowTable.getColumns().add(idProductColumn);
        borrowTable.getColumns().add(nameColumn);
        borrowTable.getColumns().add(categoryColumn);
        borrowTable.getColumns().add(dateColumn);
        borrowTable.getColumns().add(timeColumn);
        borrowTable.getColumns().add(buttonReturnedColumn);
        borrowTable.getColumns().add(statusColumn);

        borrowTable.getItems().clear();

        for (Borrow borrow : borrowList.getAllBorrow()) {
            if(account.getUsername().equals(borrow.getUserName())) {
                if(!borrow.getStatus().equals("คืน"))
                borrowTable.getItems().add(borrow);
            }
        }
    }

    @FXML
        void backtoMain(ActionEvent event) {
            try {
                FXRouter.goTo("homeShowProduct",lender);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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


    }
