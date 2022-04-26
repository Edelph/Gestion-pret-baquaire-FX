package Main.Window;

import Main.Controller.Window.AddPret;
import Main.Controller.Window.PretCtrl;
import Main.Model.Entity.Pret;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddPretWindow extends Application {
    private Stage stage;
    private PretCtrl parent;
    private Pret pret;

    private Double xOffset, yOffset;

    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Modale/AddPretCtrl.fxml"));
        Parent root = loader.load();

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
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
            }
        });

        AddPret addPret =  loader.getController();
        addPret.setStage(stage);

        if (this.pret != null){
            this.addData(addPret);
        }

        addPret.setParent(this.parent);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root));
    }

    public void show() throws Exception {
        this.stage = new Stage();
        this.start(this.stage);
        this.stage.show();
    }

    public PretCtrl getParent() {
        return parent;
    }

    public void setParent(PretCtrl parent) {
        this.parent = parent;
    }

    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

    public void addData(AddPret addPret){
        addPret.getNumCompteLabel().setText(this.pret.getNumCompte());
        addPret.setOldPret(this.pret);

        addPret.getNumBanqueLabel().setText(this.pret.getNumBanque());

        addPret.getMontantLabel().setText(Integer.toString(this.pret.getMontant()));
        addPret.getDatePicker().getEditor().setText(this.pret.getStringDate());


        if ((this.pret.getBanque() != null) && (this.pret.getClient() != null )){
            addPret.setBanque(this.pret.getBanque());
            addPret.setClient(this.pret.getClient());

            addPret.getNomLabel().setText(this.pret.getClient().getNom());
            addPret.getDesignationLabel().setText(this.pret.getBanque().getDesignation());

            addPret.getMontantPayerLabel().setText(this.pret.getMontanPayer());
        }
    }
}
