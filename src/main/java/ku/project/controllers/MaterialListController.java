package ku.project.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.project.models.account.Account;
import ku.project.models.material.Material;
import ku.project.models.material.MaterialList;
import ku.project.models.material.PassTo;
import ku.project.services.DataSource;
import ku.project.services.FXRouter;
import ku.project.services.MaterialFileDataSource;

import java.io.IOException;

public class MaterialListController {

    private MaterialList materialList;
    private DataSource<MaterialList>dataSource;
    PassTo passTo = (PassTo) FXRouter.getData();
    Account account = passTo.getAccount();
    @FXML
    private TableView materialTable;

    @FXML
    void addNewMaterialsButton(ActionEvent event) {
        try {
            FXRouter.goTo("addMaterial");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void backButton(ActionEvent event) {
        try {
            FXRouter.goTo("staffMain");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void initialize() {
        dataSource = new MaterialFileDataSource("database", "material.csv");
        materialList = dataSource.readData();
        showTable(materialList);

        materialTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Material>() {
            @Override
            public void changed(ObservableValue observable, Material oldValue, Material newValue) {
                if (newValue != null) {
                    try {
                        // FXRouter.goTo สามารถส่งข้อมูลไปยังหน้าที่ต้องการได้ โดยกำหนดเป็น parameter ที่สอง
                        FXRouter.goTo("detailsMaterial", new PassTo(account,newValue));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void showTable(MaterialList materialList) {
        // กำหนด column ให้มี title ว่า name และใช้ค่าจาก attribute name ของ object Material
        TableColumn<Material, String> nameColumn = new TableColumn<>("ชื่อ");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));


        // กำหนด column ให้มี title ว่า Category และใช้ค่าจาก attribute Category ของ object Material
        TableColumn<Material, String> categoryColumn = new TableColumn<>("หมวดหมู่");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        // กำหนด column ให้มี title ว่า Amount และใช้ค่าจาก attribute Amount ของ object Material
        TableColumn<Material, Integer> amountColumn = new TableColumn<>("จำนวน");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Material, String> propertyColumn = new TableColumn<>("คุณลักษณะอื่นๆ");
        propertyColumn.setCellValueFactory(new PropertyValueFactory<>("property"));


        // ล้าง column เดิมทั้งหมดที่มีอยู่ใน table แล้วเพิ่ม column ใหม่
        materialTable.getColumns().clear();
        materialTable.getColumns().add(nameColumn);
        materialTable.getColumns().add(categoryColumn);
        materialTable.getColumns().add(amountColumn);
//        materialTable.getColumns().add(addDateColumn);
//        materialTable.getColumns().add(addTimeColumn);
        materialTable.getColumns().add(propertyColumn);


        materialTable.getItems().clear();

        // ใส่ข้อมูล Material ทั้งหมดจาก MaterialList ไปแสดงใน TableView
        for (Material material : materialList.getMaterials()) {
            materialTable.getItems().add(material);
        }
    }
}
