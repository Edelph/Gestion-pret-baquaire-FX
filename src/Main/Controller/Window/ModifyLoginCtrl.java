package Main.Controller.Window;

import Main.Model.Entity.User;
import Main.Model.Repository.Authentication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyLoginCtrl {
    @FXML
    private TextField uname;

    @FXML
    private Button btnOk, btnCancel;

    @FXML
    private PasswordField pass, oldPass, pass2;
    Stage stage;

    @FXML
    void btnCancelHandle(ActionEvent event) {
        stage.close();
    }

    @FXML
    void btnOkHandle(ActionEvent event) {
        User newUser = new User();
        newUser.setName(this.uname.getText());
        newUser.setPassword(this.oldPass.getText());
        newUser.setNewPassword(this.pass.getText());
        newUser.setConfirmPassword(this.pass2.getText());

        if(newUser.confirmOldPassword()){
            if(newUser.confirmNewPassword()){
                Authentication authentication = new Authentication();
                authentication.updateUser(newUser);
                stage.close();
            }else this.errorNewPassword();
        }else this.errorOldPassword();
    }

    private void errorOldPassword(){
        System.out.println("mot de passe incorrect");
    }
    private void errorNewPassword(){
        System.out.println("mot de passe n'est pas identique ");
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
