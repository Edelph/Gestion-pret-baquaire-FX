package Main.Model.Repository;

import Main.Model.DB.MysqlConnection;
import Main.Model.Entity.Pret;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PretRepository {
    private String Table = "pret";
    private Connection connection;


    public PretRepository(){
        this.connection = (new MysqlConnection()).getConnection();
    }

    public ArrayList<Pret> getAll(LimitOffset limitOffset) {
        String querry = "SELECT * FROM "+this.Table + limitOffset.getSql();

        ArrayList <Pret> ClientList = new ArrayList<Pret>();

        ResultSet RSpret = null;
        try {
           Statement statement = this.connection.createStatement();
            RSpret =  statement.executeQuery(querry);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.getPret(RSpret);
    }

    public int getCountAll() {
        String querry = "SELECT COUNT(id)  as pretCount FROM "+this.Table ;

        Statement statement = null;
        int count = 0;
        try {
            statement = this.connection.createStatement();
            ResultSet RSpret =  statement.executeQuery(querry);
            RSpret.next();
            count = RSpret.getInt("pretCount");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    public ArrayList<Pret> getByClient(String patern, LimitOffset limitOffset){
        patern = "'"+patern+"'";

        String querry = "SELECT * FROM "+this.Table +" WHERE numCompte like "+patern + limitOffset.getSql();

        ArrayList <Pret> ClientList = new ArrayList<Pret>();

        ResultSet RSpret = null;
        try {
            Statement statement = this.connection.createStatement();
            RSpret =  statement.executeQuery(querry);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.getPret(RSpret);
    }

    public int getByClientCount(String patern) {
        patern = "'"+patern+"'";
        String querry = "SELECT COUNT(id)  as pretCount FROM "+this.Table+" WHERE numCompte like "+ patern;

        Statement statement = null;
        int count = 0;
        try {
            statement = this.connection.createStatement();
            ResultSet RSpret =  statement.executeQuery(querry);
            RSpret.next();
            count = RSpret.getInt("pretCount");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<Pret> getByBanque(String patern, LimitOffset limitOffset){
        patern = "'"+patern+"'";

        String querry = "SELECT * FROM "+this.Table +" WHERE numBanque like "+patern + limitOffset.getSql();
        ArrayList <Pret> ClientList = new ArrayList<Pret>();

        ResultSet RSpret = null;
        try {
            Statement statement = this.connection.createStatement();
            RSpret =  statement.executeQuery(querry);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.getPret(RSpret);
    }

    public int getByBanqueCount(String patern) {
        patern = "'"+patern+"'";
        String querry = "SELECT COUNT(id)  as pretCount FROM "+this.Table+" WHERE numCompte like "+ patern;

        Statement statement = null;
        int count = 0;
        try {
            statement = this.connection.createStatement();
            ResultSet RSpret =  statement.executeQuery(querry);
            RSpret.next();
            count = RSpret.getInt("pretCount");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    /***********************************************************************************/

    public void add(Pret pret) {
        System.out.println(pret.getNumBanque());
        String querry = "INSERT INTO "+this.Table+"(numBanque, numCompte, montant, datePret) VALUES (?,?,?,?)";


        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(querry);
            preparedStatement.setObject(1,pret.getNumBanque());
            preparedStatement.setObject(2,pret.getNumCompte());
            preparedStatement.setObject(3,pret.getMontant());
            preparedStatement.setObject(4,pret.getDatePret());

             preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void  update (Pret pret){
        String querry = "UPDATE " + this.Table + " SET numBanque =?, numCompte=?, montant =?, datePret=? WHERE id =? ";


        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(querry);
            preparedStatement.setObject(1,pret.getNumBanque());
            preparedStatement.setObject(2,pret.getNumCompte());
            preparedStatement.setObject(3,pret.getMontant());
            preparedStatement.setObject(4,this.stringDateSQL(pret.getDatePret()));
            preparedStatement.setObject(5,pret.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Pret pret) {
        String querry = "DELETE FROM "+ this.Table + " WHERE id = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement(querry);
            preparedStatement.setObject(1,pret.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private String stringDateSQL(Date date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        System.out.println(simpleDateFormat.format(date));

        return simpleDateFormat.format(date);
    }
    private ArrayList<Pret> getPret(ResultSet resultSet){
        ArrayList <Pret> ClientList = new ArrayList<Pret>();
        try {
            while (resultSet.next()){
                Pret pret= new Pret();

                pret.setId(resultSet.getInt("id"));
                pret.setNumCompte(resultSet.getString("numCompte"));
                pret.setNumBanque(resultSet.getString("numBanque"));
                pret.setMontant(resultSet.getInt("montant"));
                pret.setDatePret(resultSet.getString("datePret"));

                ClientList.add(pret);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ClientList;
    }


}
