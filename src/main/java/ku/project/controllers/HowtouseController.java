package ku.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.project.services.FXRouter;

import java.io.IOException;

public class HowtouseController {

    @FXML
    void backtologinButton(ActionEvent event) {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}