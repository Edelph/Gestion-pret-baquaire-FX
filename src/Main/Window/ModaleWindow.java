package Main.Window;

import Main.Controller.CrudCtrl;
import Main.Main;
import Main.Model.Entity.Banque;
import Main.Model.Entity.Client;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModaleWindow extends Application {
    public String pathFxml = "../View/Modale/";
    public String fxml = "";
    private String title = "Tille Of Window";
    private String[] labels = {};
    private String entity;
    private TableView tableView;
    Object Parent;
    Object data;

    private Double xOffset,yOffset;

    private Stage stage = new Stage();

    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.pathFxml+this.fxml ));

        Parent root = loader.load();
        CrudCtrl crudCtrl = loader.getController();

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


        crudCtrl.setStage(stage);

        //this.setParams(crudCtrl);

        stage.initStyle(StageStyle.UNDECORATED);

        stage.setTitle(this.title);

        stage.setScene(new Scene(root));
        this.setParams(crudCtrl);

        stage.show();
    }

    public void show() throws Exception {
        this.start(this.stage);
    }

    private void setParams(CrudCtrl crudCtrl){
        //modifier titre
        crudCtrl.getLtitle().setText(this.getTitle());

        //modifier label
        crudCtrl.getLabel1().setText(this.labels[0]);
        crudCtrl.getLabel2().setText(this.labels[1]);
        crudCtrl.getLabel3().setText(this.labels[2]);


        crudCtrl.setEntity(this.getEntity());
        crudCtrl.setParent(this.getParent());


        if ( this.data != null ){
            crudCtrl.getTextField1().setEditable(false);
            crudCtrl.getTextField1().setFocusTraversable(false);

            if(this.entity.toLowerCase().equals("banque")){
                Banque banque = (Banque) this.data;
                crudCtrl.getTextField1().setText(banque.getNumBanque());
                crudCtrl.getTextField2().setText(banque.getDesignation());
                crudCtrl.getTextField3().setText(Integer.toString(banque.getTauxDePret()));
            }else if( this.entity.toLowerCase().equals("client") ){
                Client client = (Client) this.data;
                crudCtrl.getTextField1().setText(client.getNumCompte());
                crudCtrl.getTextField2().setText(client.getNom());
                crudCtrl.getTextField3().setText(client.getAdresse());
            }
        }

    }

    public void close(){
        this.stage.close();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Object getParent() {
        return Parent;
    }

    public void setParent(Object parent) {
        Parent = parent;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
