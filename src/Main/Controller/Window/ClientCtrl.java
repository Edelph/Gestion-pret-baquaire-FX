package Main.Controller.Window;

import Main.Model.Repository.LimitOffset;
import Main.Window.ModaleWindow;
import Main.Model.Entity.Client;
import Main.Model.Repository.ClientRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientCtrl implements Initializable {

    @FXML
    private ComboBox CBsearch;

    @FXML
    private Pane paneSearch;

    @FXML
    private TableView<?> TClient;

    @FXML
    private Label Lpages;

    @FXML
    private Button btnAdd,btnUpdate,BtnDelete, btnPrev, btnNext, btnSearch;

    @FXML
    private TextField searchLabel;

    @FXML
    private StackPane stackPaneSearch;

    private ModaleWindow modaleWindow;
    private int page, clientPages;
    private LimitOffset limitOffset;
    private String paternSearch="";
    private ClientRepository clientRepository;

    private String[] option = new String[] {"Numero", "Nom"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize variable
        this.limitOffset = new LimitOffset();
        this.clientRepository = new ClientRepository();
        this.limitOffset.setItems(this.clientRepository.getCountCLient());

        //initialisation combobox
        this.CBsearch.getItems().addAll(this.option);
        this.CBsearch.getSelectionModel().select(1);

        //intitialisation tableview
        TableColumn col = TClient.getColumns().get(0);
        col.setCellValueFactory(new PropertyValueFactory< Client , String>( "numCompte"));

        TableColumn col2 = TClient.getColumns().get(1);
        col2.setCellValueFactory(new PropertyValueFactory< Client , String>( "nom"));

        TableColumn col3 = TClient.getColumns().get(2);
        col3.setCellValueFactory(new PropertyValueFactory< Client , Integer>( "adresse"));

        this.tableView(this.clientRepository.getAll(this.limitOffset));

        this.Lpages.setText(this.limitOffset.getStringPages());
    }

    @FXML
    void btnCrudHandle(ActionEvent event) throws Exception {

        String Action = ((Button) event.getSource()).getText();

        if (!Action.toLowerCase().equals("supprimer")){
            this.modaleWindow = new ModaleWindow();
            this.modaleWindow.fxml="AddUpdateEntity.fxml";
            this.modaleWindow.setEntity("client");
            this.modaleWindow.setParent(this);
            this.modaleWindow.setLabels(new String[]{"N° Compte", "Nom", "Adresse"});

            if (Action.toLowerCase().equals("modifier")){
                Client client = (Client) TClient.getSelectionModel().getSelectedItem();
                this.modaleWindow.setData(client);
            }
            modaleWindow.setTitle(Action);
            modaleWindow.show();
        }else{
            this.clientRepository.delete((Client) TClient.getSelectionModel().getSelectedItem());
            this.tableView(this.clientRepository.getAll(this.limitOffset));
        }
    }

    @FXML
    void btnNextHandle(ActionEvent event) throws Exception {
        this.limitOffset.next();
        this.btnPaginate();
    }

    @FXML
    void btnPrevHandle(ActionEvent event) throws Exception {
        this.limitOffset.prev();
        this.btnPaginate();
    }

    @FXML
    void btnSearchHandle(ActionEvent event) throws Exception {

         this.paternSearch = this.searchLabel.getText();
        if(this.paternSearch.equals("")){
            //redefinition limite offset
            this.limitOffset = new LimitOffset();
            this.limitOffset.setItems(this.clientRepository.getCountCLient());
            this.tableView(this.clientRepository.getAll(this.limitOffset));
            this.Lpages.setText(this.limitOffset.getStringPages());
        }else{
            String typeSearch = (String) this.CBsearch.getSelectionModel().getSelectedItem();
            ObservableList clients = FXCollections.observableArrayList();

            if (typeSearch.toLowerCase().equals("nom")){

                this.limitOffset = new LimitOffset();
                this.limitOffset.setItems(this.clientRepository.searchDesignationCount(this.paternSearch));

                clients = this.getObservableList(this.clientRepository.searchDesignation(this.paternSearch, this.limitOffset));

            }else if(typeSearch.toLowerCase().equals("numero")){

                this.limitOffset = new LimitOffset();
                this.limitOffset.setItems(this.clientRepository.searchNumClientCount(this.paternSearch));

                clients = this.getObservableList(this.clientRepository.searchNumClient(this.paternSearch,this.limitOffset));
            }
            this.TClient.setItems(clients);
            this.Lpages.setText(this.limitOffset.getStringPages());
        }
    }


    ObservableList getObservableList(ArrayList<Client> clientArrayList){
        return FXCollections.observableArrayList(clientArrayList);
    }

    public void tableView(ArrayList<Client> clientArrayList) {
        this.TClient.setItems(this.getObservableList(clientArrayList));
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }
    public LimitOffset getLimitOffset() {
        return limitOffset;
    }
    private void btnPaginate(){
        if(this.paternSearch.equals("")){
            this.tableView(this.clientRepository.getAll(this.limitOffset));
        }else{
            String typeSearch = (String) this.CBsearch.getSelectionModel().getSelectedItem();
            ObservableList clients = FXCollections.observableArrayList();

            if(typeSearch.toLowerCase().equals("nom"))
            {
                clients = this.getObservableList(this.clientRepository.searchDesignation(this.paternSearch, this.limitOffset));
            } else if(typeSearch.toLowerCase().equals("numero"))
            {
                clients = this.getObservableList(this.clientRepository.searchNumClient(this.paternSearch,this.limitOffset));
            }
            this.TClient.setItems(clients);
        }
        this.Lpages.setText(this.limitOffset.getStringPages());
    }
}