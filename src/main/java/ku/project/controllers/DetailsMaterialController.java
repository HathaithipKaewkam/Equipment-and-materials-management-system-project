package ku.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.project.models.account.Account;
import ku.project.models.material.*;
import ku.project.services.DataSource;
import ku.project.services.FXRouter;
import ku.project.services.MaterialFileDataSource;
import ku.project.services.HistoryDataSource;

import java.io.IOException;

public class DetailsMaterialController {

    @FXML
    private TableView<History>historyAddTable;

    @FXML
    private  TableView<History>historyRequisitionTable;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label errorLabel;
    @FXML
    private TextField amountTextField;

    @FXML
    private Label propertyLabel;

    @FXML
    private Label totalLabel;

    PassTo passTo = (PassTo) FXRouter.getData();
    private Material material = passTo.getMaterial();
    private Account account = passTo.getAccount();
    private DataSource<MaterialList> materialListDataSource;
    private DataSource<HistoryList> historyListListDataSource;
    private MaterialList materialList;
    private HistoryList historyList;
    private History requisition;
    @FXML
    public void initialize() {
        clearMaterialInfo();
        materialListDataSource = new MaterialFileDataSource("database", "material.csv");
        historyListListDataSource = new HistoryDataSource("database","HistoryMaterial.csv");

        materialList = materialListDataSource.readData();
        historyList = historyListListDataSource.readData();

        // รับข้อมูล studentId จากหน้าอื่น ผ่าน method FXRouter.getData()
        // โดยจำเป็นต้อง casting data type ให้ตรงกับหน้าที่ส่งข้อมูล
//        String materialName = (String) FXRouter.getData();
//        material =  materialList.findMaterialByName(materialName);


        showMaterial(material);
//        showList(materialList);
        showTableHistoryAddMaterial(historyList);
        ShowTableHistoryRequisitionTable(historyList);

        errorLabel.setText("");

//        historyAddMaterial.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Requisition>() {
//            @Override
//            public void changed(ObservableValue<? extends Requisition> observable, Requisition oldValue, Requisition newValue) {
//                if (newValue == null) {
//                    clearMaterialInfo();
//                } else {
//                    showMaterialInfo(newValue);
//                }
//            }
//        });
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
    private void clearMaterialInfo() {
        nameLabel.setText("");
        categoryLabel.setText("");
        totalLabel.setText("");
        propertyLabel.setText("");
    }



    @FXML
    void addAmountButton(ActionEvent event) {
//        material.initialAddMaterialTime();
        String amountString = amountTextField.getText().trim();
        if (amountString.equals("")) {
            errorLabel.setText("amount is required");
            return;
        }
        try {
            int amount = Integer.parseInt((amountString));
            if (amount < 0) {
                errorLabel.setText("amount must be positive number");
                return;
            }
            errorLabel.setText("");
//            materialList.giveAddAmountToAmount(material.getName(),amount);
//            materialList.giveAmountToMaterial(material.getName(),amount);
            requisition = new History(account.getName(), material.getName(),material.getCategory(),amount,"เพิ่ม");
            for(Material material1: materialList.getMaterials()){
                if(material1.isName(requisition.getNameMaterial())){
                    material1.setAmount(material1.getAmount()+requisition.getAmount());
                }
            }
            materialListDataSource.writeData(materialList);
            historyList.addRequisition(requisition);
            amountTextField.clear();
            historyListListDataSource.writeData(historyList);
            materialList = materialListDataSource.readData();
            showMaterial(materialList.findMaterialByName(material.getName()));
            showTableHistoryAddMaterial(historyList);
        } catch (NumberFormatException e) {
            errorLabel.setText("amount must be number");
        }

    }
//    @FXML
//    void decreaseAmountButton(ActionEvent event) {
//        String amountString = amountTextField.getText().trim();
//        if (amountString.equals("")) {
//            errorLabel.setText("amount is required");
//            return;
//        }
//        try {
//            int amount = Integer.parseInt((amountString));
//            if (amount < 0) {
//                errorLabel.setText("amount must be positive number");
//                return;
//            }
//            errorLabel.setText("");
////           materialList.decreaseAddAmountToAmount(material.getName(),amount);
//            amountTextField.clear();
//            dataSource.writeData(materialList);
//            showMaterial(material);
//        } catch (NumberFormatException e) {
//            errorLabel.setText("amount must be number");
//        }
//
//    }

    @FXML
    void backButton(ActionEvent event) {
        try {
            FXRouter.goTo("materialList");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void RequisitionButton(ActionEvent event) {
        try {
            materialList = materialListDataSource.readData();
            material = materialList.findMaterialByName(material.getName());
            PassTo passTo1 = new PassTo(account,material);
            FXRouter.goTo("requisitionMaterial",passTo1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

