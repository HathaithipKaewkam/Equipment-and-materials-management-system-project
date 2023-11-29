package ku.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.project.models.account.Account;
import ku.project.models.account.AccountList;
import ku.project.models.material.*;
import ku.project.services.*;

import java.io.IOException;

public class RequisitionMaterialController {

    @FXML
    private Spinner<Integer> requisitionAmount;
    SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
    @FXML
    private TableView<History> historyAddTable;

    @FXML
    private TableView<History> historyRequisitionTable;


    @FXML
    private Label categoryLabel;


    @FXML
    private Label nameLabel;


    @FXML
    private TextField nameUser;

    @FXML
    private Label propertyLabel;



    @FXML
    private Label totalLabel;

    PassTo passTo = (PassTo) FXRouter.getData();
    private Material material = passTo.getMaterial();
    private Account account = passTo.getAccount();
    private DataSource<HistoryList> requisitionListDataSource = new HistoryDataSource("database", "HistoryMaterial.csv");
    private DataSource<MaterialList> materialSource = new MaterialFileDataSource("database", "material.csv");
    ;
    private DataSource<AccountList> accountListDataSource = new AccountDataSource("database", "account.csv");
    private HistoryList historyList = requisitionListDataSource.readData();

    private History requisition;

    private MaterialList materialList = materialSource.readData();

    private AccountList accountList = accountListDataSource.readData();

    private Alert alert;


    @FXML
    public void initialize() {


        svf.setValue(1);
        requisitionAmount.setValueFactory(svf);
        showMaterial(material);
        showTableHistoryAddMaterial(historyList);
        ShowTableHistoryRequisitionTable(historyList);


    }
    private void showMaterial(Material material) {
        nameLabel.setText(material.getName());
        categoryLabel.setText(material.getCategory());
        totalLabel.setText("" + material.getAmount());
        propertyLabel.setText(material.getProperty());

    }


    private void showTableHistoryAddMaterial(HistoryList historyList) {
        TableColumn<History, String> nameAddColumn = new TableColumn<>("เพิ่มโดย");
        nameAddColumn.setCellValueFactory(new PropertyValueFactory<>("nameAccount"));

        TableColumn<History, String> nameMaterialColumn = new TableColumn<>("ชื่อ");
        nameMaterialColumn.setCellValueFactory(new PropertyValueFactory<>("nameMaterial"));

        TableColumn<History, String> categoryColumn = new TableColumn<>("หมวดหมู่");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<History, Integer> amountColumn = new TableColumn<>("จำนวนที่เพิ่ม");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<History, String> addDateColumn = new TableColumn<>("วันที่เพิ่ม");
        addDateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));

        TableColumn<History, String> addTimeColumn = new TableColumn<>("เวลาที่เพิ่ม");
        addTimeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));



        // ล้าง column เดิมทั้งหมดที่มีอยู่ใน table แล้วเพิ่ม column ใหม่
        historyAddTable.getColumns().clear();
        historyAddTable.getColumns().add(nameAddColumn);
        historyAddTable.getColumns().add(nameMaterialColumn);
        historyAddTable.getColumns().add(categoryColumn);
        historyAddTable.getColumns().add(amountColumn);
        historyAddTable.getColumns().add(addDateColumn);
        historyAddTable.getColumns().add(addTimeColumn);


        historyAddTable.getItems().clear();

        // ใส่ข้อมูล Material ทั้งหมดจาก MaterialList ไปแสดงใน TableView
        for (History addMaterial : historyList.getAllRequisition()) {
            if(addMaterial.getNameMaterial().equals(material.getName())){
                if(addMaterial.getStatus().equals("เพิ่ม"))
                    historyAddTable.getItems().add(addMaterial);
            }}
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
            if(requisition.getNameMaterial().equals(material.getName())){
                if(requisition.getStatus().equals("เบิก"))
                    historyRequisitionTable.getItems().add(requisition);
            }}
    }

    @FXML
    void addAmountButton(ActionEvent event) {
        String name = nameUser.getText();
        int i = requisitionAmount.getValue();
        alert = new Alert(Alert.AlertType.NONE);
        if (name.isEmpty()) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("โปรดกรอกข้อมูลให้ครบถ้วน");
            alert.show();
        } else if (!accountList.ExistUsername(name)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("ไม่มีชื่อนี้ในระบบ");
            alert.show();

        } if (i>material.getAmount()) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("ไม่สามารถเบิกเกินจำนวนได้");
            alert.show();

        } else {
            requisition = new History(name, material.getName(), material.getCategory(),i, "เบิก");
            historyList.addRequisition(requisition);
            for (Material material1 : materialList.getMaterials()) {
                if (material1.isName(requisition.getNameMaterial())) {
                    material1.setAmount(material1.getAmount() - requisition.getAmount());
                }
            }
            materialSource.writeData(materialList);
            requisitionListDataSource.writeData(historyList);
            materialList = materialSource.readData();
            showMaterial(materialList.findMaterialByName(material.getName()));
            ShowTableHistoryRequisitionTable(historyList);

        }

    }
    @FXML
    void backButton(ActionEvent event) {
        try {
            FXRouter.goTo("materialList");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
