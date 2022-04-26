package Main.Controller;

import Main.Controller.Window.BanqueCtrl;
import Main.Controller.Window.ClientCtrl;
import Main.Model.Entity.Banque;
import Main.Model.Entity.Client;
import Main.Model.Repository.BanqueRepository;
import Main.Model.Repository.ClientRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CrudCtrl {

    @FXML
    private TextField textField1, textField2, textField3;

    @FXML
    private Button btnOk, btnCancel;

    @FXML
    private Label Ltitle,label1, label2, label3;

    private Stage stage;
    private String entity;
    private Object parent;

    @FXML
    void btnCancelHandle(ActionEvent event) {
        if (this.stage != null){
            this.stage.close();
        }
    }

    @FXML
    void btnOkHandle(ActionEvent event){
        if (this.entity.toLowerCase().equals("client")){
            if(!this.isEmpty()){
                Client client = new Client();

                client.setNumCompte(this.getTextField1().getText());
                client.setNom(this.getTextField2().getText());
                client.setAdresse( this.getTextField3().getText());
                ClientRepository clientRepository = new ClientRepository();

                if (this.Ltitle.getText().toLowerCase().equals("ajouter")) {
                    clientRepository.add(client);
                } else {
                    clientRepository.update(client);
                }
                ClientCtrl clientCtrl = (ClientCtrl) this.parent;
                clientCtrl.tableView(clientCtrl.getClientRepository().getAll(clientCtrl.getLimitOffset()));;
                this.stage.close();
            }
            else this.errorEmpty();

        }else if (this.entity.toLowerCase().equals("banque")) {
            if(!this.isEmptyBanque()){
                Banque banque = new Banque();

                banque.setNumBanque(this.getTextField1().getText());
                banque.setDesignation(this.getTextField2().getText());

                banque.setTauxDePret( Integer.parseInt(this.getTextField3().getText()));

                BanqueRepository banqueRepository = new BanqueRepository();

                if (this.Ltitle.getText().toLowerCase().equals("ajouter")) {
                    banqueRepository.add(banque);
                } else {
                    banqueRepository.update(banque);
                }
                BanqueCtrl banqueCtrl = (BanqueCtrl) this.parent;
                banqueCtrl.TBanque.setItems(banqueCtrl.getObservableList(banqueCtrl.banqueRepository.getAll(banqueCtrl.limitOffset)));
                this.stage.close();
            }
            else this.errorEmptyBanque();
        }
    }
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Label getLtitle() {
        return Ltitle;
    }

    public void setLtitle(Label ltitle) {
        Ltitle = ltitle;
    }

    public TextField getTextField1() {
        return textField1;
    }

    public void setTextField1(TextField textField1) {
        this.textField1 = textField1;
    }

    public Button getBtnOk() {
        return btnOk;
    }

    public void setBtnOk(Button btnOk) {
        this.btnOk = btnOk;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(Button btnCancel) {
        this.btnCancel = btnCancel;
    }

    public TextField getTextField2() {
        return textField2;
    }

    public void setTextField2(TextField textField2) {
        this.textField2 = textField2;
    }

    public TextField getTextField3() {
        return textField3;
    }

    public void setTextField3(TextField textField3) {
        this.textField3 = textField3;
    }

    public Label getLabel1() {
        return label1;
    }

    public void setLabel1(Label label1) {
        this.label1 = label1;
    }

    public Label getLabel2() {
        return label2;
    }

    public void setLabel2(Label label2) {
        this.label2 = label2;
    }

    public Label getLabel3() {
        return label3;
    }

    public void setLabel3(Label label3) {
        this.label3 = label3;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    private boolean isEmpty(){
        return this.textField1.getText().isEmpty() | this.textField2.getText().isEmpty();
    }
    private void errorEmpty(){
        this.textField1.setPromptText("encore vide");
        this.textField2.setPromptText("encore vide");
    }
    private boolean isEmptyBanque(){
        return this.isEmpty() | this.textField3.getText().isEmpty();
    }
    private void errorEmptyBanque(){
        this.errorEmpty();
        this.textField3.setPromptText("encore vide");
    }
}
