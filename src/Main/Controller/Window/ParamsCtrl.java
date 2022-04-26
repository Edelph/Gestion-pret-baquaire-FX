package Main.Controller.Window;

import Main.Window.ModifyLoginWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;

public class ParamsCtrl {

    @FXML
    private ImageView imgProfil;

    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private Button btnProfil;

    @FXML
    private Button btnLogin;

    @FXML
    void btnLoginHandle(ActionEvent event) throws Exception {
        ModifyLoginWindow modifyLoginWindow = new ModifyLoginWindow();
        modifyLoginWindow.start(new Stage());
    }

    @FXML
    void btnProfilHandle(ActionEvent event) {

    }

}
