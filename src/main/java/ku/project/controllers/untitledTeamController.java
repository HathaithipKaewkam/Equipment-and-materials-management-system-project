package ku.project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import ku.project.services.FXRouter;

import java.io.IOException;

public class untitledTeamController {

    @FXML
    private ImageView profileBow;

    @FXML
    private ImageView profileEarth;

    @FXML
    private ImageView profilePin;
    @FXML
    void backLoginButton(ActionEvent event) {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

