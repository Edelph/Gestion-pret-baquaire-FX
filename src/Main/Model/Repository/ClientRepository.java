package Main.Model.Repository;

import Main.Model.DB.MysqlConnection;
import Main.Model.Entity.Banque;
import Main.Model.Entity.Client;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClientRepository {

    private String Table = "client";
    private Connection connection;


    public ClientRepository(){
        this.connection = (new MysqlConnection()).getConnection();
    }

    public  ArrayList<Client> listClientHavePret() throws SQLException {

        String querry = "SELECT * FROM pret  INNER jOIN "+ this.Table +
                " ON pret.numCompte = " + this.Table + ".numCompte";

        Statement statement = this.connection.createStatement();
        ResultSet RSclient =  statement.executeQuery(querry);
        return this.getClients(RSclient);

    }

    /**
     *
     * @param date transformation date en date SQL
     * @return
     */
    private String stringDateSQL(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return simpleDateFormat.format(date);
    }

    private String stringMonthSQL(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        return simpleDateFormat.format(date);
    }
    private String stringYearSQL(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(date);
    }

    public String chiffreAffaire(String numCompte) throws SQLException {

        String querry = "SELECT SUM(pret.montant) Montant FROM pret "+
                "LEFT JOIN client ON client.numCompte = pret.numCompte"+
                " WHERE client.numCompte<>'"+ numCompte +"'";

        Statement statement = this.connection.createStatement();
        ResultSet RSclient=  statement.executeQuery(querry);

        RSclient.next();
        return RSclient.getString("Montant");
    }

    public Client searchOneClientByNumCompte (String numCompte ) throws SQLException {
        String querry = "SELECT * FROM "+ this.Table +
                " WHERE numCompte= "+ numCompte ;

        Statement statement = this.connection.createStatement();
        ResultSet RSclient=  statement.executeQuery(querry);
        ArrayList<Client> arrayList = this.getClients(RSclient);

        if (arrayList.isEmpty()){
            return null;
        }else{
            return arrayList.get(0);
        }

    }

    public String[] searchOneClientByNumCompteAndMontantTotal (String numCompte ) throws SQLException {
        String querry = "SELECT client.nom nom, SUM(pret.montant) Montant FROM pret "+
                "LEFT JOIN client ON client.numCompte = pret.numCompte"+
                " WHERE client.numCompte='"+ numCompte +"'";

        Statement statement = this.connection.createStatement();
        ResultSet RSclient=  statement.executeQuery(querry);

        RSclient.next();
        String[] re = new String[2];

        re[0] = RSclient.getString("nom");
        re[1] = RSclient.getString("Montant");

        return re;

    }



    public  ArrayList<Client> listClientHavePretWith2Date(Date debCalendar, Date finCalendar) throws SQLException {

        String querry = "SELECT * FROM pret  INNER jOIN "+ this.Table +
                " ON pret.numCompte = " + this.Table + ".numCompte"+
                " WHERE pret.datePret BETWEEN  "+ this.stringDateSQL(debCalendar) +" AND " + this.stringDateSQL(finCalendar);

        if (finCalendar == null){
             querry = "SELECT * FROM pret  INNER jOIN "+ this.Table +
                    " ON pret.numCompte = " + this.Table + ".numCompte"+
                    " WHERE pret.datePret="+ this.stringDateSQL(debCalendar);
        }


        Statement statement = this.connection.createStatement();
        ResultSet RSclient =  statement.executeQuery(querry);
        return this.getClients(RSclient);
    }

    public  ArrayList<Client> listClientHavePretOneMonth(Date date) throws SQLException {

        String querry = "SELECT * FROM pret  INNER jOIN "+ this.Table +
                " ON pret.numCompte = " + this.Table + ".numCompte"+
                " WHERE MONTH(pret.datePret)="+ this.stringMonthSQL(date) ;

        Statement statement = this.connection.createStatement();
        ResultSet RSclient =  statement.executeQuery(querry);
        return this.getClients(RSclient);
    }

    public  ArrayList<Client> listClientHavePretOneYear(Date date) throws SQLException {

        String querry = "SELECT * FROM pret  INNER jOIN "+ this.Table +
                " ON pret.numCompte = " + this.Table + ".numCompte"+
                " WHERE YEAR(pret.datePret)="+ this.stringYearSQL(date) ;

        Statement statement = this.connection.createStatement();
        ResultSet RSclient =  statement.executeQuery(querry);
        return this.getClients(RSclient);
    }
    public int getCountCLient(){
        String querry = "SELECT COUNT(numCompte) as clients FROM "+this.Table;

        ResultSet RSclient = null;
        int clients = 0;
        try {
            Statement statement = this.connection.createStatement();
            RSclient =  statement.executeQuery(querry);
            RSclient.next();
            clients = RSclient.getInt("clients");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public ArrayList<Client> getAll(LimitOffset limitOffset){
        String querry = "SELECT * FROM "+this.Table + limitOffset.getPreparedSql();

        ResultSet RSclient = null;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(querry);
            preparedStatement.setObject(1,limitOffset.getLimit());
            preparedStatement.setObject(2,limitOffset.getOffset());
            RSclient =  preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.getClients(RSclient);

    }

    public ArrayList<Client> getAll(int limite, int offset){
        String querry = "SELECT * FROM "+this.Table+
                " LIMIT ? OFFSET ?";

        ResultSet RSclient = null;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(querry);
            preparedStatement.setObject(1,limite);
            preparedStatement.setObject(2,offset);
            RSclient =  preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.getClients(RSclient);

    }
    public ArrayList<Client> getAll() {
        String querry = "SELECT * FROM "+this.Table;
        ResultSet RSclient = null;
        try {
           Statement statement = this.connection.createStatement();
            RSclient =  statement.executeQuery(querry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.getClients(RSclient);
    }

    /*
        recherche numero client (numero compte)
     */
    public ArrayList<Client> searchNumClient(String patern, LimitOffset limitOffset) {
        ResultSet RSclient =  this.searchLikeOne("numCompte",patern, limitOffset);
        return this.getClients(RSclient);
    }
    public int searchNumClientCount(String patern){
       return  this.searchLikeOneCount("numCompte",patern);
    }


    public ArrayList<Client> searchDesignation (String patern, LimitOffset limitOffset){
        ResultSet RSclient =  this.searchLikeOne("Nom",patern, limitOffset);
        return this.getClients(RSclient);
    }
    public int searchDesignationCount (String patern){
        return  this.searchLikeOneCount("Nom",patern);
    }


    public void add(Client client) {
        String querry = "INSERT INTO "+this.Table+" VALUES (?,?,?)";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement(querry);
            preparedStatement.setObject(1,client.getNumCompte());
            preparedStatement.setObject(2,client.getNom());
            preparedStatement.setObject(3,client.getAdresse());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void update(Client client){
        String querry = "UPDATE " + this.Table + " SET nom =?, adresse=? WHERE numCompte =? ";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement(querry);
            preparedStatement.setObject(1,client.getNom());
            preparedStatement.setObject(2,client.getAdresse());
            preparedStatement.setObject(3,client.getNumCompte());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(Client client){
        String querry = "DELETE FROM "+ this.Table + " WHERE numCompte = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement(querry);
            preparedStatement.setObject(1,client.getNumCompte());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private ResultSet searchLikeOne(String colomn, String patern, LimitOffset limitOffset) {
        patern = "'%"+patern+"%'";
        String querry = "SELECT * FROM "+this.Table + " Where " + colomn + " LIKE "+ patern + limitOffset.getSql();

        ResultSet RSclient = null;
        try {
            Statement statement = this.connection.createStatement();
             RSclient = statement.executeQuery(querry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return RSclient;
    }

    private int searchLikeOneCount(String colomn, String patern) {
        patern = "'%"+patern+"%'";

        String querry = "SELECT COUNT(numCompte) as client FROM "+this.Table + " Where " + colomn + " LIKE "+ patern;

        ResultSet RSclient = null;
        int count = 0;
        try {
            Statement statement = this.connection.createStatement();
            RSclient = statement.executeQuery(querry);
            RSclient.next();
            count = RSclient.getInt("client");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     *
     * @param RSclient
     * @return ArrayList of Client
     * @throws SQLException
     */
    private ArrayList<Client> getClients(ResultSet RSclient){

        ArrayList <Client> ClientList = new ArrayList<Client>();

        try {
            while (RSclient.next()){
                Client client= new Client();
                client.setNumCompte(RSclient.getString("numCompte"));
                client.setNom(RSclient.getString("nom"));
                client.setAdresse(RSclient.getString("adresse"));

                ClientList.add(client);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ClientList;
    }


}
