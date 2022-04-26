package Main.Controller.Window;

import Main.Model.Repository.BanqueRepository;
import Main.Model.Repository.LimitOffset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class MontantParBanqueCtrl implements Initializable {

    @FXML
    public TableView<?> TBanque;

    @FXML
    private Label Lpages;

    @FXML
    private Button btnPrev, tnNext;

    @FXML
    private Pane paneSearchDate;
    ResearchDateCtrl researchDateCtrl;
    BanqueRepository banqueRepository;
    LimitOffset limitOffset;

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
    void rowClicked(MouseEvent event) {

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

    void MysearchYear(String date)  {
        this.limitOffset = new LimitOffset();
        this.limitOffset.setItems(this.banqueRepository.montanPretBanqueAnneeCount(this.getDate(date)));
        this.Lpages.setText(this.limitOffset.getStringPages());

        ObservableList montantBanque = FXCollections.observableArrayList(this.banqueRepository.montanPretBanqueAnnee(this.getDate(date), this.limitOffset));
        TBanque.setItems(montantBanque);
    }
    void Mysearch2date(String debDate, String finDate){
        this.limitOffset = new LimitOffset();
        this.limitOffset.setItems(this.banqueRepository.montanPretBanque2DateCount(this.getDate(debDate), this.getDate(finDate)));
        this.Lpages.setText(this.limitOffset.getStringPages());

        ObservableList montantBanque = FXCollections.observableArrayList(this.banqueRepository.montanPretBanque2Date(this.getDate(debDate), this.getDate(finDate),this.limitOffset));
        TBanque.setItems(montantBanque);
    }

    void MysearchMonth(String date){
        this.limitOffset = new LimitOffset();
        this.limitOffset.setItems(this.banqueRepository.montanPretBanqueMoisCount(this.getDate(date)));
        this.Lpages.setText(this.limitOffset.getStringPages());

        ObservableList montantBanque = FXCollections.observableArrayList(this.banqueRepository.montanPretBanqueMois(this.getDate(date),this.limitOffset));
        TBanque.setItems(montantBanque);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.banqueRepository = new BanqueRepository();
        this.limitOffset = new LimitOffset();
        this.limitOffset.setItems(this.banqueRepository.montanPretBanqueCount());
        this.Lpages.setText(this.limitOffset.getStringPages());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../View/Component/ResearchDate.fxml"));
            this.paneSearchDate.getChildren().add((Node) loader.load());
            this.researchDateCtrl = loader.getController();
            this.researchDateCtrl.montantParBanqueCtrl = this;

            this.researchDateCtrl.setActionBtnSearch(new ResearchDateCtrl.ActionBtnSearch() {
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

        TableColumn clo1 = TBanque.getColumns().get(0);
        clo1.setCellValueFactory(new PropertyValueFactory<TableModelMontantParBanque,String>("banque"));

        TableColumn clo2 = TBanque.getColumns().get(1);
        clo2.setCellValueFactory(new PropertyValueFactory<TableModelMontantParBanque,String>("taux"));

        TableColumn clo3 = TBanque.getColumns().get(2);
        clo3.setCellValueFactory(new PropertyValueFactory<TableModelMontantParBanque,String>("effectif"));

        TableColumn clo4 = TBanque.getColumns().get(3);
        clo4.setCellValueFactory(new PropertyValueFactory<TableModelMontantParBanque,String>("total"));

        TableColumn clo5 = TBanque.getColumns().get(4);
        clo5.setCellValueFactory(new PropertyValueFactory<TableModelMontantParBanque,String>("totalAPayer"));
        TBanque.setItems(this.getObservableList(this.banqueRepository.montanPretBanque(this.limitOffset)));
    }

    ObservableList getObservableList(ArrayList<TableModelMontantParBanque> clientArrayList){
        return FXCollections.observableArrayList(clientArrayList);
    }

    private void btnPaginate(){
        if(!this.researchDateCtrl.isSearch()){
            this.TBanque.setItems(this.getObservableList(this.banqueRepository.montanPretBanque(this.limitOffset)));
        }else{
            int selected = this.researchDateCtrl.selectedTypeDate.getSelectionModel().getSelectedIndex();
            ObservableList montantBanque = FXCollections.observableArrayList();
            String finDate = this.researchDateCtrl.finDate.getEditor().getText();
            String debDate = this.researchDateCtrl.debDate.getEditor().getText();

            switch (selected){
                case 0:
                    montantBanque = this.getObservableList(this.banqueRepository.montanPretBanqueAnnee(this.getDate(finDate),this.limitOffset));
                    break;
                case 1:
                    montantBanque = this.getObservableList(this.banqueRepository.montanPretBanqueMois( this.getDate(finDate), this.limitOffset));
                    break;
                case 2:
                    montantBanque = this.getObservableList(this.banqueRepository.montanPretBanque2Date(this.getDate(debDate), this.getDate(finDate), this.limitOffset));
                    break;
            }
            this.TBanque.setItems(montantBanque);
        }
    }

    public LimitOffset getLimitOffset() {
        return limitOffset;
    }
}
