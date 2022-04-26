package Main.Controller.Window;

import Main.Model.Entity.Pret;
import Main.Model.Repository.LimitOffset;
import Main.Model.Repository.PretRepository;
import Main.Window.AddPretWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PretCtrl implements Initializable {

    @FXML
    private StackPane stackPaneSearch;

    @FXML
    private TextField search;

    @FXML
    public TableView<?> TPret;

    @FXML
    private ComboBox searchOption;

    @FXML
    private Label Lpages;


    @FXML
    private Button btnAdd, btnUpdate, btnDelete,btnPrev, btnNext;
    PretRepository pretRepository;
    LimitOffset limitOffset;

    @FXML
    void btnCrudHandle(ActionEvent event) throws Exception {
        if (event.getSource() == this.btnAdd ){
            AddPretWindow addPretWindow = new AddPretWindow();
            addPretWindow.setParent(this);
            addPretWindow.show();
        }
        else if(event.getSource() == this.btnUpdate){
            Pret pret = (Pret) TPret.getSelectionModel().getSelectedItem();
            if (pret != null){
                AddPretWindow addPretWindow = new AddPretWindow();
                addPretWindow.setParent(this);
                addPretWindow.setPret(pret);
                addPretWindow.show();
            }
        }
        else if (event.getSource() == this.btnDelete){
            Pret pret = (Pret) TPret.getSelectionModel().getSelectedItem();
            if (pret != null){
                PretRepository rep = new PretRepository();
                rep.delete(pret);
            }
        }
    }

    @FXML
    void btnNextHandle(ActionEvent event) {
        this.limitOffset.next();
        this.btnPaginate();
    }

    @FXML
    void btnPrevHandle(ActionEvent event) {
        this.limitOffset.prev();
        this.btnPaginate();
    }

    @FXML
    void searchHandle(ActionEvent event) {
            this.limitOffset = new LimitOffset();
            int selected = this.searchOption.getSelectionModel().getSelectedIndex();
            String patern = this.search.getText();
            if(patern.equals("")){
                this.limitOffset.setItems(this.pretRepository.getCountAll());
                this.TPret.setItems(this.getObservableList(this.pretRepository.getAll(this.limitOffset)));
            }else{
                if ( selected == 0 ){
                    this.limitOffset.setItems(this.pretRepository.getByClientCount(patern));
                    this.TPret.setItems(this.getObservableList(this.pretRepository.getByClient(patern, this.limitOffset)));
                }
                else if( selected == 1  ){
                    this.limitOffset.setItems(this.pretRepository.getByBanqueCount(patern));
                    this.TPret.setItems(this.getObservableList(this.pretRepository.getByBanque(patern, this.limitOffset)));
                }
            }
            this.Lpages.setText(this.limitOffset.getStringPages());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.pretRepository = new PretRepository();
        this.limitOffset = new LimitOffset();
        this.limitOffset.setItems(this.pretRepository.getCountAll());
        this.Lpages.setText(this.limitOffset.getStringPages());

        this.searchOption.getItems().addAll(new String[]{"N° Client", "N° Banque"});
        this.searchOption.getSelectionModel().select(0);


        TableColumn col1 =  TPret.getColumns().get(0);
        col1.setCellValueFactory(new PropertyValueFactory<Pret,String>("numCompte"));

        TableColumn col2 = TPret.getColumns().get(1);
        col2.setCellValueFactory(new PropertyValueFactory<Pret, String>("numBanque"));

        TableColumn col3 = TPret.getColumns().get(2);
        col3.setCellValueFactory(new PropertyValueFactory<Pret,Double>("montant"));

        TableColumn col4 = TPret.getColumns().get(3);
        col4.setCellValueFactory(new PropertyValueFactory<Pret, String>("date"));

        this.TPret.setItems(this.getObservableList(this.pretRepository.getAll(this.limitOffset)));

    }
    ObservableList getObservableList(ArrayList<Pret> clientArrayList){
        return FXCollections.observableArrayList(clientArrayList);
    }
    private void btnPaginate(){
            int selected = this.searchOption.getSelectionModel().getSelectedIndex();
            String patern = this.search.getText();

            if(patern.equals("")){
                this.limitOffset.setItems(this.pretRepository.getCountAll());
                this.TPret.setItems(this.getObservableList(this.pretRepository.getAll(this.limitOffset)));
            }else{
                if ( selected == 0 ){
                    this.limitOffset.setItems(this.pretRepository.getByClientCount(patern));
                    this.TPret.setItems(this.getObservableList(this.pretRepository.getByClient(patern, this.limitOffset)));
                }
                else if( selected == 1  ){
                    this.limitOffset.setItems(this.pretRepository.getByBanqueCount(patern));
                    this.TPret.setItems(this.getObservableList(this.pretRepository.getByBanque(patern, this.limitOffset)));
                }
            }
            this.Lpages.setText(this.limitOffset.getStringPages());
    }
}
