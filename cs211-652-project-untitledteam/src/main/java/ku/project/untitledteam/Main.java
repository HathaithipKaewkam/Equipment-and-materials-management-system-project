package ku.project.untitledteam;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import ku.project.models.products.Lender;
import ku.project.models.products.Product;
import ku.project.services.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Untitled Team",900,600);
        stage.setResizable(false);
        configRoute();
        FXRouter.goTo("login");

    }

    public static void configRoute()
    {
        String viewPath = "ku/project/views/";
        FXRouter.when("hello", viewPath + "hello-view.fxml");
        FXRouter.when("login", viewPath + "login.fxml");
        FXRouter.when("newstaff", viewPath + "newstaff.fxml");
        FXRouter.when("register", viewPath + "register.fxml");
        FXRouter.when("password", viewPath + "password.fxml");
        FXRouter.when("homeShowProduct", viewPath + "showProduct.fxml");
        FXRouter.when("userprofile", viewPath + "userProfile.fxml");
        FXRouter.when("adminMain", viewPath + "adminMain.fxml");
        FXRouter.when("staffMain", viewPath + "staffMain.fxml");
        FXRouter.when("untitledTeam", viewPath + "untitledTeam.fxml");
        FXRouter.when("howToUse", viewPath + "howToUse.fxml");
        FXRouter.when("materialList", viewPath + "materialList.fxml");
        FXRouter.when("addMaterial", viewPath + "addMaterial.fxml");
        FXRouter.when("detailsMaterial", viewPath + "detailsMaterial.fxml");
        FXRouter.when("requisitionMaterial", viewPath + "requisitionMaterial.fxml");
        FXRouter.when("detailSelect", viewPath + "detailProductStaff.fxml");
        FXRouter.when("addProduct", viewPath + "addProduct.fxml");



    }
//    private static Parent loadFXML(String fxml) throws IOException{
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + "fxml"));
//        return fxmlLoader.load();
//    }

    public static void main(String[] args) {
        launch();
    }
}