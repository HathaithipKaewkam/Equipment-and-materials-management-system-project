package ku.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ku.project.models.account.Account;
import ku.project.models.material.*;
import ku.project.services.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AddMaterialController {
    @FXML
    private Spinner<Integer> addAmountMaterial;
    SpinnerValueFactory<Integer>svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,50,1);


    @FXML
    private ComboBox categoryComboBox;

    @FXML
    private TextField nameMaterial;
    @FXML
    private TextArea materialPropertyTextArea;

    @FXML
    private Label nameLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label propertyLabel;

    private Material material;
    private DataSource<MaterialList> materialFileDataSource = new MaterialFileDataSource("database", "material.csv");
    private MaterialList materialList = materialFileDataSource.readData();

    private Alert alert;
    private DataSource<CategoryList> categoryListDataSource = new CategoryDataSource();
    private CategoryList categoryList = categoryListDataSource.readData();





    @FXML
    public void initialize() {
        clearMaterialInfo();
        initializeComboBox();


        svf.setValue(1);
        addAmountMaterial.setValueFactory(svf);
        materialPropertyTextArea.setWrapText(true);
    }

    private void initializeComboBox() {
        for (Category category : categoryList.getAllCategory()) {
            if(category.getType().equals("วัสดุ")) {
                String newItem = category.getCategory();
                categoryComboBox.getItems().add(newItem);
            }
        }
    }

    private void showMaterial(Material material) {
        nameLabel.setText(material.getName());
        amountLabel.setText(material.getCategory());
        categoryLabel.setText(String.valueOf(material.getCategory()));
        propertyLabel.setText(material.getProperty());
    }
    private void clearMaterialInfo() {
        nameLabel.setText("");
        amountLabel.setText("");
        categoryLabel.setText("");
        propertyLabel.setText("");
    }


    @FXML
    void addMaterialButton(ActionEvent event) {
        String name = nameMaterial.getText();
        String category = (String) categoryComboBox.getValue();
        int amount = addAmountMaterial.getValue();
        String  property =materialPropertyTextArea.getText();
        alert = new Alert(Alert.AlertType.NONE);
        if (name.isEmpty() || category.isEmpty() || property.isEmpty() || categoryComboBox.getValue().equals(null)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("โปรดกรอกข้อมูลให้ครบถ้วน");
            alert.show();
            clearMaterialInfo();
        }else if (!(materialList.ExistNameMaterial(name))) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("มีวัสดุนี้ในระบบ");
            alert.show();

        }else{
            material = new Material(name,category,amount,property);
            materialList.addMaterial(material);
            materialFileDataSource.writeData(materialList);
            showMaterial(material);
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

    @FXML
    void requisitionButton(ActionEvent event) {
        try {
            FXRouter.goTo("requisitionMaterial");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
