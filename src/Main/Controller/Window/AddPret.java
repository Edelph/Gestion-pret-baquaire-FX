package Main.Controller.Window;

import Main.Model.Entity.Banque;
import Main.Model.Entity.Client;
import Main.Model.Entity.Pret;
import Main.Model.Repository.Authentication;
import Main.Model.Repository.BanqueRepository;
import Main.Model.Repository.ClientRepository;
import Main.Model.Repository.PretRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.text.ParseException;

public class AddPret{

    @FXML
    private TextField numCompteLabel;

    @FXML
    private TextField nomLabel;

    @FXML
    private Button searchClient;

    @FXML
    private TextField numBanqueLabel;

    @FXML
    private TextField designationLabel;

    @FXML
    private Button searchBanque;

    @FXML
    private TextField montantLabel;

    @FXML
    private TextField montantPayerLabel;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button okBtn;

    @FXML
    private Button annulerBtn;

    private Stage stage;

    private Banque banque =null;
    private Client client = null;
    private PretCtrl parent;
    private Pret pret, oldPret;

    @FXML
    void annulerBtnHandle(ActionEvent event) throws SQLException {
        Authentication auth = new Authentication();

        System.out.println(auth.getUser().getPassword());
        this.stage.close();
    }

    @FXML
    void okBtnHandle(ActionEvent event) throws SQLException {

        if ((this.client != null) && (this.banque != null)){

            Pret pret = new Pret();

            pret.setDatePret(this.datePicker.getEditor().getText());
            pret.setMontant(Integer.parseInt(this.montantLabel.getText()));
            pret.setBanque(this.banque);
            pret.setClient(this.client);

            PretRepository rep = new PretRepository();

            if(this.oldPret != null ){
                pret.setId(this.oldPret.getId());
                rep.update(pret);
            }else{
                rep.add(pret);
            }

            this.parent.TPret.setItems(this.parent.getObservableList(this.parent.pretRepository.getAll(this.parent.limitOffset)));
            this.stage.close();
        }
    }

    @FXML
    void searchBanqueHandle(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(!this.numBanqueLabel.getText().equals("")){
            BanqueRepository rep = new BanqueRepository();
            this.banque = rep.searchOneBanqueByNumBanque(this.numBanqueLabel.getText());

            if (this.banque != null ){
                this.designationLabel.setText(banque.getDesignation());
                this.setMontantLabel();
            } else{
                this.montantPayerLabel.clear();
                this.designationLabel.clear();
            }
        }
    }

    @FXML
    void searchClientHandle(ActionEvent event) throws SQLException, ClassNotFoundException {

        if(!this.numCompteLabel.getText().equals("")){
            ClientRepository rep = new ClientRepository();
            this.client = rep.searchOneClientByNumCompte(this.numCompteLabel.getText());

            if (this.client != null ){
                this.nomLabel.setText(client.getNom());
            }else{
                this.nomLabel.clear();
            }
        }
    }

    @FXML
    void montanLabelKeyReleased(KeyEvent event) {
        this.setMontantLabel();
    }

    private double montantPayer(double montant, int percentage){
        return montant+(montant/percentage);
    }

    private void setMontantLabel(){
        if (this.banque != null){
            try {
                this.montantPayerLabel.setText(Double.toString(this.montantPayer(getMontant(),this.banque.getTauxDePret())));
            }catch (NumberFormatException e){
                this.montantPayerLabel.clear();
            }
        }
    }
    private double getMontant(){
       String montant = this.montantLabel.getText();
       if (montant.trim().equals("")){
           return 0.0;
       }
       return  Double.parseDouble(montant);
    }

    public PretCtrl getParent() {
        return parent;
    }

    public void setParent(PretCtrl parent) {
        this.parent = parent;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

    public TextField getNumCompteLabel() {
        return numCompteLabel;
    }

    public void setNumCompteLabel(TextField numCompteLabel) {
        this.numCompteLabel = numCompteLabel;
    }

    public TextField getNomLabel() {
        return nomLabel;
    }

    public void setNomLabel(TextField nomLabel) {
        this.nomLabel = nomLabel;
    }

    public Button getSearchClient() {
        return searchClient;
    }

    public void setSearchClient(Button searchClient) {
        this.searchClient = searchClient;
    }

    public TextField getNumBanqueLabel() {
        return numBanqueLabel;
    }

    public void setNumBanqueLabel(TextField numBanqueLabel) {
        this.numBanqueLabel = numBanqueLabel;
    }

    public TextField getDesignationLabel() {
        return designationLabel;
    }

    public void setDesignationLabel(TextField designationLabel) {
        this.designationLabel = designationLabel;
    }

    public Button getSearchBanque() {
        return searchBanque;
    }

    public void setSearchBanque(Button searchBanque) {
        this.searchBanque = searchBanque;
    }

    public TextField getMontantLabel() {
        return montantLabel;
    }

    public void setMontantLabel(TextField montantLabel) {
        this.montantLabel = montantLabel;
    }

    public TextField getMontantPayerLabel() {
        return montantPayerLabel;
    }

    public void setMontantPayerLabel(TextField montantPayerLabel) {
        this.montantPayerLabel = montantPayerLabel;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    public Button getOkBtn() {
        return okBtn;
    }

    public void setOkBtn(Button okBtn) {
        this.okBtn = okBtn;
    }

    public Banque getBanque() {
        return banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Pret getOldPret() {
        return oldPret;
    }

    public void setOldPret(Pret oldPret) {
        this.oldPret = oldPret;
    }
}
