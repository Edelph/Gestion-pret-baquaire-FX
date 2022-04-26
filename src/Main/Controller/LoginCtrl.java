package Main.Controller;

import Main.Model.Entity.User;
import Main.Model.Repository.Authentication;
import Main.Window.MainWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



public class LoginCtrl {

    Stage stage;

    @FXML
    private ImageView closeIcon;

    @FXML
    private Separator passwordSeparator, userSeparator;

    @FXML
    private TextField userLabel, passwordLabel;


    public void btnSeConnectHandle() throws Exception {

        User user = new User();
        user.setPassword(this.passwordLabel.getText());
        user.setName(this.userLabel.getText());

        Authentication auth = new Authentication();
        User result = auth.signing(user);
        if (result.getName() != null ) {
            MainWindow mainWindow = new MainWindow();
            this.stage.close();
            mainWindow.start(new Stage());
        }

        this.userNull();

    }
    public void btnCloseHandle(){
        System.exit(1);
    }

    @FXML
    void btnCloseEntered(MouseEvent mouseEvent){
        this.closeIcon.setImage(new Image("./Assert/icon/icons8_Close_hover_32.png"));
    }
    @FXML
    void btnCloseExited(MouseEvent mouseEvent){
        this.closeIcon.setImage(new Image("./Assert/icon/icons8_Close_32.png"));
    }

    private void userNull(){
        this.passwordSeparator.setStyle("-fx-background-color: #E42022");
        this.passwordSeparator.setStyle("-fx-border-color: #E42022");
        this.userSeparator.setStyle("-fx-background-color: #E42022");
        this.userSeparator.setStyle("-fx-border-color: #E42022");
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
