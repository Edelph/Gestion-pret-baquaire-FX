package Main;

import Main.Controller.LoginCtrl;
import Main.Model.Repository.BanqueRepository;
import Main.Model.Repository.ClientRepository;
import Main.Model.Repository.LimitOffset;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    Stage stage;
    Double xx, xy;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/Login.fxml"));
        Parent root = loader.load();
        LoginCtrl loginCtrl = loader.getController();
        loginCtrl.setStage(primaryStage);
        this.stage = primaryStage;

        Image icon = new Image("./Assert/logo.png");

        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Login");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xx = mouseEvent.getSceneX();
                xy = mouseEvent.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() - xx);
                stage.setY(mouseEvent.getScreenY() - xy);
            }
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
