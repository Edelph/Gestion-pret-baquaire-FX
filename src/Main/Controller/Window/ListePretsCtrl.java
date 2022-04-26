package Main.Controller.Window;

import Main.Model.Entity.Banque;
import Main.Model.Entity.Client;
import Main.Model.Repository.BanqueRepository;
import Main.Model.Repository.LimitOffset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ListePretsCtrl implements Initializable {

    @FXML
    private TableView<?> TClient;

    @FXML
    private TextField designationLabel, tauxLabel,searchNumBanque,totalAPayerLabel;

    @FXML
    private Button btnNext, btnPrev ,btnSearch;



    @FXML
    private Label Lpages;

    @FXML
    private Pane panelBareClient, panelBareBanque, paneListBanque, paneListeClient;

    @FXML
    private Pane paneSearchDate;

    private Banque banque;
    ResearchDateCtrl researchDateCtrl;
    private LimitOffset limitOffset;
    private BanqueRepository banqueRepository;

    @FXML
    void btnNextHandle(ActionEvent event) {
        this.limitOffset.next();
        this.btnPaginate();
        this.Lpages.setText(limitOffset.getStringPages());
    }

    @FXML
    void btnPrevHandle(ActionEvent event) {
        this.limitOffset.prev();
        this.btnPaginate();
        this.Lpages.setText(limitOffset.getStringPages());
    }

    @FXML
    void btnSearchClientHandle(ActionEvent event) {
        this.limitOffset.prev();
        this.btnPaginate();
        this.Lpages.setText(limitOffset.getStringPages());
    }


    @FXML
    void btnSearchBanqueHandle(ActionEvent event) {
        String patern = this.searchNumBanque.getText();
        int selectedTypeDate = this.researchDateCtrl.selectedTypeDate.getSelectionModel().getSelectedIndex();

        if(this.getBanque(patern) != null ){
            this.limitOffset = new LimitOffset();
            this.limitOffset.setItems(this.banqueRepository.listPretCount(patern));
            this.Lpages.setText(this.limitOffset.getStringPages());

            this.designationLabel.setText(this.banque.getDesignation());
            this.tauxLabel.setText(Integer.toString(this.banque.getTauxDePret()));
            this.totalAPayerLabel.setText(Double.toString(this.banque.getMontantAPayer()));

            if(this.researchDateCtrl.isSearch()){
                switch (selectedTypeDate){
                    case 0:
                        this.researchDateCtrl.searchYear();
                        break;
                    case 1:
                        this.researchDateCtrl.searchMonth();
                        break;
                    case 2:
                        this.researchDateCtrl.search2date();
                        break;
                }
            }else{
                TClient.setItems(this.getObservableList(this.banqueRepository.listPret(patern,this.limitOffset)));
            }
        }else{
            this.designationLabel.clear();
            this.tauxLabel.clear();
        }
    }

    Date getDate(String date) {
        Date value = null;
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            value = simpleDateFormat.parse(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return value;
    }

    void MysearchYear(String date){
        this.limitOffset = new LimitOffset();
        this.limitOffset.setItems(this.banqueRepository.listPretAnneeCount(this.banque.getNumBanque(),this.getDate(date)));
        this.Lpages.setText(this.limitOffset.getStringPages());

        ObservableList listPrets = this.getObservableList(this.banqueRepository.listPretAnnee(this.banque.getNumBanque(),this.getDate(date),this.limitOffset));
        totalAPayerLabel.setText(Double.toString(this.banqueRepository.listPretAnneeMontantAPayer(this.banque.getNumBanque(),this.getDate(date))));
        TClient.setItems(listPrets);
    }
    void Mysearch2date(String debDate, String finDate){
        this.limitOffset = new LimitOffset();
        limitOffset.setItems(this.banqueRepository.listPret2DateCount(this.banque.getNumBanque(), this.getDate(debDate), this.getDate(finDate)));
        this.Lpages.setText(this.limitOffset.getStringPages());

        ObservableList listPrets = this.getObservableList(this.banqueRepository.listPret2Date(this.banque.getNumBanque(), this.getDate(debDate), this.getDate(finDate), this.limitOffset));
        totalAPayerLabel.setText(Double.toString(this.banqueRepository.listPret2DateMontantAPayer(this.banque.getNumBanque(),this.getDate(debDate), this.getDate(finDate))));
        TClient.setItems(listPrets);
    }

    void MysearchMonth(String date){
        this.limitOffset = new LimitOffset();
        limitOffset.setItems(this.banqueRepository.listPretMoisCount(this.banque.getNumBanque(), this.getDate(date)));
        this.Lpages.setText(this.limitOffset.getStringPages());

        ObservableList listPrets = this.getObservableList(this.banqueRepository.listPretMois(this.banque.getNumBanque(), this.getDate(date), this.limitOffset));
        totalAPayerLabel.setText(Double.toString(this.banqueRepository.listPretMoisMontantAPayer(this.banque.getNumBanque(),this.getDate(date))));
        TClient.setItems(listPrets);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.banqueRepository = new BanqueRepository();

        this.designationLabel.setEditable(false);
        this.tauxLabel.setEditable(false);
        this.totalAPayerLabel.setEditable(false);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../View/Component/ResearchDate.fxml"));

            this.paneSearchDate.getChildren().add((Node) loader.load());
            researchDateCtrl = loader.getController();
            researchDateCtrl.listePretsCtrl = this;

            researchDateCtrl.setActionBtnSearch(new ResearchDateCtrl.ActionBtnSearch() {
                @Override
                public void searchYear(String date) {
                    MysearchYear(date);
                }

                @Override
                public void searchMonth(String date) {
                    MysearchMonth(date);
                }

                @Override
                public void search2date(String debDate, String finDate) {
                    Mysearch2date(debDate, finDate);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


        TableColumn clo1 = TClient.getColumns().get(0);
        clo1.setCellValueFactory(new PropertyValueFactory<TableModelListePret,String>("client"));

        TableColumn clo2 = TClient.getColumns().get(1);
        clo2.setCellValueFactory(new PropertyValueFactory<TableModelListePret,String>("date"));

        TableColumn clo3 = TClient.getColumns().get(2);
        clo3.setCellValueFactory(new PropertyValueFactory<TableModelListePret,String>("montant"));

        TableColumn clo4 = TClient.getColumns().get(3);
        clo4.setCellValueFactory(new PropertyValueFactory<TableModelListePret,String>("montantAPayer"));

    }

    ObservableList getObservableList(ArrayList<TableModelListePret> clientArrayList){
        return FXCollections.observableArrayList(clientArrayList);
    }


    Banque getBanque(String numBanque){
        this.banque = this.banqueRepository.searchOneBanqueByNumBanqueWithMontantAPayer(numBanque);
        TableModelListePret tableModelListePret = new TableModelListePret();
        tableModelListePret.setBanque(this.banque);

        return this.banque;
    }

    boolean isSearch(){
       return !this.designationLabel.getText().equals("") || !this.tauxLabel.getText().equals("");
    }

    private void btnPaginate(){
        if(!this.researchDateCtrl.isSearch()){
            this.TClient.setItems(this.getObservableList(this.banqueRepository.listPret(this.banque.getNumBanque(),this.limitOffset)));
        }else{
            int selected = this.researchDateCtrl.selectedTypeDate.getSelectionModel().getSelectedIndex();
            ObservableList listPrets = FXCollections.observableArrayList();
            String finDate = this.researchDateCtrl.finDate.getEditor().getText();
            String debDate = this.researchDateCtrl.debDate.getEditor().getText();

            switch (selected){
                case 0:
                    listPrets = this.getObservableList(this.banqueRepository.listPretAnnee(this.banque.getNumBanque(),this.getDate(finDate),this.limitOffset));
                    break;
                case 1:
                    listPrets = this.getObservableList(this.banqueRepository.listPretMois(this.banque.getNumBanque(), this.getDate(finDate), this.limitOffset));
                    break;
                case 2:
                    listPrets = this.getObservableList(this.banqueRepository.listPret2Date(this.banque.getNumBanque(), this.getDate(debDate), this.getDate(finDate), this.limitOffset));
                    break;
            }
            this.TClient.setItems(listPrets);
        }
    }

    public Banque getBanque() {
        return banque;
    }

    public LimitOffset getLimitOffset() {
        return limitOffset;
    }
}

