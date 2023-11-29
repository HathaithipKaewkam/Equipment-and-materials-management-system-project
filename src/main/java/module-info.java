module ku.project.untitledteam {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
//    requires de.jensd.fx.glyphs.fontawesome;
    requires java.desktop;


    opens ku.project.untitledteam to javafx.fxml;
    exports ku.project.untitledteam;

    exports ku.project.controllers;
    opens ku.project.controllers to javafx.fxml;

    exports ku.project.models.material;
    opens ku.project.models.material to javafx.base;

    exports ku.project.models.user;
    opens ku.project.models.user to javafx.base;

    exports ku.project.models.products;
    opens ku.project.models.products to javafx.base;

    exports ku.project.models.account;
    opens ku.project.models.account to javafx.base;

}