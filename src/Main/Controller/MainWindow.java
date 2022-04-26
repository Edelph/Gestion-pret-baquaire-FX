package Main.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

    @FXML
    private Button menuChart, menuClient, menuBanque, menuPret, menuBilan, btnClose, menuParams,menuSary;

    @FXML
    private StackPane stackPaneWindow;

    @FXML
    private ImageView closeIcon,closeIcon1;

    @FXML
    private Pane paneTitle, paneTitrePre;

    @FXML
    private Label title, topMenuPre, topMenuMontant, topMenuListePret;

    private Button btnActive;
    private Label labelActive;

    private Stage stage;

    private Node windowBanque, windowClient
            , montantParBanque,listePrets,
            windowPret, params, windowChart;

    @FXML
    void menuSaryHandle(ActionEvent event) {
        this.paneTitle.toFront();
        this.title.setText("Chiffre d'affaire");
        this.activeMenu(menuSary);
        this.windowChart.toFront();
    }

    @FXML
    void menuParamsHandle(ActionEvent event) {
        this.paneTitle.toFront();
        this.title.setText("Parametre");
        this.activeMenu(menuParams);
        this.params.toFront();
    }

    @FXML
    void btnCloseHandle(ActionEvent event){
        this.stage.close();
    }

    @FXML
    void btnCloseEntered(MouseEvent mouseEvent){
        this.closeIcon.setImage(new Image("./Assert/icon/icons8_Close_hover_32.png"));
        this.closeIcon1.setImage(new Image("./Assert/icon/icons8_Close_hover_32.png"));
    }
    @FXML
    void btnCloseExited(MouseEvent mouseEvent){
        this.closeIcon.setImage(new Image("./Assert/icon/icons8_Close_32.png"));
        this.closeIcon1.setImage(new Image("./Assert/icon/icons8_Close_32.png"));
    }

    @FXML
    void menuClientHandle(ActionEvent event) {
        this.paneTitle.toFront();
        this.title.setText("Client");
        this.activeMenu(menuClient);
        this.windowClient.toFront();
    }

    @FXML
    void menuPretHandle(ActionEvent event) {
            this.paneTitrePre.toFront();
            this.title.setText("Pret");
            this.activeMenu(menuPret);
            this.windowPret.toFront();
    }

    @FXML
    void menubanqueHandle(ActionEvent event) {
        this.paneTitle.toFront();
        this.title.setText("Banque");
        this.activeMenu(menuBanque);
        this.windowBanque.toFront();
    }


    @FXML
    void pretPressed(MouseEvent event){
        this.activeLabelMenu((Label) event.getSource());
        this.activeMenu(menuPret);
        this.windowPret.toFront();
    }

    @FXML
    void montantPressed(MouseEvent event){
        this.activeLabelMenu((Label) event.getSource());
        this.montantParBanque.toFront();
    }
    @FXML
    void listPretPressed(MouseEvent event){
        this.activeLabelMenu((Label) event.getSource());
        this.listePrets.toFront();
    }
    @FXML
    void menuTopMouseEnter(MouseEvent event){
        ((Label) event.getSource()).setStyle("-fx-background-color: #3f567f;");
    }
    @FXML
    void menuTopMouseExit(MouseEvent event){
        ((Label) event.getSource()).setStyle("-fx-background-color: transparent;");
        this.labelActive.setStyle("-fx-background-color: #4D6E9B");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.paneTitle.toFront();
        try {
            this.windowBanque     = FXMLLoader.load(getClass().getResource("../View/Window/Banque.fxml"));
            this.windowClient     = FXMLLoader.load(getClass().getResource("../View/Window/Client.fxml"));
            this.montantParBanque = FXMLLoader.load(getClass().getResource("../View/Window/MontantParBanque.fxml"));
            this.windowPret       = FXMLLoader.load(getClass().getResource("../View/Window/Pret.fxml"));
            this.listePrets       = FXMLLoader.load(getClass().getResource("../View/Window/ListePrets.fxml"));
            this.params           = FXMLLoader.load(getClass().getResource("../View/Window/Params.fxml"));
            this.windowChart      = FXMLLoader.load(getClass().getResource("../View/Window/Chart.fxml"));

            this.stackPaneWindow.getChildren().
                    addAll(this.windowBanque, this.windowClient,
                    this.listePrets, this.montantParBanque,
                    this.windowPret,this.params, this.windowChart
                    );

            this.btnActive = menuClient;
            this.labelActive = topMenuPre;

            this.btnActive.setStyle("-fx-text-fill: #E42022");
            this.labelActive.setStyle("-fx-background-color: #4D6E9B");
            this.windowClient.toFront();
            this.title.setText("Client");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Stage getStage() {
        return stage;
    }

    private void activeMenu(Button btn){
        this.btnActive.setStyle("-fx-text-fill: #fff");
        this.btnActive = btn;
        this.btnActive.setStyle("-fx-text-fill: #E42022");
    }
    private void activeLabelMenu(Label label){
        this.labelActive.setStyle("-fx-background-color: transparent");
        this.labelActive = label;
        this.labelActive.setStyle("-fx-background-color: #4D6E9B");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
