package Main.Window;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainWindow extends Application {

    Double xOffset,yOffset;

    @Override
    public void start(final Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/MainWindow.fxml"));
        Parent root = loader.load();

        Main.Controller.MainWindow controller = loader.getController();

        controller.setStage(primaryStage);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        Image icon = new Image("./Assert/logo.png");
        primaryStage.getIcons().add(icon);

        primaryStage.setTitle("Edelph");
        primaryStage.setScene(new Scene(root));



        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                primaryStage.setX(mouseEvent.getScreenX() - xOffset);
                primaryStage.setY(mouseEvent.getScreenY() - yOffset);
            }
        });



        primaryStage.show();
    }

}