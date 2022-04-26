package Main.Controller.Window;

import Main.Model.Entity.Pret;
import Main.Model.Repository.LimitOffset;
import Main.Window.ModaleWindow;
import Main.Model.Entity.Banque;
import Main.Model.Repository.BanqueRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BanqueCtrl implements Initializable {

    @FXML
    private ChoiceBox<String> CBsearch;

    @FXML
    private Pane paneSearch;

    @FXML
    public TableView<?> TBanque;

    @FXML
    private Label Lpages;

    @FXML
    private Button btnAdd,btnUpdate,BtnDelete, btnPrev, btnNext;

    @FXML
    private TextField search;

    private ModaleWindow modaleWindow;

    private String[] option = new String[] {"option 1", "option 2", "option3"};
    public LimitOffset limitOffset;
    public BanqueRepository banqueRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.banqueRepository = new BanqueRepository();
        this.limitOffset = new LimitOffset();
        this.limitOffset.setItems(this.banqueRepository.getCountBanque());
        this.Lpages.setText(this.limitOffset.getStringPages());

        TableColumn col = TBanque.getColumns().get(0);
        col.setCellValueFactory(new PropertyValueFactory< Banque , String>( "numBanque"));

        TableColumn col2 = TBanque.getColumns().get(1);
        col2.setCellValueFactory(new PropertyValueFactory< Banque , String>( "designation"));

        TableColumn col3 = TBanque.getColumns().get(2);
        col3.setCellValueFactory(new PropertyValueFactory< Banque , Integer>( "tauxDePret"));

        this.TBanque.setItems(this.getObservableList(this.banqueRepository.getAll(this.limitOffset)));
    }

    @FXML
    void btnCrudHandle(ActionEvent event) throws Exception {

        String Action = ((Button) event.getSource()).getText();

        if (!Action.toLowerCase().equals("supprimer")){
            this.modaleWindow = new ModaleWindow();
            this.modaleWindow.fxml="AddUpdateEntity.fxml";
            this.modaleWindow.setEntity("banque");
            this.modaleWindow.setParent(this);
            this.modaleWindow.setLabels(new String[]{"N° Banque", "Designation", "Taux de pret"});

            if (Action.toLowerCase().equals("modifier")){
                Banque banque = (Banque) TBanque.getSelectionModel().getSelectedItem();
                this.modaleWindow.setData(banque);
            }
            modaleWindow.setTitle(Action);
            modaleWindow.show();
        }else{
            this.banqueRepository.delete((Banque) TBanque.getSelectionModel().getSelectedItem());
            this.TBanque.setItems(this.getObservableList(this.banqueRepository.getAll(this.limitOffset)));
        }
    }

    @FXML
    void btnNextHandle(ActionEvent event){
        this.limitOffset.next();
        this.btnPaginate();
        this.Lpages.setText(this.limitOffset.getStringPages());
    }

    @FXML
    void btnPrevHandle(ActionEvent event){
        this.limitOffset.prev();
        this.btnPaginate();
        this.Lpages.setText(this.limitOffset.getStringPages());
    }

    @FXML
    void searchHandle(ActionEvent event) {
        this.limitOffset = new LimitOffset();
        if(this.search.getText().equals("")){

            this.limitOffset.setItems(this.banqueRepository.getCountBanque());
            this.Lpages.setText(this.limitOffset.getStringPages());

            this.TBanque.setItems(this.getObservableList(this.banqueRepository.getAll(this.limitOffset)));
        }else{

            this.limitOffset.setItems(this.banqueRepository.listBanqueWithSearchCount(this.search.getText()));
            this.Lpages.setText(this.limitOffset.getStringPages());

            TBanque.setItems(this.getObservableList(this.banqueRepository.listBanqueWithSearch(this.search.getText(),limitOffset)));

        }
    }
    public ObservableList getObservableList(ArrayList<Banque> banqueArrayList){
        return FXCollections.observableArrayList(banqueArrayList);
    }
    private void btnPaginate(){
        if(this.search.getText().equals("")){
            this.TBanque.setItems(this.getObservableList(this.banqueRepository.getAll(this.limitOffset)));
        }else{
            this.TBanque.setItems(this.getObservableList(this.banqueRepository.listBanqueWithSearch(this.search.getText(),this.limitOffset)));
        }
    }

}