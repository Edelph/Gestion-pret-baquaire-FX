package Main.Controller.Window;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


public class ResearchDateCtrl implements Initializable {

    @FXML
    Button btnSearchDate;

    @FXML
    ComboBox selectedTypeDate;

    @FXML
    DatePicker debDate, finDate;

    @FXML
    Label debLabel,finLabel;

    private ActionBtnSearch action ;
    public ListePretsCtrl listePretsCtrl;
    public MontantParBanqueCtrl montantParBanqueCtrl;


    @FXML
    void setBtnSearchDateHandle(ActionEvent event){
        if (!this.finDate.getEditor().getText().equals("")){
            if( listePretsCtrl != null && this.listePretsCtrl.isSearch()){
                this.search();
            }
            if (listePretsCtrl == null){
                if (!this.isSearch()){
                    montantParBanqueCtrl.TBanque.setItems(montantParBanqueCtrl.getObservableList(montantParBanqueCtrl.banqueRepository.montanPretBanque(montantParBanqueCtrl.limitOffset)));
                }
                this.search();
            }
        }
    }

    void search(){
        int index = this.selectedTypeDate.getSelectionModel().getSelectedIndex();
        switch (index){
            case 0:
                this.searchYear();
                break;
            case 1:
                this.searchMonth();
                break;
            case 2:
                if(!this.debDate.getEditor().getText().equals("")) this.search2date();
                break;
        }
    }

    void searchYear(){
        String year = this.finDate.getEditor().getText();

        if (this.action != null ) this.action.searchYear(year);
    }
    void searchMonth(){
        this.debDate.setEditable(false);
        String month = this.finDate.getEditor().getText();

        if (this.action != null ) this.action.searchMonth(month);

    }
    void search2date(){
        String debDate = this.debDate.getEditor().getText();
        String finDate = this.finDate.getEditor().getText();

        if (this.action != null ) this.action.search2date(debDate, finDate);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] tydeDate = new String[]{"Annee", "Mois", "Deux Date"};

        this.selectedTypeDate.getItems().addAll(tydeDate);
        this.selectedTypeDate.getSelectionModel().select(0);
        this.finLabel.setText("Annee:");
        this.debDate.setDisable(true);

        this.selectedTypeDate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                comboboxChanged();
            }
        });
    }

    public void setActionBtnSearch(ActionBtnSearch action){
        this.action = action;
    }


    void comboboxChanged(){
       int selected = this.selectedTypeDate.getSelectionModel().getSelectedIndex();
       switch (selected){
           case 0:
               this.debDate.setDisable(true);
               this.debDate.getEditor().clear();
               this.finLabel.setText("Annee:");
               break;
           case 1:
               this.debDate.setDisable(true);
               this.debDate.getEditor().clear();
               this.finLabel.setText("Mois:");
               break;
           case 2:
               this.debDate.setDisable(false);
               this.debDate.setEditable(true);
               this.finLabel.setText("fin: ");
               break;
       }
    }

    public interface ActionBtnSearch{
        abstract void searchYear(String date);
        abstract void searchMonth(String date);
        abstract void search2date(String debDate, String finDate);
    }

    boolean isSearch(){
        return !(this.debDate.getEditor().getText().equals("") && this.finDate.getEditor().getText().equals(""));
}

}
