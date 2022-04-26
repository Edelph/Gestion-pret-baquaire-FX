package Main.Model.Repository;

import Main.Controller.Window.TableModelListePret;
import Main.Controller.Window.TableModelMontantParBanque;
import Main.Model.Entity.Banque;
import Main.Model.DB.MysqlConnection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BanqueRepository {

    private String Table = "banque";
    private Connection connection;

    /**
     * constructor
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public BanqueRepository() {
        this.connection = (new MysqlConnection()).getConnection();
    }


    //date to string

    private String stringDateSQL(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return simpleDateFormat.format(date);
    }

    private String stringMonthSQL(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        return simpleDateFormat.format(date);
    }
    private String stringYearSQL(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(date);
    }
    //end date to string

    //research
    public  ArrayList<Banque> listBanqueWithSearch(String patern, LimitOffset limitOffset) {

        String querry = "SELECT * FROM "+ this.Table +
                " WHERE designation like '%" + patern + "%' " + limitOffset.getSql();
        ResultSet RSbanque = null;
        try{
            Statement statement = this.connection.createStatement();
            RSbanque =  statement.executeQuery(querry);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return this.getBanques(RSbanque);
    }
    public  int listBanqueWithSearchCount(String patern) {

        String querry = "SELECT COUNT(numBanque) as countBanque FROM "+ this.Table +
                " WHERE designation like '%" + patern + "%' " ;
        ResultSet RSbanque = null;
        int count = 0;
        try{
            Statement statement = this.connection.createStatement();
            RSbanque =  statement.executeQuery(querry);
            RSbanque.next();
            count = RSbanque.getInt("countBanque");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }

    /********************************************************************************************************/
                            //Montant par banque
    /********************************************************************************************************/

    //// Requete pour Montant Par Banque

    public  ArrayList<TableModelMontantParBanque> montanPretBanque(LimitOffset limitOffset) {

        String querry = "SELECT banque.designation as banque, banque.tauxDePret as taux, COUNT(client.numCompte) as effectif, SUM(pret.montant) as total " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "GROUP BY banque.numBanque" + limitOffset.getSql();

        return this.getBanquesMontantParBanque(querry);
    }

    public int montanPretBanqueCount( ) {

        String querry = "SELECT COUNT(banque.numBanque) as countMontant " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "GROUP BY banque.numBanque";

        return this.getBanquesMontantParBanqueCount(querry);
    }

    public  ArrayList<TableModelMontantParBanque> montanPretBanqueAnnee(Date date, LimitOffset limitOffset) {

        String querry = "SELECT banque.designation as banque, banque.tauxDePret as taux, COUNT(client.numCompte) as effectif, SUM(pret.montant) as total " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE YEAR(pret.datePret)="+this.stringYearSQL(date)+
                " GROUP BY banque.numBanque" + limitOffset.getSql();

        return this.getBanquesMontantParBanque(querry);
    }

    public  int montanPretBanqueAnneeCount(Date date) {

        String querry = "SELECT COUNT(banque.numBanque) as countMontant " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE YEAR(pret.datePret)="+this.stringYearSQL(date)+
                " GROUP BY banque.numBanque";

        return this.getBanquesMontantParBanqueCount(querry);
    }

    public  ArrayList<TableModelMontantParBanque> montanPretBanqueMois(Date date, LimitOffset limitOffset) {

        String querry = "SELECT banque.designation as banque, banque.tauxDePret as taux, COUNT(client.numCompte) as effectif, SUM(pret.montant) as total " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE MONTH(pret.datePret)="+this.stringMonthSQL(date)+
                " GROUP BY banque.numBanque" + limitOffset.getSql();

        return this.getBanquesMontantParBanque(querry);
    }

    public  int montanPretBanqueMoisCount(Date date) {

        String querry = "SELECT COUNT(banque.numBanque) as countMontant " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE MONTH(pret.datePret)="+this.stringMonthSQL(date)+
                " GROUP BY banque.numBanque";

        return this.getBanquesMontantParBanqueCount(querry);
    }

    public  ArrayList<TableModelMontantParBanque> montanPretBanque2Date(Date debDate, Date finDate, LimitOffset limitOffset) {

        String querry = "SELECT banque.designation as banque, banque.tauxDePret as taux, COUNT(client.numCompte) as effectif, SUM(pret.montant) as total " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE pret.datePret BETWEEN "+this.stringYearSQL(debDate)+
                " AND "+ this.stringDateSQL(finDate) +
                " GROUP BY banque.numBanque"+ limitOffset.getSql();

        return this.getBanquesMontantParBanque(querry);
    }

    public  int montanPretBanque2DateCount(Date debDate, Date finDate) {

        String querry = "SELECT COUNT(banque.numBanque) as countMontant " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE pret.datePret BETWEEN "+this.stringYearSQL(debDate)+
                " AND "+ this.stringDateSQL(finDate) +
                " GROUP BY banque.numBanque";

        return this.getBanquesMontantParBanqueCount(querry);
    }

    /********************************************************************************************************/
                                            // LIST PRET
    /********************************************************************************************************/

    public  ArrayList<TableModelListePret> listPret(String numBanque, LimitOffset limitOffset) {

        String querry = "SELECT client.nom as client, pret.datePret as datePret, pret.montant as montant " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE banque.numBanque = '" + numBanque + "' " +
                "GROUP BY pret.id"+limitOffset.getSql();

        return this.getBanquesListePret(querry);
    }

    public  int listPretCount(String numBanque) {
        String querry = "SELECT  COUNT(pret.id) as countPret " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE banque.numBanque = '" + numBanque + "' " ;
        return this.getBanquesListePretCount(querry);
    }

    public  ArrayList<TableModelListePret> listPretAnnee(String numBanque, Date date, LimitOffset limitOffset ) {

        String querry = "SELECT client.nom as client, pret.datePret as datePret, pret.montant as montant " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE banque.numBanque = '" + numBanque + "' " +
                "AND YEAR(pret.datePret)="+this.stringYearSQL(date)+
                " GROUP BY pret.id" + limitOffset.getSql();

        return this.getBanquesListePret(querry);
    }

    public int listPretAnneeCount(String numBanque, Date date) {

        String querry = "SELECT COUNT(pret.id) as countPret " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE banque.numBanque = '" + numBanque + "' " +
                "AND YEAR(pret.datePret)="+this.stringYearSQL(date);

        return this.getBanquesListePretCount(querry);
    }

    public  Double listPretAnneeMontantAPayer(String numBanque, Date date) {

        String querry = "SELECT SUM(pret.montant + (pret.montant/ banque.tauxDePret)) as montantAPayer  " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE banque.numBanque = '" + numBanque + "' " +
                "AND YEAR(pret.datePret)="+this.stringYearSQL(date);

        return this.getMontantAPayer(querry);
    }


    public  ArrayList<TableModelListePret> listPretMois(String numBanque, Date date, LimitOffset limitOffset) {

        String querry = "SELECT client.nom as client, pret.datePret as datePret, pret.montant as montant " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE banque.numBanque = '" + numBanque + "' " +
                "AND MONTH(pret.datePret)="+this.stringMonthSQL(date)+
                " GROUP BY pret.id" + limitOffset.getSql();

        return this.getBanquesListePret(querry);
    }
    public  int listPretMoisCount(String numBanque, Date date) {

        String querry = "SELECT COUNT(pret.id) as countPret " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE banque.numBanque = '" + numBanque + "' " +
                "AND MONTH(pret.datePret)="+this.stringMonthSQL(date);

        return this.getBanquesListePretCount(querry);
    }

    public Double listPretMoisMontantAPayer(String numBanque, Date date) {

        String querry = "SELECT SUM(pret.montant + (pret.montant/ banque.tauxDePret)) as montantAPayer  " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE banque.numBanque = '" + numBanque + "' " +
                "AND MONTH(pret.datePret)="+this.stringMonthSQL(date);

        return this.getMontantAPayer(querry);
    }

    public ArrayList<TableModelListePret> listPret2Date(String numBanque, Date debDate, Date finDate, LimitOffset limitOffset) {

        String querry = "SELECT client.nom as client, pret.datePret as datePret, pret.montant as montant " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE banque.numBanque = '" + numBanque + "' " +
                "AND pret.datePret BETWEEN '"+this.stringDateSQL(debDate)+"' "+
                "AND '"+ this.stringDateSQL(finDate) +"' "+
                "GROUP BY pret.id" + limitOffset.getSql();

        return this.getBanquesListePret(querry);
    }

    public  int listPret2DateCount(String numBanque, Date debDate, Date finDate) {

        String querry = "SELECT COUNT(pret.id) as countPret " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE banque.numBanque = '" + numBanque + "' " +
                "AND pret.datePret BETWEEN '"+this.stringDateSQL(debDate)+"' "+
                "AND '"+ this.stringDateSQL(finDate) +"' ";

        return this.getBanquesListePretCount(querry);
    }

    public  double listPret2DateMontantAPayer(String numBanque, Date debDate, Date finDate) {
        String querry = "SELECT  SUM(pret.montant + (pret.montant/ banque.tauxDePret)) as montantAPayer " +
                "FROM banque LEFT JOIN pret ON banque.numBanque = pret.numBanque " +
                "LEFT JOIN client ON client.numCompte = pret.numCompte " +
                "WHERE banque.numBanque = '" + numBanque + "' " +
                "AND pret.datePret BETWEEN '"+this.stringDateSQL(debDate)+"' "+
                "AND '"+ this.stringDateSQL(finDate) +"' ";

        return this.getMontantAPayer(querry);
    }

    /********************************************************************************************************/
    /********************************************************************************************************/

    public  Banque searchOneBanqueByNumBanque (String numBanque ) throws SQLException {

        String querry = "SELECT * FROM "+ this.Table +
                " WHERE numBanque= "+ numBanque ;

        Statement statement = this.connection.createStatement();
        ResultSet RSbanque =  statement.executeQuery(querry);
        ArrayList<Banque> arrayList = this.getBanques(RSbanque);

        if (arrayList.isEmpty()) return null;
        return arrayList.get(0);
    }

    public  Banque searchOneBanqueByNumBanqueWithMontantAPayer (String numBanque ) {
        String querry = "SELECT banque.*, SUM(pret.montant + (pret.montant/ banque.tauxDePret)) as montantAPayer " +
                "FROM banque " +
                "LEFT JOIN pret ON pret.numBanque = banque.numBanque " +
                "WHERE banque.numBanque = '"+ numBanque + "' "+
                "GROUP BY banque.numBanque";


        Statement statement = null;
        ArrayList<Banque> banqueList = new ArrayList();
        try {
            statement = this.connection.createStatement();

            ResultSet RSbanque =  statement.executeQuery(querry);

            while (RSbanque.next()){
                Banque banque= new Banque();

                banque.setDesignation(RSbanque.getString("designation"));
                banque.setNumBanque(RSbanque.getString("numBanque"));
                banque.setTauxDePret(RSbanque.getInt("tauxDePret"));
                banque.setMontantAPayer(RSbanque.getDouble("montantAPayer"));

                banqueList.add(banque);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (banqueList.isEmpty() ) return null;
        return banqueList.get(0);
    }

    public  ArrayList<Banque> listClientHavePretOneMonth(Date date) throws SQLException {

        String querry = "SELECT * FROM pret  INNER jOIN "+ this.Table +
                " ON pret.numCompte = " + this.Table + ".numCompte"+
                " WHERE MONTH(pret.datePret)="+ this.stringMonthSQL(date) ;

        Statement statement = this.connection.createStatement();
        ResultSet RSclient =  statement.executeQuery(querry);
        return this.getBanques(RSclient);
    }


    // end research

    public int getCountBanque()  {
        String querry = "SELECT COUNT(numBanque) as clients FROM "+this.Table;

        ResultSet RSbanque = null;
        int clients = 0;

        try {
            Statement statement = this.connection.createStatement();
            RSbanque =  statement.executeQuery(querry);
            RSbanque.next();
            clients = RSbanque.getInt("clients");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return clients;
    }

    public ArrayList<Banque> getAll(LimitOffset limitOffset)  {
        String querry = "SELECT * FROM "+this.Table + limitOffset.getPreparedSql();
        ResultSet RSbanque = null;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(querry);
            preparedStatement.setObject(1,limitOffset.getLimit());
            preparedStatement.setObject(2,limitOffset.getOffset());
            RSbanque =  preparedStatement.executeQuery();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return this.getBanques(RSbanque);
    }

    /********************************************************************************************************/
    //                             CREATE-DELETE-UPDATE
    /********************************************************************************************************/
    public void add(Banque banque) {
        String querry = "INSERT INTO "+this.Table+" VALUES (?,?,?)";

        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(querry);

            preparedStatement.setObject(1,banque.getNumBanque());
            preparedStatement.setObject(2,banque.getDesignation());
            preparedStatement.setObject(3,banque.getTauxDePret());
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void update(Banque banque) {
        String querry = "UPDATE " + this.Table + " SET designation =?, tauxDePret=? WHERE numBanque =? ";

        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(querry);
            preparedStatement.setObject(1,banque.getDesignation());
            preparedStatement.setObject(2,banque.getTauxDePret());
            preparedStatement.setObject(3,banque.getNumBanque());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(Banque banque) {
        String querry = "DELETE FROM "+ this.Table + " WHERE numBanque = ?";

        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(querry);

            preparedStatement.setObject(1,banque.getNumBanque());

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    /********************************************************************************************************/
    //                        RECUPERATION DONNEES
    /********************************************************************************************************/
    private ArrayList<Banque> getBanques(ResultSet RSbanque) {

        ArrayList <Banque> BanqueList = new ArrayList<Banque>();
        try {
            while (RSbanque.next()) {
                Banque banque = new Banque();
                banque.setNumBanque(RSbanque.getString("numBanque"));
                banque.setDesignation(RSbanque.getString("designation"));
                banque.setTauxDePret(RSbanque.getInt("tauxDePret"));

                BanqueList.add(banque);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return BanqueList;
    }

    private ArrayList<TableModelMontantParBanque> getBanquesMontantParBanque(String querry) {

        ResultSet RSbanque = null;

        try {
            Statement statement = this.connection.createStatement();
            RSbanque = statement.executeQuery(querry);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<TableModelMontantParBanque> banqueList = new ArrayList();
        try {
            while (RSbanque.next()) {
                TableModelMontantParBanque banque = new TableModelMontantParBanque();
                banque.setBanque(RSbanque.getString("banque"));
                banque.setTaux(RSbanque.getInt("taux"));
                banque.setEffectif(RSbanque.getString("effectif"));
                banque.setTotal(RSbanque.getInt("total"));

                banqueList.add(banque);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return banqueList;
    }

    private int getBanquesMontantParBanqueCount(String querry) {

        ResultSet RSbanque = null;
        int count = 0;
        try {
            Statement statement = this.connection.createStatement();
            RSbanque = statement.executeQuery(querry);
            RSbanque.next();
            count = RSbanque.getInt("countMontant");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    private ArrayList<TableModelListePret> getBanquesListePret(String querry) {
        ResultSet RSbanque = null;

        try {
            Statement statement = this.connection.createStatement();
            RSbanque = statement.executeQuery(querry);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<TableModelListePret> banqueList = new ArrayList();
        try{
            while (RSbanque.next()){
                if(RSbanque.getString("client") != null ){
                    TableModelListePret banque= new TableModelListePret();
                    banque.setClient(RSbanque.getString("client"));
                    banque.setDate(RSbanque.getString("datePret"));
                    banque.setMontant(RSbanque.getInt("montant"));
                    banqueList.add(banque);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return banqueList;
    }

    private int getBanquesListePretCount(String querry) {
        ResultSet RSbanque = null;
        int count = 0;
        try {
            Statement statement = this.connection.createStatement();
            RSbanque = statement.executeQuery(querry);
            RSbanque.next();
            count = RSbanque.getInt("countPret");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    private Double getMontantAPayer(String querry){

        Double montantAPayer = null;
        try {
            Statement statement = this.connection.createStatement();
            ResultSet RSbanque =  statement.executeQuery(querry);
            RSbanque.next();
            montantAPayer = RSbanque.getDouble("montantAPayer");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return montantAPayer;
    }
    /********************************************************************************************************/
    /********************************************************************************************************/

}
