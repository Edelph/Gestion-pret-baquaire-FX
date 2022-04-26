package Main.Controller.Window;

import Main.Model.Entity.Client;
import Main.Model.Repository.ClientRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ChartCtrl {
    @FXML
    private PieChart pieChart;

    @FXML
    private TextField nomLabel, numClient;

    @FXML
    private Button btnSearch;


    @FXML
    void btnSearchHandle(ActionEvent event) throws SQLException {
        ClientRepository rep = new ClientRepository();
        String[] result =  rep.searchOneClientByNumCompteAndMontantTotal(this.numClient.getText());
        String other = rep.chiffreAffaire(this.numClient.getText());
        if(result != null){
            this.nomLabel.setText(result[0]);
        }
        this.paintChart(result,other);
    }


    private void paintChart(String[] client, String other){
        ObservableList<PieChart.Data> pieChartDate =
                FXCollections.observableArrayList(
                        new PieChart.Data(client[0],1),
                        new PieChart.Data("manoro",1)
                );
        pieChart.setData(pieChartDate);
    }
}
